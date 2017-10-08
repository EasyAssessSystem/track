package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanGroup;
import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.track.services.IQCPlanGroupService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/iqc/group"})
@EnableAutoConfiguration
public class IQCPlanGroupController extends MaintenanceController<IQCPlanGroup> {
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    protected EntityService<IQCPlanGroup> getService() {
        return getApplicationContext().getBean(IQCPlanGroupService.class);
    }

    @Override
    protected boolean preAdd(IQCPlanGroup model) throws Exception {
        Owner owner = getOwner();
        if (owner != null) {
            model.setOwner(owner);
        }
        return super.preAdd(model);
    }

    @Override
    protected boolean preUpdate(String id, IQCPlanGroup model) throws Exception {
        Owner owner = getOwner();
        if (!model.getOwner().equals(owner)) {
            return false;
        }
        IQCPlanGroup group = getService().get(id);
        if (group != null) {
            model.setId(id);
            model.setOwner(group.getOwner());
            model.setTemplate(group.getTemplate());
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

    @RequestMapping(path="/{id}/plan",
            method={RequestMethod.POST})
    public ViewJSONWrapper createPlan(@RequestParam(value = "name") String name,  @PathVariable String id) throws Exception {
        IQCPlanGroup group = getService().get(id);
        IQCPlanService planService = applicationContext.getBean(IQCPlanService.class);
        IQCPlan plan = new IQCPlan(group.getTemplate(), group.getOwner());
        plan.setName(name);
        plan.setGroup(group);
        planService.save(plan);
        group.getPlans().add(plan);
        getService().save(group);
        return new ViewJSONWrapper(plan);
    }

    @RequestMapping(path="/{id}/plan/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper planList(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "4") Integer size,
                                    @RequestParam(value = "sort", defaultValue = "id") String sort,
                                    @RequestParam(value = "filterField", defaultValue = "") String field,
                                    @RequestParam(value = "filterValue", defaultValue = "") String value ) throws Exception {
        List<Selection> selections = new ArrayList();
        selections.add(new Selection("group.id", Selection.Operator.EQUAL, id));
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        return new ViewJSONWrapper(applicationContext.getBean(IQCPlanService.class).list(page, size , sort, selections));
    }
}
