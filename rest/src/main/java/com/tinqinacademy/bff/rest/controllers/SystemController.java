package com.tinqinacademy.bff.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.bff.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitor;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfo;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoom;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartially;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffInput;
import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffOutput;
import com.tinqinacademy.bff.api.restapiroutes.RestApiRoutes;
import com.tinqinacademy.bff.rest.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@Tag(name = "System API", description = "System related functionality.")
@RestController
public class SystemController extends BaseController {

    private final DeleteRoom deleteRoom;
    private final AddRoom addRoom;
    private final RegisterVisitor registerVisitor;
    private final ReportVisitorsInfo reportVisitorsInfo;
    private final UpdateRoom updateRoom;
    private final UpdateRoomPartially updateRoomPartially;

    public SystemController(ObjectMapper objectMapper, DeleteRoom deleteRoom, AddRoom addRoom, RegisterVisitor registerVisitor, ReportVisitorsInfo reportVisitorsInfo,
                            UpdateRoom updateRoom,
                            UpdateRoomPartially updateRoomPartially) {
        super(objectMapper);
        this.deleteRoom = deleteRoom;
        this.addRoom = addRoom;
        this.registerVisitor = registerVisitor;
        this.reportVisitorsInfo = reportVisitorsInfo;
        this.updateRoom = updateRoom;
        this.updateRoomPartially = updateRoomPartially;
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
        return handleResult(output, HttpStatus.CREATED);
    }

    @Operation(summary = "Report room info by various criteria.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping(RestApiRoutes.SYSTEM_REPORT_VISITOR_INFO)
    public ResponseEntity<?> report(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
        @RequestParam(required = false) Optional<String> firstName,
        @RequestParam(required = false) Optional<String> lastName,
        @RequestParam(required = false) Optional<String> phoneNo,
        @RequestParam(required = false) Optional<String> idCardNo,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> idCardValidity,
        @RequestParam(required = false) Optional<String> idCardIssueAuthority,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> cardIssueDate,
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
        return handleResult(output,HttpStatus.OK);
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

}
