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
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRooms;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoById;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitor;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfo;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartially;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffOutput;
import com.tinqinacademy.bff.api.restapiroutes.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Tag(name = "Hotel API", description = "Hotel room booking related functionality.")
@RestController
public class HotelController extends BaseController {

  private final AddRoom addRoom;
  private final BookRoom bookRoom;
  private final DeleteRoom deleteRoom;
  private final GetAvailableRooms getAvailableRooms;
  private final GetRoomInfoById getRoomInfoById;
  private final UnbookRoom unbookRoom;
  private final RegisterVisitor registerVisitor;
  private final ReportVisitorsInfo reportVisitorsInfo;
  private final UpdateRoom updateRoom;
  private final UpdateRoomPartially updateRoomPartially;

    public HotelController(ObjectMapper objectMapper, AddRoom addRoom, BookRoom bookRoom, DeleteRoom deleteRoom, GetAvailableRooms getAvailableRooms,
                           GetRoomInfoById getRoomInfoById, UnbookRoom unbookRoom, RegisterVisitor registerVisitor,
                           ReportVisitorsInfo reportVisitorsInfo, UpdateRoom updateRoom, UpdateRoomPartially updateRoomPartially) {
        super(objectMapper);
        this.addRoom = addRoom;
        this.bookRoom = bookRoom;
        this.deleteRoom = deleteRoom;
        this.getAvailableRooms = getAvailableRooms;
        this.getRoomInfoById = getRoomInfoById;
        this.unbookRoom = unbookRoom;
        this.registerVisitor = registerVisitor;
        this.reportVisitorsInfo = reportVisitorsInfo;
        this.updateRoom = updateRoom;
        this.updateRoomPartially = updateRoomPartially;
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

    @Operation(summary = "Delete room.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    @DeleteMapping(RestApiRoutes.SYSTEM_DELETE_ROOM)
    public ResponseEntity<?> deleteRoom(@PathVariable("roomId") String roomId){

        DeleteRoomBffInput input = DeleteRoomBffInput.builder()
            .id(roomId)
            .build();

        Either<ErrorWrapper, DeleteRoomBffOutput> output = deleteRoom.process(input);
        return handleResult(output,HttpStatus.OK);
    }

    @Operation(summary = "Get available rooms by criteria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping(RestApiRoutes.HOTEL_GET_AVAILABLE_ROOMS)
    public ResponseEntity<?> getAvailableRooms(@RequestParam(value = "startDate") LocalDate startDate,
                                               @RequestParam(value = "endDate") LocalDate endDate,
                                               @RequestParam(value = "bedCount") Integer bedCount,
                                               @RequestParam(value = "bedSize") List<String> beds,
                                               @RequestParam(value = "bathroomType") String bathroomType) {
        GetAvailableRoomsBffInput input = GetAvailableRoomsBffInput.builder()
            .startDate(startDate)
            .endDate(endDate)
            .bedCount(bedCount)
            .bathroomType(bathroomType)
            .beds(beds)
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

    @Operation(summary = "Register a guest in existing booking.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PostMapping(RestApiRoutes.SYSTEM_REGISTER_VISITOR)
    public ResponseEntity<?> register(@RequestBody RegisterVisitorBffInput registerVisitorBffInput) {

        RegisterVisitorBffInput input = RegisterVisitorBffInput.builder()
            .idCardIssueDate(registerVisitorBffInput.getIdCardIssueDate())
            .startDate(registerVisitorBffInput.getStartDate())
            .endDate(registerVisitorBffInput.getEndDate())
            .firstName(registerVisitorBffInput.getFirstName())
            .lastName(registerVisitorBffInput.getLastName())
            .idCardIssueAuthority(registerVisitorBffInput.getIdCardIssueAuthority())
            .idCardValidity(registerVisitorBffInput.getIdCardValidity())
            .idCardNo(registerVisitorBffInput.getIdCardNo())
            .phoneNo(registerVisitorBffInput.getPhoneNo())
            .birthDate(registerVisitorBffInput.getBirthDate())
            .roomNo(registerVisitorBffInput.getRoomNo())
            .build();

        Either<ErrorWrapper, RegisterVisitorBffOutput> output = registerVisitor.process(input);
        return handleResult(output,HttpStatus.CREATED);
    }

    @Operation(summary = "Get basic info by various criteria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping(RestApiRoutes.SYSTEM_REPORT_VISITOR_INFO)
    public ResponseEntity<?> report(
        @RequestParam(required = false) Optional<LocalDate> startDate,
        @RequestParam(required = false) Optional<LocalDate> endDate,
        @RequestParam(required = false) Optional<String> firstName,
        @RequestParam(required = false) Optional<String> lastName,
        @RequestParam(required = false) Optional<String> phoneNo,
        @RequestParam(required = false) Optional<String> idCardNo,
        @RequestParam(required = false) Optional<LocalDate> idCardValidity,
        @RequestParam(required = false) Optional<String> idCardIssueAuthority,
        @RequestParam(required = false) Optional<LocalDate> cardIssueDate,
        @RequestParam String roomNo
    ) {
        ReportVisitorsInfoBffInput input = ReportVisitorsInfoBffInput.builder()
            .endDate(endDate.orElse(null))
            .firstName(firstName.orElse(null))
            .idCardIssueAuthority(idCardIssueAuthority.orElse(null))
            .idCardNo(idCardNo.orElse(null))
            .idCardValidity(idCardValidity.orElse(null))
            .lastName(lastName.orElse(null))
            .phoneNo(phoneNo.orElse(null))
            .startDate(startDate.orElse(null))
            .cardIssueDate(cardIssueDate.orElse(null))
            .roomNo(roomNo)
            .build();

        Either<ErrorWrapper, ReportVisitorsInfoBffOutput> output = reportVisitorsInfo.process(input);
        return handleResult(output,HttpStatus.OK);
    }

    @Operation(summary = "Update room.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PutMapping(RestApiRoutes.SYSTEM_UPDATE_ROOM)
    public ResponseEntity<?> updateRoom(@RequestBody UpdateRoomBffInput updateRoomBffInput,
                                        @PathVariable("roomId") String roomId) {

        UpdateRoomBffInput input = UpdateRoomBffInput.builder()
            .id(roomId)
            .bedCount(updateRoomBffInput.getBedCount())
            .bathroomType(updateRoomBffInput.getBathroomType())
            .floor(updateRoomBffInput.getFloor())
            .price(updateRoomBffInput.getPrice())
            .roomNo(updateRoomBffInput.getRoomNo())
            .beds(updateRoomBffInput.getBeds())
            .build();

        Either<ErrorWrapper, UpdateRoomBffOutput> output = updateRoom.process(input);
        return handleResult(output,HttpStatus.OK);
    }

    @Operation(summary = "Update room partially.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @PatchMapping(RestApiRoutes.SYSTEM_UPDATE_ROOM_PARTIALLY)
    public ResponseEntity<?> updateRoomPartially(@PathVariable("roomId") String roomId,
                                                 @RequestBody UpdateRoomPartiallyBffInput updateRoomPartiallyBffInput) {

        UpdateRoomPartiallyBffInput input = UpdateRoomPartiallyBffInput.builder()
            .id(roomId)
            .bedCount(updateRoomPartiallyBffInput.getBedCount())
            .bathroomType(updateRoomPartiallyBffInput.getBathroomType())
            .floor(updateRoomPartiallyBffInput.getFloor())
            .roomNo(updateRoomPartiallyBffInput.getRoomNo())
            .price(updateRoomPartiallyBffInput.getPrice())
            .beds(updateRoomPartiallyBffInput.getBeds())
            .build();

        Either<ErrorWrapper, UpdateRoomPartiallyBffOutput> output = updateRoomPartially.process(input);
        return new ResponseEntity<>(output,HttpStatus.OK);
    }
}
