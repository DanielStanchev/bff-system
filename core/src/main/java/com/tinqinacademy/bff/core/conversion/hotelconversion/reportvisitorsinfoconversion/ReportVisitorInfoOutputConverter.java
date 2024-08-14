package com.tinqinacademy.bff.core.conversion.hotelconversion.reportvisitorsinfoconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsBffInfoOutputInfo;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.reportvisitorinfo.ReportVisitorsInfoOutput;
import org.springframework.stereotype.Component;

@Component
public class ReportVisitorInfoOutputConverter extends BaseConverter<ReportVisitorsInfoOutput, ReportVisitorsInfoBffOutput> {

    @Override
    protected ReportVisitorsInfoBffOutput convertObject(ReportVisitorsInfoOutput source) {

        return ReportVisitorsInfoBffOutput.builder()
            .visitorsReport(source.getVisitorsReport()
                                .stream()
                                .map(visitorreport -> ReportVisitorsBffInfoOutputInfo.builder()
                                    .startDate(visitorreport.getStartDate())
                                    .endDate(visitorreport.getEndDate())
                                    .firstName(visitorreport.getFirstName())
                                    .lastName(visitorreport.getLastName())
                                    .phoneNo(visitorreport.getPhoneNo())
                                    .idCardNo(visitorreport.getIdCardNo())
                                    .idCardValidity(visitorreport.getIdCardValidity())
                                    .idCardIssueAuthority(visitorreport.getIdCardIssueAuthority())
                                    .cardIssueDate(visitorreport.getCardIssueDate())
                                    .build())
                                .toList())
            .build();
    }
}
