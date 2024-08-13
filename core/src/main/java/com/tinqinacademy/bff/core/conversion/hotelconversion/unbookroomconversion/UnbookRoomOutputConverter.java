package com.tinqinacademy.bff.core.conversion.hotelconversion.unbookroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.unbookroom.UnbookRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class UnbookRoomOutputConverter extends BaseConverter<UnbookRoomOutput, UnbookRoomBffOutput> {
    @Override
    protected UnbookRoomBffOutput convertObject(UnbookRoomOutput source) {
        return UnbookRoomBffOutput.builder()
            .build();
    }
}
