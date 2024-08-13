package com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms;


import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GetAvailableRoomsBffOutput implements OperationOutput {
   private List<String> roomIds;
}
