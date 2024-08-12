package com.tinqinacademy.bff.core.processors.hotelprocessors;


import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.core.exception.exceptions.NotFoundException;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomInput;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomOutput;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class AddRoomOperationProcessor extends BaseOperationProcessor implements AddRoom {

    private final HotelRestClient hotelRestClient;
    private final ApplicationContext applicationContext;

    public AddRoomOperationProcessor(ConversionService conversionService, Validator validator,
                                     HotelRestClient hotelRestClient, ErrorMapper errorMapper, ApplicationContext applicationContext) {
        super(validator, conversionService,errorMapper);
        this.hotelRestClient = hotelRestClient;
        this.applicationContext = applicationContext;
    }

    @Override
    public Either<ErrorWrapper, AddRoomBffOutput> process(AddRoomBffInput input) {
        log.info("Start createRoom input:{}.", input);

        return addRoom(input);
    }

    private Either<ErrorWrapper, AddRoomBffOutput> addRoom(AddRoomBffInput input) {
        return Try.of(() -> {

                AddRoomInput convertedRoomInput =
                    conversionService.convert(input, AddRoomInput.class);

                AddRoomOutput output =
                    hotelRestClient.addRoom(convertedRoomInput);

                AddRoomBffOutput convertedBffOutput =
                    conversionService.convert(output, AddRoomBffOutput.class);

                log.info("End createRoom output:{}.", convertedBffOutput);
                return convertedBffOutput;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(NotFoundException.class)), errorMapper.handleError(throwable, HttpStatus.NOT_FOUND)),
                Case($(instanceOf(IllegalArgumentException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST)),
                Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
            ));
    }
}
