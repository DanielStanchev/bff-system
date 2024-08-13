package com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid;

import com.tinqinacademy.bff.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GetRoomInfoByIdBffOutput implements OperationOutput {

    private String id;
    private BigDecimal price;
    private Integer floor;
    private String bathroomType;
    private Integer bedCount;
    private List<String> beds;
    private List<GetRoomDatesOccupiedBffInfo> getRoomDatesOccupiedBffInfo;
}
