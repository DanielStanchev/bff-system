package com.tinqinacademy.bff.core.conversion.hotelconversion.registervisitorconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffOutput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import org.springframework.stereotype.Component;

@Component
public class RegisterVisitorOutputConverter extends BaseConverter<RegisterVisitorOutput, RegisterVisitorBffOutput> {
    @Override
    protected RegisterVisitorBffOutput convertObject(RegisterVisitorOutput source) {
        return RegisterVisitorBffOutput.builder()
            .build();
    }
}
