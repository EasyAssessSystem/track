package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticSet;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.track.services.IQCPlanService;
import com.stardust.easyassess.track.services.IQCReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/iqc/plan"})
@EnableAutoConfiguration
public class IQCPlanController extends MaintenanceController<IQCPlan> {
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    protected EntityService<IQCPlan> getService() {
        return getApplicationContext().getBean(IQCPlanService.class);
    }

    @Override
    protected boolean preAdd(IQCPlan model) throws Exception {
        Owner owner = getOwner();
        if (owner != null) {
            model.setOwner(owner);
        }
        return super.preAdd(model);
    }

    @Override
    protected boolean preUpdate(String id, IQCPlan model) throws Exception {
        Owner owner = getOwner();
        if (!model.getOwner().equals(owner)) {
            return false;
        }
        IQCPlan plan = getService().get(id);
        if (plan != null) {
            model.setId(id);
            model.setOwner(plan.getOwner());
            model.setGroup(plan.getGroup());
            model.setTemplate(plan.getTemplate());
            model.setRecords(plan.getRecords());
        }
        return super.preUpdate(id, model);
    }

    @Override
    protected void postDelete(String id) {

    }

    @Override
    protected boolean preList(List<Selection> selections) throws ESAppException {
        Owner owner = getNullableOwner();
        if (owner != null && owner.getId() != null && !owner.getId().isEmpty()) {
            selections.add(new Selection("owner.id", Selection.Operator.EQUAL, owner.getId()));
            selections.add(new Selection("owner", Selection.Operator.IS_NULL, null, Selection.Operand.OR));
        }
        return true;
    }

    @RequestMapping(path="/owner",
            method={RequestMethod.PUT})
    public ViewJSONWrapper updateOwner(@RequestBody Owner owner) throws ESAppException, ParseException {
        ((IQCPlanService)getService()).updateOwnerName(owner);
        return new ViewJSONWrapper(owner);
    }

    @RequestMapping(path="/{id}/record",
            method={RequestMethod.POST})
    public ViewJSONWrapper createRecord(@PathVariable String id, @RequestBody IQCPlanRecord record) throws ESAppException, ParseException {
        return new ViewJSONWrapper(((IQCPlanService)getService()).submitRecord(record.getDate(), id, record, getOwner()));
    }

    @RequestMapping(path="/{id}/record",
            method={RequestMethod.GET})
    public ViewJSONWrapper getTodayRecord(@PathVariable String id) throws ESAppException, ParseException {
        return new ViewJSONWrapper(((IQCPlanService)getService()).getTodayRecord(id));
    }

    @RequestMapping(path="/{id}/record/{date}",
            method={RequestMethod.GET})
    public ViewJSONWrapper getRecordByDate(@PathVariable String id, @PathVariable String date) throws ESAppException, ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new ViewJSONWrapper(((IQCPlanService)getService()).getRecord(id, formatter.parse(date)));
    }

    @RequestMapping(path="/{id}/records",
            method={RequestMethod.GET})
    public ViewJSONWrapper getRecordsWithinTargetDate(@PathVariable String id,
                                                      @RequestParam(name = "targetDate", defaultValue = "") String targetDate,
                                                      @RequestParam(name = "count", defaultValue = "30") Integer count) throws ESAppException, ParseException {

        Date target = new Date();
        if (!targetDate.isEmpty()) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            target = formatter.parse(targetDate);
        }
        return new ViewJSONWrapper(((IQCPlanService)getService()).getRecords(id, target, count));
    }

    @RequestMapping(path="/{id}/statistic",
            method={RequestMethod.POST})
    public ViewJSONWrapper getStatisticData(@PathVariable String id,
                                            @RequestBody Map<String, String> filters,
                                            @RequestParam(name = "targetDate", defaultValue = "") String targetDate,
                                            @RequestParam(name = "count", defaultValue = "30") Integer count) throws ESAppException, ParseException {
        Date target = new Date();
        if (!targetDate.isEmpty()) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            target = formatter.parse(targetDate);
        }

        return new ViewJSONWrapper(((IQCPlanService)getService()).getPeriodStatisticComparison(id, target, count, filters));
    }

    @RequestMapping(path="/{id}/statistic/excel",
            method={RequestMethod.POST})
    public void getStatisticReport(@PathVariable String id,
                                      @RequestBody Map<String, String> filters,
                                      @RequestParam(name = "targetDate", defaultValue = "") String targetDate,
                                      @RequestParam(name = "count", defaultValue = "30") Integer count,
                                      HttpServletResponse response) throws Exception {
        response.reset();
        response.setHeader("Content-disposition", "attachment;filename=" +  java.net.URLEncoder.encode("统计报表", "UTF-8") + ".xls");
        response.setContentType("application/msexcel");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        IQCReportingService reportingService = applicationContext.getBean(IQCReportingService.class);
        reportingService.generatePeriodicalUnitStatisticReport(id, targetDate, count, filters, response.getOutputStream());
    }
}
