package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.core.exception.exceptions.NotFoundException;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
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

    public BookRoomOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper,
                                      HotelRestClient hotelRestClient) {
        super(validator, conversionService, errorMapper);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, BookRoomBffOutput> process(BookRoomBffInput input) {
        log.info("Start bookRoom input:{}.", input);
        return bookRoom(input);
    }

    private Either<ErrorWrapper, BookRoomBffOutput> bookRoom(BookRoomBffInput input) {
        return Try.of(()->{

                BookRoomInput convertedBookRoomInput = conversionService.convert(input,BookRoomInput.class);
                BookRoomOutput output = hotelRestClient.bookRoom(convertedBookRoomInput.getRoomId(),convertedBookRoomInput);
                log.info("End bookRoom output:{}",output);
                return BookRoomBffOutput.builder().build();

        }).toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(NotFoundException.class)), errorMapper.handleError(throwable, HttpStatus.NOT_FOUND)),
                Case($(instanceOf(IllegalArgumentException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST)),
                Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
            ));
    }
}
