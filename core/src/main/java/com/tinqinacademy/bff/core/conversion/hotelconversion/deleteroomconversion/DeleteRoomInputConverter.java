package com.tinqinacademy.bff.core.conversion.hotelconversion.deleteroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import org.springframework.stereotype.Component;

@Component
public class DeleteRoomInputConverter extends BaseConverter<DeleteRoomBffInput, DeleteRoomInput> {
    @Override
    protected DeleteRoomInput convertObject(DeleteRoomBffInput source) {
        return DeleteRoomInput.builder()
            .id(source.getId()).build();
    }
}
