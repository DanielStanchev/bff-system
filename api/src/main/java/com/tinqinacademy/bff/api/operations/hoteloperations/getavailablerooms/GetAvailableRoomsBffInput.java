package com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms;

import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.customvalidation.hotelvalidations.bathroomtypevalidation.BathroomTypeValidation;
import com.tinqinacademy.bff.api.customvalidation.hotelvalidations.bedsizevalidation.BedSizeValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GetAvailableRoomsBffInput implements OperationInput {

    @NotNull(message = "Start date cannot be null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date cannot be null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Bed count cannot be null.")
    @Min(value = 1, message = "Min bed count should be no more than 1")
    @Max(value = 5, message = "Max bed count should be no more than 5")
    private Integer bedCount;

    @NotNull(message = "Bathroom type cannot be null.")
    @BathroomTypeValidation
    private String bathroomType;

    @NotEmpty(message = "Bed sizes should correspond to bed count.")
    @Builder.Default
    private List<@BedSizeValidation @Valid String> beds = new ArrayList<>();
}
