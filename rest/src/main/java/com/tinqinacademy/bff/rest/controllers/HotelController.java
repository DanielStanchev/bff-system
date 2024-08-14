package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRooms;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoById;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bff.api.restapiroutes.RestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Hotel API", description = "Hotel room booking related functionality.")
@RestController
public class HotelController extends BaseController {

  private final BookRoom bookRoom;
  private final GetAvailableRooms getAvailableRooms;
  private final GetRoomInfoById getRoomInfoById;
  private final UnbookRoom unbookRoom;


    public HotelController(ObjectMapper objectMapper,BookRoom bookRoom, GetAvailableRooms getAvailableRooms,
                           GetRoomInfoById getRoomInfoById, UnbookRoom unbookRoom) {
        super(objectMapper);
        this.bookRoom = bookRoom;
        this.getAvailableRooms = getAvailableRooms;
        this.getRoomInfoById = getRoomInfoById;
        this.unbookRoom = unbookRoom;
    }

    @Operation(summary = "Book room.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping(RestApiRoutes.HOTEL_BOOK_ROOM)
    public ResponseEntity<?> bookRoom( @PathVariable("roomId") String roomId,
                                       @RequestBody BookRoomBffInput bookRoomBffInput,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) throws JsonProcessingException {

        String userId = getUserIdViaHeaders(authorization);

        BookRoomBffInput input = BookRoomBffInput.builder()
            .roomId(roomId)
            .endDate(bookRoomBffInput.getEndDate())
            .startDate(bookRoomBffInput.getStartDate())
            .userId(userId)
            .build();

        Either<ErrorWrapper, BookRoomBffOutput> result = bookRoom.process(input);
        return handleResult(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Unbook a room.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")})
    @DeleteMapping(RestApiRoutes.HOTEL_UNBOOK_ROOM)
    public ResponseEntity<?> unbook(@PathVariable("bookingId") String bookingId) {

        UnbookRoomBffInput input = UnbookRoomBffInput.builder()
            .id(bookingId)
            .build();

        Either<ErrorWrapper, UnbookRoomBffOutput> output = unbookRoom.process(input);
        return handleResult(output,HttpStatus.OK);
    }

    @Operation(summary = "Get available rooms by criteria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping(RestApiRoutes.HOTEL_GET_AVAILABLE_ROOMS)
    public ResponseEntity<?> getAvailableRooms(@RequestParam(value = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                               @RequestParam(value = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                               @RequestParam(value = "bedCount") Integer bedCount,
                                               @RequestParam(value = "beds") List<String> beds,
                                               @RequestParam(value = "bathroomType") String bathroomType) {

        GetAvailableRoomsBffInput input = GetAvailableRoomsBffInput.builder()
            .startDate(startDate)
            .endDate(endDate)
            .bedCount(bedCount)
            .beds(beds)
            .bathroomType(bathroomType)
            .build();

        Either<ErrorWrapper, GetAvailableRoomsBffOutput> output = getAvailableRooms.process(input);
        return handleResult(output, HttpStatus.OK);
    }

    @Operation(summary = "Get room basic info by Id.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping(RestApiRoutes.HOTEL_GET_AVAILABLE_ROOMS_BY_ID)
    public ResponseEntity<?> getRoomInfoById(@PathVariable("roomId") String roomId) {

        GetRoomInfoByIdBffInput input = GetRoomInfoByIdBffInput.builder()
            .roomId(roomId)
            .build();

        Either<ErrorWrapper, GetRoomInfoByIdBffOutput> output = getRoomInfoById.process(input);
        return handleResult(output, HttpStatus.OK);
    }

}
