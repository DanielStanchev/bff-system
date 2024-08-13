package com.tinqinacademy.bff.core.conversion.hotelconversion.getroominfobyidconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.getroominfobyid.GetRoomInfoByIdInput;
import org.springframework.stereotype.Component;

@Component
public class GetRoomInfoByIdInputConverter extends BaseConverter<GetRoomInfoByIdBffInput, GetRoomInfoByIdInput> {
    @Override
    protected GetRoomInfoByIdInput convertObject(GetRoomInfoByIdBffInput source) {
        return GetRoomInfoByIdInput.builder()
            .roomId(source.getRoomId())
            .build();
    }
}
