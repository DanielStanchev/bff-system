package com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially;

import com.tinqinacademy.bff.api.base.OperationOutput;
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
public class UpdateRoomPartiallyBffOutput implements OperationOutput {
    String id;
}
