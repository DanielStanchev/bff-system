package com.tinqinacademy.bff.core.conversion.hotelconversion.updateroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomInputConverter extends BaseConverter<UpdateRoomBffInput, UpdateRoomInput> {
    @Override
    protected UpdateRoomInput convertObject(UpdateRoomBffInput source) {
        return UpdateRoomInput.builder()
            .bedCount(source.getBedCount())
            .beds(source.getBeds())
            .bathroomType(source.getBathroomType())
            .floor(source.getFloor())
            .roomNo(source.getRoomNo())
            .price(source.getPrice())
            .build();
    }
}
