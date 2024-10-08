package com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid;

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
public class GetRoomDatesOccupiedBffInfo {

    private String startDate;
    private String endDate;
}
