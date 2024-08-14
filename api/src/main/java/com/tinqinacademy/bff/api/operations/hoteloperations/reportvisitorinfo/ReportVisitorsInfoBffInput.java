package com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReportVisitorsInfoBffInput implements OperationInput {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 symbols.")
    private String firstName;

    @Size(min = 2,max = 30,message = "Last name should be between 2 and 30 symbols.")
    private String lastName;

    @Size(min = 2,max = 30,message = "Phone number should be between 2 and 30 symbols.")
    private String phoneNo;

    @Size(min = 2, max = 30,message = "ID card number should be between 2 and 30 symbols.")
    private String idCardNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate idCardValidity;

    private String idCardIssueAuthority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate cardIssueDate;

    @NotNull(message = "Room number cannot be null.")
    private String roomNo;
}
