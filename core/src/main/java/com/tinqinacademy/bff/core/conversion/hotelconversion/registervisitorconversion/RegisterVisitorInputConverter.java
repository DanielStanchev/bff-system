package com.tinqinacademy.bff.core.conversion.hotelconversion.registervisitorconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.registervisitor.RegisterVisitorBffInput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import org.springframework.stereotype.Component;

@Component
public class RegisterVisitorInputConverter extends BaseConverter<RegisterVisitorBffInput, RegisterVisitorInput> {
    @Override
    protected RegisterVisitorInput convertObject(RegisterVisitorBffInput source) {
        return RegisterVisitorInput.builder()
            .startDate(source.getStartDate())
            .endDate(source.getEndDate())
            .firstName(source.getFirstName())
            .lastName(source.getLastName())
            .phoneNo(source.getPhoneNo())
            .idCardNo(source.getIdCardNo())
            .idCardValidity(source.getIdCardValidity())
            .idCardIssueAuthority(source.getIdCardIssueAuthority())
            .idCardIssueDate(source.getIdCardIssueDate())
            .build();
    }
}
