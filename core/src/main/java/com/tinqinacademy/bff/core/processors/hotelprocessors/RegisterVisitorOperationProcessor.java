package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitor;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
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
public class RegisterVisitorOperationProcessor extends BaseOperationProcessor implements RegisterVisitor {

    private final HotelRestClient hotelRestClient;

    public RegisterVisitorOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper,
                                             HotelRestClient hotelRestClient) {
        super(conversionService, errorMapper, validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, RegisterVisitorBffOutput> process(RegisterVisitorBffInput bffInput) {
        log.info("Start registerVisitor input:{}.", bffInput);
        return validateInput(bffInput).flatMap(validated -> registerVisitor(bffInput));
    }

    private Either<ErrorWrapper, RegisterVisitorBffOutput> registerVisitor(RegisterVisitorBffInput bffInput) {
        return Try.of(() -> {
                RegisterVisitorInput input = conversionService.convert(bffInput,RegisterVisitorInput.class);
                RegisterVisitorOutput output = hotelRestClient.register(input);
                RegisterVisitorBffOutput bffOutput = conversionService.convert(output,RegisterVisitorBffOutput.class);
                log.info("End registerVisitor output:{}.", bffOutput);
                return bffOutput;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))));
    }
}