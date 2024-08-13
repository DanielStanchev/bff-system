package com.tinqinacademy.bff.core.conversion.hotelconversion.unbookroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomInputConverter extends BaseConverter<UnbookRoomBffInput, UnbookRoomInput> {
    @Override
    protected UnbookRoomInput convertObject(UnbookRoomBffInput source) {
        return UnbookRoomInput.builder()
            .id(source.getId()).build();
    }
}
