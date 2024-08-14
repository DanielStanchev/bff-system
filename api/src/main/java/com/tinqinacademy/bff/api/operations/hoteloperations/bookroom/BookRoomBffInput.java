package com.tinqinacademy.bff.api.operations.hoteloperations.bookroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookRoomBffInput implements OperationInput {

    @JsonIgnore
    @NotBlank(message = "Room ID cannot be blank.")
    private String roomId;

    @NotNull(message = "Start date cannot be null.")
    @FutureOrPresent(message = "Start date should be present or future.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null.")
    @FutureOrPresent(message = "Start date should be present or future.")
    @JsonFormat(pattern = "yyyy-MM-dd",shape=JsonFormat.Shape.STRING)
    private LocalDate endDate;

    @JsonIgnore
    @NotBlank(message = "User ID cannot be blank.")
    private String userId;
}
