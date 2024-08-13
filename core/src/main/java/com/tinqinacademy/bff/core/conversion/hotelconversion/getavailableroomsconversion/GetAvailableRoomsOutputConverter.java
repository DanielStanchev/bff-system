package com.tinqinacademy.bff.core.conversion.hotelconversion.getavailableroomsconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.getavailablerooms.GetAvailableRoomsOutput;
import org.springframework.stereotype.Component;

@Component
public class GetAvailableRoomsOutputConverter extends BaseConverter<GetAvailableRoomsOutput, GetAvailableRoomsBffOutput> {
    @Override
    protected GetAvailableRoomsBffOutput convertObject(GetAvailableRoomsOutput source) {
        return GetAvailableRoomsBffOutput.builder()
            .roomIds(source.getRoomIds())
            .build();
    }
}
