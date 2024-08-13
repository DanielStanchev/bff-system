package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
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
public class UnbookRoomOperationProcessor extends BaseOperationProcessor implements UnbookRoom {

    private final HotelRestClient hotelRestClient;

    public UnbookRoomOperationProcessor(ConversionService conversionService,
                                        HotelRestClient hotelRestClient, ErrorMapper errorMapper, Validator validator) {
        super(conversionService,errorMapper,validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, UnbookRoomBffOutput> process(UnbookRoomBffInput bffInput) {
        log.info("Start unbook input:{}.", bffInput);
        return validateInput(bffInput).flatMap(validated->unbookRoom(bffInput));
    }

    private Either<ErrorWrapper, UnbookRoomBffOutput> unbookRoom(UnbookRoomBffInput bffInput) {
        return Try.of(()->{
            UnbookRoomInput input = conversionService.convert(bffInput, UnbookRoomInput.class);
            UnbookRoomOutput output = hotelRestClient.unbook(input.getId());
            UnbookRoomBffOutput bffOutput = conversionService.convert(output, UnbookRoomBffOutput.class);
            log.info("End unbook output:{}.", bffOutput);
            return bffOutput;

        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
