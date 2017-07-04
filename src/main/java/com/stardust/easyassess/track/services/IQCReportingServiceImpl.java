package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.statistics.*;
import com.stardust.easyassess.track.reports.PeriodGatherStatisticExcelGridReport;
import com.stardust.easyassess.track.reports.PeriodUnitStatisticExcelGridReport;
import com.stardust.easyassess.track.reports.PeriodUnitsStatisticExcelGridReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
@Scope("request")
public class IQCReportingServiceImpl implements IQCReportingService {

    @Autowired
    private IQCPlanTemplateService templateService;

    @Autowired
    private IQCPlanService planService;


    @Override
    public void generatePeriodicalGatherStatisticReport(String templateId, String targetDate, int count, Map<String, String> filters, OutputStream outputStream) throws Exception {
        Date target = new Date();
        if (!targetDate.isEmpty()) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            target = formatter.parse(targetDate);
        }
        IQCHistoryGatherStatisticModel model
                = templateService.getGatherStatisticData(templateId, target, count, filters);

        new PeriodGatherStatisticExcelGridReport(outputStream, model).generate();
    }

    @Override
    public void generatePeriodicalUnitsStatisticReport(String templateId, String targetDate, int count, Map<String, String> filters, OutputStream outputStream) throws Exception {
        Date target = new Date();
        if (!targetDate.isEmpty()) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            target = formatter.parse(targetDate);
        }

        Map<String, IQCHistoryUnitStatisticModel> model
                = templateService.getUnitStatisticCollection(templateId, target, count, filters);
        new PeriodUnitsStatisticExcelGridReport(outputStream, model).generate();
    }

    @Override
    public void generatePeriodicalUnitStatisticReport(String planId, String targetDate, int count, Map<String, String> filters, OutputStream outputStream) throws Exception {
        Date target = new Date();
        if (!targetDate.isEmpty()) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            target = formatter.parse(targetDate);
        }

        IQCPlan plan = planService.get(planId);

        IQCHistoryGatherStatisticModel gatherModel
                = templateService.getGatherStatisticData(plan.getTemplate().getId(), target, count, filters);

        IQCHistoryUnitStatisticModel unitModel
                = planService.getUnitStatistic(planId, target, count, filters);

        new PeriodUnitStatisticExcelGridReport(outputStream, unitModel, gatherModel).generate();
    }
}
