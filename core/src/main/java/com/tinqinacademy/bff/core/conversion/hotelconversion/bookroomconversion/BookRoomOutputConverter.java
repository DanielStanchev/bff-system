package com.tinqinacademy.bff.core.conversion.hotelconversion.bookroomconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.bookroom.BookRoomBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import org.springframework.stereotype.Component;

@Component
public class BookRoomOutputConverter extends BaseConverter<BookRoomOutput, BookRoomBffOutput> {
    @Override
    protected BookRoomBffOutput convertObject(BookRoomOutput source) {
        return BookRoomBffOutput.builder()
            .build();
    }
}
