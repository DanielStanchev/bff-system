package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;

import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfo;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;

import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.reportvisitorinfo.ReportVisitorsInfoInput;
import com.tinqinacademy.hotel.api.operations.reportvisitorinfo.ReportVisitorsInfoOutput;
import feign.FeignException;
import io.vavr.control.Either;
import io.vavr.control.Try;

import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import java.util.Optional;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class ReportVisitorsInfoOperationProcessor extends BaseOperationProcessor implements ReportVisitorsInfo {

    private final HotelRestClient hotelRestClient;

    public ReportVisitorsInfoOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper,
                                                HotelRestClient hotelRestClient, HotelRestClient hotelRestClient1) {
        super(conversionService, errorMapper, validator);
        this.hotelRestClient = hotelRestClient1;
    }

    @Override
    public Either<ErrorWrapper, ReportVisitorsInfoBffOutput> process (ReportVisitorsInfoBffInput bffInput) {
        log.info("Start reportVisitorInfo input{}",bffInput);
        return validateInput(bffInput).flatMap(validated->reportVisitorsInfo(bffInput));
    }

    private Either<ErrorWrapper, ReportVisitorsInfoBffOutput> reportVisitorsInfo(ReportVisitorsInfoBffInput bffInput) {
        return Try.of(()->{
            ReportVisitorsInfoInput input = conversionService.convert(bffInput, ReportVisitorsInfoInput.class);
            ReportVisitorsInfoOutput output = hotelRestClient.report(Optional.of(input.getStartDate()),
                                                                     Optional.of(input.getEndDate()),
                                                                     Optional.of(input.getFirstName()),
                                                                     Optional.of(input.getLastName()),
                                                                     Optional.of(input.getPhoneNo()),
                                                                     Optional.of(input.getIdCardNo()),
                                                                     Optional.of(input.getIdCardValidity()),
                                                                     Optional.of(input.getIdCardIssueAuthority()),
                                                                     Optional.of(input.getCardIssueDate()),
                                                                     input.getRoomNo());
            ReportVisitorsInfoBffOutput bffOutput = conversionService.convert(output, ReportVisitorsInfoBffOutput.class);
            log.info("End reportVisitorInfo output: {}", bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
