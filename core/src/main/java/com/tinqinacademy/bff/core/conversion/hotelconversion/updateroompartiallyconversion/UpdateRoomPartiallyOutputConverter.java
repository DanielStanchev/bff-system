package com.tinqinacademy.bff.core.conversion.hotelconversion.updateroompartiallyconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.updateroompartially.UpdateRoomPartiallyBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.updateroompartially.UpdateRoomPartiallyOutput;
import org.springframework.stereotype.Component;

@Component
public class UpdateRoomPartiallyOutputConverter extends BaseConverter<UpdateRoomPartiallyOutput, UpdateRoomPartiallyBffOutput> {
    @Override
    protected UpdateRoomPartiallyBffOutput convertObject(UpdateRoomPartiallyOutput source) {
        return UpdateRoomPartiallyBffOutput.builder()
            .id(source.getId())
            .build();
    }
}
