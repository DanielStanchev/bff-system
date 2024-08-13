package com.tinqinacademy.bff.core.conversion.hotelconversion.reportvisitorsinfoconversion;

import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsBffInfoOutputInfo;
import com.tinqinacademy.bff.api.operations.hoteloperations.reportvisitorinfo.ReportVisitorsInfoBffOutput;
import com.tinqinacademy.bff.core.base.BaseConverter;
import com.tinqinacademy.hotel.api.operations.visitorreportinfo.ReportVisitorsInfoOutput;
import com.tinqinacademy.hotel.api.operations.visitorreportinfo.ReportVisitorsInfoOutputInfo;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ReportVisitorInfoOutputConverter extends BaseConverter<ReportVisitorsInfoOutput, ReportVisitorsInfoBffOutput> {

    private final ConversionService conversionService;

    public ReportVisitorInfoOutputConverter(ConversionService conversionService) {this.conversionService = conversionService;}

    @Override
    protected ReportVisitorsInfoBffOutput convertObject(ReportVisitorsInfoOutput source) {

        List<ReportVisitorsInfoOutputInfo> visitorsInfo = source.getVisitorsReport();
        List<ReportVisitorsBffInfoOutputInfo> visitorsBffInfo =
            Collections.singletonList(conversionService.convert(visitorsInfo, ReportVisitorsBffInfoOutputInfo.class));

        return ReportVisitorsInfoBffOutput.builder()
            .visitorsReport(visitorsBffInfo)
            .build();
    }
}
