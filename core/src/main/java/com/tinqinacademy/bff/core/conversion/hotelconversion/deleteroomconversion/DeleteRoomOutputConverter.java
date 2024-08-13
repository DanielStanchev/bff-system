package com.tinqinacademy.bff.core.conversion.hotelconversion.deleteroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.deleteroom.DeleteRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class DeleteRoomOutputConverter extends BaseConverter<DeleteRoomOutput, DeleteRoomBffOutput> {
    @Override
    protected DeleteRoomBffOutput convertObject(DeleteRoomOutput source) {
        return DeleteRoomBffOutput.builder()
            .build();
    }
}
