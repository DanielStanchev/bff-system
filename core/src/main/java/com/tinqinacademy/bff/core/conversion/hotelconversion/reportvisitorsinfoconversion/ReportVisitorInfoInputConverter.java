package com.tinqinacademy.bff.core.conversion.hotelconversion.reportvisitorsinfoconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffInput;
import com.tinqinacademy.bff.core.conversion.BaseConverter;
import com.tinqinacademy.hotel.api.operations.visitorreportinfo.ReportVisitorsInfoInput;
import org.springframework.stereotype.Component;

@Component
public class ReportVisitorInfoInputConverter extends BaseConverter<ReportVisitorsInfoBffInput, ReportVisitorsInfoInput> {
    @Override
    protected ReportVisitorsInfoInput convertObject(ReportVisitorsInfoBffInput source) {
        return ReportVisitorsInfoInput.builder()
            .startDate(source.getStartDate())
            .endDate(source.getEndDate())
            .firstName(source.getFirstName())
            .lastName(source.getLastName())
            .phoneNo(source.getPhoneNo())
            .idCardNo(source.getIdCardNo())
            .idCardValidity(source.getIdCardValidity())
            .idCardIssueAuthority(source.getIdCardIssueAuthority())
            .cardIssueDate(source.getCardIssueDate())
            .roomNo(source.getRoomNo())
            .build();
    }
}
