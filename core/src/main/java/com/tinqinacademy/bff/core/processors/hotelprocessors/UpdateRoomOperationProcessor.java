package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
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
public class UpdateRoomOperationProcessor extends BaseOperationProcessor implements UpdateRoom {

    private final HotelRestClient hotelRestClient;

    public UpdateRoomOperationProcessor(ConversionService conversionService,
                                        HotelRestClient hotelRestClient, ErrorMapper errorMapper, Validator validator) {
        super(conversionService,errorMapper,validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, UpdateRoomBffOutput> process (UpdateRoomBffInput bffInput) {
        log.info("Start updateRoom input:{}.", bffInput);
        return validateInput(bffInput).flatMap(validated-> updateRoom(bffInput));
    }

    private Either<ErrorWrapper, UpdateRoomBffOutput> updateRoom(UpdateRoomBffInput bffInput) {
        return Try.of(()->{
            UpdateRoomInput input = conversionService.convert(bffInput, UpdateRoomInput.class);
            UpdateRoomOutput output = hotelRestClient.updateRoom(input,input.getId());
            UpdateRoomBffOutput bffOutput = conversionService.convert(output, UpdateRoomBffOutput.class);
            log.info("End updateRoom output:{}.", bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
