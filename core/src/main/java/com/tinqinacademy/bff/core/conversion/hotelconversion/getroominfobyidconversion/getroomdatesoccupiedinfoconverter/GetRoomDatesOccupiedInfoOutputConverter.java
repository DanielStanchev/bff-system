package com.tinqinacademy.bff.core.conversion.hotelconversion.getroominfobyidconversion.getroomdatesoccupiedinfoconverter;

import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomDatesOccupiedBffInfo;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.getroominfobyid.GetRoomDatesOccupiedInfo;
import org.springframework.stereotype.Component;

@Component
public class GetRoomDatesOccupiedInfoOutputConverter extends BaseConverter<GetRoomDatesOccupiedInfo, GetRoomDatesOccupiedBffInfo> {
    @Override
    protected GetRoomDatesOccupiedBffInfo convertObject(GetRoomDatesOccupiedInfo source) {
        return GetRoomDatesOccupiedBffInfo.builder()
            .startDate(source.getStartDate())
            .endDate(source.getEndDate())
            .build();
    }
}
