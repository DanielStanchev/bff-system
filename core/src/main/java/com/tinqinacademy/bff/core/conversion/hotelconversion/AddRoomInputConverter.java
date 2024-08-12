package com.tinqinacademy.bff.core.conversion.hotelconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.addroom.AddRoomBffInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.addroom.AddRoomInput;
import org.springframework.stereotype.Component;

@Component
public class AddRoomInputConverter extends BaseConverter<AddRoomBffInput, AddRoomInput>{
    @Override
    protected AddRoomInput convertObject(AddRoomBffInput source) {

        return AddRoomInput.builder()
            .roomNo(source.getRoomNo())
            .beds(source.getBeds())
            .bathroomType(source.getBathroomType())
            .bedCount(source.getBedCount())
            .floor(source.getFloor())
            .price(source.getPrice()).build();
    }
}
