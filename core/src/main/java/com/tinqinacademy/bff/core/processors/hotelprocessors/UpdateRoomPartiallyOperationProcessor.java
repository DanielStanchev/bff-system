package com.tinqinacademy.bff.core.processors.hotelprocessors;


import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartially;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;

import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.updateroompartially.UpdateRoomPartiallyInput;
import com.tinqinacademy.hotel.api.operations.updateroompartially.UpdateRoomPartiallyOutput;
import feign.FeignException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class UpdateRoomPartiallyOperationProcessor extends BaseOperationProcessor implements UpdateRoomPartially {

    private final HotelRestClient hotelRestClient;

    public UpdateRoomPartiallyOperationProcessor(ConversionService conversionService,
                                                 HotelRestClient hotelRestClient, ErrorMapper errorMapper, Validator validator) {
        super(conversionService,errorMapper,validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, UpdateRoomPartiallyBffOutput> process (UpdateRoomPartiallyBffInput bffInput) {
        log.info("Start updateRoomPartially input:{}.", bffInput);
        return validateInput(bffInput).flatMap(validated-> updateRoomPartially(bffInput));
    }

    private Either<ErrorWrapper, UpdateRoomPartiallyBffOutput> updateRoomPartially(UpdateRoomPartiallyBffInput bffInput) {
        return Try.of(()->{
            UpdateRoomPartiallyInput input = conversionService.convert(bffInput, UpdateRoomPartiallyInput.class);
            UpdateRoomPartiallyOutput output = hotelRestClient.updateRoomPartially(input.getId(),input);
            UpdateRoomPartiallyBffOutput bffOutput =conversionService.convert(output, UpdateRoomPartiallyBffOutput.class);
            log.info("Start updateRoomPartially output:{}.", bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
