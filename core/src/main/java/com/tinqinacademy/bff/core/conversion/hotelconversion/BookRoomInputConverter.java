package com.tinqinacademy.bff.core.conversion.hotelconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomInputConverter extends BaseConverter<BookRoomBffInput, BookRoomInput> {

    @Override
    protected BookRoomInput convertObject(BookRoomBffInput source) {
        return BookRoomInput.builder()
            .roomId(source.getRoomId())
            .userId(source.getUserId())
            .startDate(source.getStartDate())
            .endDate(source.getEndDate())
            .build();
    }
}
