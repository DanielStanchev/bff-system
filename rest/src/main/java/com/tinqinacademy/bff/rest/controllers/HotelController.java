package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.api.restapiroutes.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hotel API", description = "Hotel room booking related functionality.")
@RestController
public class HotelController extends BaseController {

  private final AddRoom addRoom;

    public HotelController(AddRoom addRoom) {
        this.addRoom = addRoom;
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



}
