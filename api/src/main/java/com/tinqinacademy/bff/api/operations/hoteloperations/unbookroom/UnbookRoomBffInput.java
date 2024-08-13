package com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UnbookRoomBffInput implements OperationInput {
    @JsonIgnore
    @NotBlank(message = "Room ID should not be blank! ")
    private String id;
}
