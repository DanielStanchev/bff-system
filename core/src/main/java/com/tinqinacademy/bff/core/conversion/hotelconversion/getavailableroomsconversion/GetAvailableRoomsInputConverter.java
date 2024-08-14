package com.tinqinacademy.bff.core.conversion.hotelconversion.getavailableroomsconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.getavailablerooms.GetAvailableRoomsBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.getavailablerooms.GetAvailableRoomsInput;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GetAvailableRoomsInputConverter extends BaseConverter<GetAvailableRoomsBffInput, GetAvailableRoomsInput> {
    @Override
    protected GetAvailableRoomsInput convertObject(GetAvailableRoomsBffInput source) {
        return GetAvailableRoomsInput.builder()
            .startDate(source.getStartDate())
            .endDate(source.getEndDate())
            .bedCount(source.getBedCount())
            .beds(source.getBeds())
            .bathroomType(source.getBathroomType())
            .build();
    }
}
