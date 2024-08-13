package com.tinqinacademy.bff.core.conversion.hotelconversion.updateroompartiallyconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.updateroompartially.UpdateRoomPartiallyInput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomPartiallyInputConverter extends BaseConverter<UpdateRoomPartiallyBffInput, UpdateRoomPartiallyInput> {
    @Override
    protected UpdateRoomPartiallyInput convertObject(UpdateRoomPartiallyBffInput source) {
        return UpdateRoomPartiallyInput.builder()
            .bedCount(source.getBedCount())
            .beds(source.getBeds())
            .bathroomType(source.getBathroomType())
            .floor(source.getFloor())
            .roomNo(source.getRoomNo())
            .price(source.getPrice())
            .build();
    }
}
