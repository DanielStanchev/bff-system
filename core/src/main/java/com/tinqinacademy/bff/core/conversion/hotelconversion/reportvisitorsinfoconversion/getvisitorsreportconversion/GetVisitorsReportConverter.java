package com.tinqinacademy.bff.core.conversion.hotelconversion.reportvisitorsinfoconversion.getvisitorsreportconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsBffInfoOutputInfo;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.visitorreportinfo.ReportVisitorsInfoOutputInfo;
import org.springframework.stereotype.Component;

@Component
public class GetVisitorsReportConverter extends BaseConverter<ReportVisitorsInfoOutputInfo, ReportVisitorsBffInfoOutputInfo> {
    @Override
    protected ReportVisitorsBffInfoOutputInfo convertObject(ReportVisitorsInfoOutputInfo source) {
        return ReportVisitorsBffInfoOutputInfo.builder()
            .startDate(source.getStartDate())
            .endDate(source.getEndDate())
            .firstName(source.getFirstName())
            .lastName(source.getLastName())
            .phoneNo(source.getPhoneNo())
            .idCardNo(source.getIdCardNo())
            .idCardValidity(source.getIdCardValidity())
            .idCardIssueAuthority(source.getIdCardIssueAuthority())
            .cardIssueDate(source.getCardIssueDate())
            .build();
    }
}
