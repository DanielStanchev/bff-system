package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
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
public class DeleteRoomOperationProcessor extends BaseOperationProcessor implements DeleteRoom {

    private final HotelRestClient hotelRestClient;
    private final ErrorMapper errorMapper;

    public DeleteRoomOperationProcessor(HotelRestClient hotelRestClient, ErrorMapper errorMapper, Validator validator,
                                        ConversionService conversionService) {
        super(conversionService,errorMapper,validator);
        this.hotelRestClient = hotelRestClient;
        this.errorMapper = errorMapper;
    }

    @Override
    public Either<ErrorWrapper, DeleteRoomBffOutput> process(DeleteRoomBffInput bffInput) {
        //log.info("Start deleteRoom input: {} ",bffInput);
        return validateInput(bffInput).flatMap(validated->deleteRoom(bffInput));
    }

    private Either<ErrorWrapper, DeleteRoomBffOutput> deleteRoom(DeleteRoomBffInput bffInput) {
        return Try.of(()->{

            DeleteRoomInput input = conversionService.convert(bffInput, DeleteRoomInput.class);
            DeleteRoomOutput output = hotelRestClient.deleteRoom(input.getId());
            DeleteRoomBffOutput bffOutput = conversionService.convert(output,DeleteRoomBffOutput.class);

            //log.info("End deleteRoom output {} deleted",bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
