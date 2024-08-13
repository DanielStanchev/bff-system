package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
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
public class BookRoomOperationProcessor extends BaseOperationProcessor implements BookRoom {

    private final HotelRestClient hotelRestClient;

    public BookRoomOperationProcessor(ConversionService conversionService, ErrorMapper errorMapper, Validator validator,
                                      HotelRestClient hotelRestClient) {
        super(conversionService, errorMapper,validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, BookRoomBffOutput> process(BookRoomBffInput bffInput) {
        log.info("Start bookRoom input:{}.", bffInput);
        return validateInput(bffInput).flatMap(validated->bookRoom(bffInput));
    }

    private Either<ErrorWrapper, BookRoomBffOutput> bookRoom(BookRoomBffInput bffInput) {
        return Try.of(()->{
                BookRoomInput input = conversionService.convert(bffInput,BookRoomInput.class);
                BookRoomOutput output = hotelRestClient.bookRoom(input.getRoomId(),input);
                BookRoomBffOutput bffOutput = conversionService.convert(output,BookRoomBffOutput.class);
                log.info("End bookRoom output:{}",bffOutput);
                return bffOutput;

        }).toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
            ));
    }
}
