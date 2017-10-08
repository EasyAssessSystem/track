package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCHistorySet;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/iqc/template"})
@EnableAutoConfiguration
public class IQCPlanTemplateController extends MaintenanceController<IQCPlanTemplate> {
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    protected EntityService<IQCPlanTemplate> getService() {
        return getApplicationContext().getBean(IQCPlanTemplateService.class);
    }

    @Override
    protected boolean preAdd(IQCPlanTemplate model) throws Exception {
        Owner owner = getNullableOwner();
        if (owner != null) {
            model.setOwner(owner);
        }
        return super.preAdd(model);
    }

    @Override
    protected boolean preUpdate(String id, IQCPlanTemplate model) throws Exception {
        Owner owner = getNullableOwner();
        if (owner != null && !model.getOwner().equals(owner)) {
            return false;
        }
        return super.preAdd(model);
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

    @RequestMapping(path="/list/available",
            method={RequestMethod.GET})
    public ViewJSONWrapper availableTemplates(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "4") Integer size,
                                @RequestParam(value = "sort", defaultValue = "id") String sort,
                                @RequestParam(value = "filterField", defaultValue = "") String field,
                                @RequestParam(value = "filterValue", defaultValue = "") String value ) throws Exception {

        List<Selection> selections = new ArrayList<Selection>();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        return postList(getService().list(page, size , sort, selections));
    }

    @RequestMapping(path="/{id}/plan/{owner}/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper planList(@PathVariable String id, @PathVariable String owner, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "4") Integer size,
                                              @RequestParam(value = "sort", defaultValue = "id") String sort,
                                              @RequestParam(value = "filterField", defaultValue = "") String field,
                                              @RequestParam(value = "filterValue", defaultValue = "") String value ) throws Exception {
        List<Selection> selections = new ArrayList();
        selections.add(new Selection("template.id", Selection.Operator.EQUAL, id));
        selections.add(new Selection("owner.id", Selection.Operator.EQUAL, owner));
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        return new ViewJSONWrapper(applicationContext.getBean(IQCPlanService.class).list(page, size , sort, selections));
    }

    @RequestMapping(path="/{id}/group/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper groupList(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "4") Integer size,
                                    @RequestParam(value = "sort", defaultValue = "id") String sort,
                                    @RequestParam(value = "filterField", defaultValue = "") String field,
                                    @RequestParam(value = "filterValue", defaultValue = "") String value ) throws Exception {
        List<Selection> selections = new ArrayList();
        selections.add(new Selection("template.id", Selection.Operator.EQUAL, id));
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        return new ViewJSONWrapper(applicationContext.getBean(IQCPlanGroupService.class).list(page, size , sort, selections));
    }

    @RequestMapping(path="/{id}/record/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper recordList(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "4") Integer size,
                                    @RequestParam(value = "sort", defaultValue = "id") String sort,
                                    @RequestParam(value = "filterField", defaultValue = "") String field,
                                    @RequestParam(value = "filterValue", defaultValue = "") String value,
                                    @RequestParam(name = "targetDate", defaultValue = "") String targetDate,
                                    @RequestParam(name = "count", defaultValue = "20") Integer count) throws Exception {

        List<Selection> selections = new ArrayList();
        selections.add(new Selection("template.id", Selection.Operator.EQUAL, id));
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        Page<IQCPlan> planPage = applicationContext.getBean(IQCPlanService.class).list(page, size , sort, selections);

        Date target = new Date();
        if (!targetDate.isEmpty()) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            target = formatter.parse(targetDate);
        }

        Sort sortBy = new Sort(Sort.Direction.ASC, sort);
        Pageable pageRequest = new PageRequest(page, size, sortBy);
        return new ViewJSONWrapper(new PageImpl(((IQCPlanTemplateService)getService()).getRecordsWithPlans(planPage.getContent(), target), pageRequest, planPage.getTotalElements()));
    }

    @RequestMapping(path="/{id}/statistic",
            method={RequestMethod.POST})
    public void getStatisticData(@PathVariable String id,
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
        reportingService.generatePeriodicalGatherStatisticReport(id, targetDate, count, filters, response.getOutputStream());
    }


    @RequestMapping(path="/{id}/statistic/units",
            method={RequestMethod.POST})
    public void getPlansStatisticData(@PathVariable String id,
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
        reportingService.generatePeriodicalUnitsStatisticReport(id, targetDate, count, filters, response.getOutputStream());
    }
}
