package com.tinqinacademy.bff.core.processors.hotelprocessors;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoById;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffOutput;
import com.tinqinacademy.bff.core.base.BaseOperationProcessor;
import com.tinqinacademy.bff.core.exception.ErrorMapper;
import com.tinqinacademy.bff.domain.HotelRestClient;
import com.tinqinacademy.hotel.api.operations.getroominfobyid.GetRoomInfoByIdInput;
import com.tinqinacademy.hotel.api.operations.getroominfobyid.GetRoomInfoByIdOutput;
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
public class GetRoomInfoByIdOperationProcessor extends BaseOperationProcessor implements GetRoomInfoById {

private final HotelRestClient hotelRestClient;

    public GetRoomInfoByIdOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper,
                                             HotelRestClient hotelRestClient) {
        super(conversionService, errorMapper, validator);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<ErrorWrapper, GetRoomInfoByIdBffOutput> process(GetRoomInfoByIdBffInput bffInput) {
        log.info("Start getRoomInfoById input:{}.", bffInput);
        return validateInput(bffInput).flatMap(validated->getRoomInfoById(bffInput));
    }

    private Either<ErrorWrapper, GetRoomInfoByIdBffOutput> getRoomInfoById(GetRoomInfoByIdBffInput bffInput) {
       return Try.of(()-> {
               GetRoomInfoByIdInput input = conversionService.convert(bffInput, GetRoomInfoByIdInput.class);
               GetRoomInfoByIdOutput output = hotelRestClient.getRoomInfoById(input.getRoomId());
               GetRoomInfoByIdBffOutput bffOutput = conversionService.convert(output, GetRoomInfoByIdBffOutput.class);
               log.info("End getRoomInfoById output:{}.", bffOutput);
               return bffOutput;

        }).toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(FeignException.class)), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }
}
