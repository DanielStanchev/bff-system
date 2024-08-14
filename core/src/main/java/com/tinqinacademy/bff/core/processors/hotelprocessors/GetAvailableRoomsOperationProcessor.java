package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRooms;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.getavailablerooms.GetAvailableRoomsInput;
import com.tinqinacademy.hotel.api.operations.getavailablerooms.GetAvailableRoomsOutput;
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
public class GetAvailableRoomsOperationProcessor extends BaseOperationProcessor implements GetAvailableRooms {

    private final HotelRestClient hotelRestClient;

    public GetAvailableRoomsOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper,
                                               Validator validator, HotelRestClient hotelRestClient) {
        super(conversionService,errorMapper,validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, GetAvailableRoomsBffOutput> process(GetAvailableRoomsBffInput bffInput) {
        log.info("Start checkAvailableRooms input: {} ",bffInput);
        return validateInput(bffInput).flatMap(validated->getAvailableRooms(bffInput));
    }

    private Either<ErrorWrapper, GetAvailableRoomsBffOutput> getAvailableRooms(GetAvailableRoomsBffInput bffInput) {
        return Try.of(()->{

            GetAvailableRoomsInput input = conversionService.convert(bffInput, GetAvailableRoomsInput.class);

            GetAvailableRoomsOutput output = hotelRestClient.getAvailableRooms(input.getStartDate(),input.getEndDate(),input.getBedCount(),
                                                                               input.getBeds(),input.getBathroomType());

            GetAvailableRoomsBffOutput bffOutput = conversionService.convert(output, GetAvailableRoomsBffOutput.class);
            log.info("End checkAvailableRooms output: {} ",bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
