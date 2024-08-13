package com.tinqinacademy.bff.core.conversion.hotelconversion.addroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class AddRoomOutputConverter extends BaseConverter<AddRoomOutput, AddRoomBffOutput> {

    @Override
    protected AddRoomBffOutput convertObject(AddRoomOutput source) {
        return AddRoomBffOutput.builder()
            .id(source.getId()).build();
    }
}

