package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.track.services.IQCPlanService;
import com.stardust.easyassess.track.services.IQCPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


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
            model.setRecords(plan.getRecords());
        }
        return super.preUpdate(id, model);
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

    @RequestMapping(path="/{id}/record",
            method={RequestMethod.POST})
    public ViewJSONWrapper createRecord(@PathVariable String id, @RequestBody IQCPlanRecord record) throws ESAppException, ParseException {
        return new ViewJSONWrapper(((IQCPlanService)getService()).submitRecord(id, record, getOwner()));
    }

    @RequestMapping(path="/{id}/record",
            method={RequestMethod.GET})
    public ViewJSONWrapper getTodayRecord(@PathVariable String id) throws ESAppException, ParseException {
        return new ViewJSONWrapper(((IQCPlanService)getService()).getTodayRecord(id));
    }
}
