package com.tinqinacademy.bff.core.processors.hotelprocessors;


import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomInput;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomOutput;
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

@Service
@Slf4j
public class AddRoomOperationProcessor extends BaseOperationProcessor implements AddRoom {

    private final HotelRestClient hotelRestClient;

    public AddRoomOperationProcessor(ConversionService conversionService,
                                     HotelRestClient hotelRestClient, ErrorMapper errorMapper,Validator validator) {
        super(conversionService,errorMapper,validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, AddRoomBffOutput> process(AddRoomBffInput bffInput) {
        log.info("Start createRoom input:{}.", bffInput);
        return validateInput(bffInput).flatMap(validated->addRoom(bffInput));
    }

    private Either<ErrorWrapper, AddRoomBffOutput> addRoom(AddRoomBffInput bffInput) {
        return Try.of(() -> {
                AddRoomInput input = conversionService.convert(bffInput, AddRoomInput.class);
                AddRoomOutput output = hotelRestClient.addRoom(input);
                AddRoomBffOutput bffOutput = conversionService.convert(output, AddRoomBffOutput.class);
                log.info("End createRoom output:{}.", bffOutput);
                return bffOutput;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
            ));
    }
}
