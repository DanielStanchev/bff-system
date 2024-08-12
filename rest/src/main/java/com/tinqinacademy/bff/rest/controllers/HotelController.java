package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bff.api.restapiroutes.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hotel API", description = "Hotel room booking related functionality.")
@RestController
public class HotelController extends BaseController {

  private final AddRoom addRoom;
  private final BookRoom bookRoom;

    public HotelController(ObjectMapper objectMapper,AddRoom addRoom, BookRoom bookRoom) {
        super(objectMapper);
        this.addRoom = addRoom;
        this.bookRoom = bookRoom;
    }

    @Operation(summary = "Create a room.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping(RestApiRoutes.SYSTEM_CREATE_ROOM)
    public ResponseEntity<?> addRoom(@RequestBody AddRoomBffInput addRoomBffInput) {

        AddRoomBffInput input = AddRoomBffInput.builder()
            .bedCount(addRoomBffInput.getBedCount())
            .bathroomType(addRoomBffInput.getBathroomType())
            .beds(addRoomBffInput.getBeds())
            .floor(addRoomBffInput.getFloor())
            .price(addRoomBffInput.getPrice())
            .roomNo(addRoomBffInput.getRoomNo())
            .build();

        Either<ErrorWrapper, AddRoomBffOutput> result = addRoom.process(input);
        return handleResult(result, HttpStatus.CREATED);
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

}
