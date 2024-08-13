package com.tinqinacademy.bff.core.conversion.hotelconversion.updateroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.updateroom.UpdateRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomOutputConverter extends BaseConverter<UpdateRoomOutput, UpdateRoomBffOutput> {
    @Override
    protected UpdateRoomBffOutput convertObject(UpdateRoomOutput source) {
        return UpdateRoomBffOutput.builder()
            .id(source.getId())
            .build();
    }
}
