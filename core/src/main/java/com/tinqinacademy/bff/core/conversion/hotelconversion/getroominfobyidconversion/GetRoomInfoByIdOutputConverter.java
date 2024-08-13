package com.tinqinacademy.bff.core.conversion.hotelconversion.getroominfobyidconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomDatesOccupiedBffInfo;
import com.tinqinacademy.bff.api.operations.hoteloperations.getroominfobyid.GetRoomInfoByIdBffOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.getroominfobyid.GetRoomDatesOccupiedInfo;
import com.tinqinacademy.hotel.api.operations.getroominfobyid.GetRoomInfoByIdOutput;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GetRoomInfoByIdOutputConverter extends BaseConverter<GetRoomInfoByIdOutput, GetRoomInfoByIdBffOutput> {

    private final ConversionService conversionService;

    public GetRoomInfoByIdOutputConverter(ConversionService conversionService) {this.conversionService = conversionService;}

    @Override
    protected GetRoomInfoByIdBffOutput convertObject(GetRoomInfoByIdOutput source) {

        List<GetRoomDatesOccupiedInfo> occupiedRoomDatesOutput = source.getGetRoomDatesOccupiedInfo();

        List<GetRoomDatesOccupiedBffInfo> occupiedRoomDatedBffOutput =
            Collections.singletonList(conversionService.convert(occupiedRoomDatesOutput, GetRoomDatesOccupiedBffInfo.class));

        return GetRoomInfoByIdBffOutput.builder()
            .id(source.getId())
            .price(source.getPrice())
            .floor(source.getFloor())
            .bathroomType(source.getBathroomType())
            .bedCount(source.getBedCount())
            .getRoomDatesOccupiedBffInfo(occupiedRoomDatedBffOutput)
            .beds(source.getBeds())
            .build();
    }
}
