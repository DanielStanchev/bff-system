package com.tinqinacademy.bff.api.operations.hoteloperations.addroom;

import com.tinqinacademy.bff.api.base.OperationInput;
import com.tinqinacademy.bff.api.customvalidation.hotelvalidations.bathroomtypevalidation.BathroomTypeValidation;
import com.tinqinacademy.bff.api.customvalidation.hotelvalidations.bedsizevalidation.BedSizeValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class AddRoomBffInput implements OperationInput {

    @NotNull(message = "Bed count cannot be null.")
    @Min(value = 1, message = "Min bed count should be no more than 1")
    @Max(value = 5, message = "Max bed count should be no more than 5")
    private Integer bedCount;

    @NotNull(message = "Bathroom type cannot be null.")
    @BathroomTypeValidation
    private String bathroomType;

    @NotNull(message = "Floor cannot be null.")
    @Min(value = -2, message = "Lowest floor is -2.")
    @Max(value = 16, message = "Highest floor is 16.")
    private Integer floor;

    @NotNull(message = "Room number cannot be null.")
    @Size(min = 2, max = 20, message = "Room number should be between 2 and 20 symbols.")
    private String roomNo;

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price should be positive !")
    private BigDecimal price;

    @NotEmpty(message = "Bed sizes to insert should correspond to bed count.")
    @Builder.Default
    private List<@BedSizeValidation @Valid String> beds = new ArrayList<>();
}
