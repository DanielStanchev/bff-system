package com.tinqinacademy.bff.core.conversion.hotelconversion.getroominfobyidconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomDatesOccupiedBffInfo;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.getroominfobyid.GetRoomInfoByIdOutput;
import org.springframework.stereotype.Component;

@Component
public class GetRoomInfoByIdOutputConverter extends BaseConverter<GetRoomInfoByIdOutput, GetRoomInfoByIdBffOutput> {

    @Override
    protected GetRoomInfoByIdBffOutput convertObject(GetRoomInfoByIdOutput source) {

        return GetRoomInfoByIdBffOutput.builder()
            .id(source.getId())
            .price(source.getPrice())
            .floor(source.getFloor())
            .bathroomType(source.getBathroomType())
            .bedCount(source.getBedCount())
            .beds(source.getBeds())
            .getRoomDatesOccupiedBffInfo(
                source.getGetRoomDatesOccupiedInfo().stream()
                    .map(roomDateOccupied ->
                             GetRoomDatesOccupiedBffInfo.builder()
                                 .startDate(roomDateOccupied.getStartDate())
                                 .endDate(roomDateOccupied.getEndDate())
                                 .build()
                    )
                    .toList()
            )
            .build();
    }
}
