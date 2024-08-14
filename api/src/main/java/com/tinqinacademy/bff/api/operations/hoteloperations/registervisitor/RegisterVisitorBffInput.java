package com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
public class RegisterVisitorBffInput implements OperationInput {

    @NotNull(message = "Start date cannot be null.")
    @FutureOrPresent(message = "Start date should be present or future.")
    @JsonFormat(pattern = "yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null.")
    @FutureOrPresent(message = "End date should be present or future.")
    @JsonFormat(pattern = "yyyy-MM-dd",shape=JsonFormat.Shape.STRING)
    private LocalDate endDate;

    @NotNull(message = "First name cannot be null.")
    @Size(min = 2, max = 30,message = "First name should be between 2 and 30 symbols.")
    private String firstName;

    @NotNull(message = "Last name cannot be null.")
    @Size(min = 2, max = 30,message = "Last name should be between 2 and 30 symbols.")
    private String lastName;

    @FutureOrPresent(message = "ID card validity should be present or future.")
    @JsonFormat(pattern = "yyyy-MM-dd",shape=JsonFormat.Shape.STRING)
    private LocalDate idCardValidity;

    @Size(min = 2, max = 30,message = "ID card issue authority should be between 2 and 30 symbols.")
    private String idCardIssueAuthority;

    @Size(min = 2, max = 30,message = "ID card number should be between 2 and 30 symbols.")
    private String idCardNo;

    @PastOrPresent(message = "Card issue date should be past or present.")
    @JsonFormat(pattern = "yyyy-MM-dd",shape=JsonFormat.Shape.STRING)
    private LocalDate idCardIssueDate;

    @Size(min = 2, max = 30,message = "Phone number should be between 2 and 30 symbols.")
    private String phoneNo;

    @NotNull(message = "Room number cannot be null.")
    @Size(min = 2, max = 20,message = "Room number should be between 2 and 20 symbols.")
    private String roomNo;

    @NotNull(message = "Birth date cannot be null.")
    @Past(message = "Birth date should be in the past.")
    @JsonFormat(pattern = "yyyy-MM-dd",shape=JsonFormat.Shape.STRING)
    private LocalDate birthDate;
}
