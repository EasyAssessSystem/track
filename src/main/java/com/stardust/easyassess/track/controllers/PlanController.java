package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.track.models.Plan;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.form.Specimen;
import com.stardust.easyassess.track.services.PlanService;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.core.exception.MinistryOnlyException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.core.query.Selection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/iqc/plan"})
@EnableAutoConfiguration
public class PlanController extends MaintenanceController<Plan> {
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    @ResponseBody
    @RequestMapping(method={RequestMethod.POST})
    public ViewJSONWrapper add(@RequestBody Plan model) throws Exception {
        if (preAdd(model)) {
            ((PlanService)getService()).createPlan(model);
            return postAdd(getService().save(model));
        } else {
            return createEmptyResult();
        }
    }

    @Override
    protected boolean preList(List<Selection> selections) throws MinistryOnlyException {
        Owner owner = getNullableOwner();
        if (owner != null && owner.getId() != null && !owner.getId().isEmpty()) {
            selections.add(new Selection("owner", Selection.Operator.EQUAL, owner.getId()));
        }
        return true;
    }

    @Override
    protected boolean preAdd(Plan model) throws Exception {
        Owner owner = getOwner();
        model.setOwner(owner.getId());
        model.setOwnerName(owner.getName());
        return super.preAdd(model);
    }

    @Override
    protected boolean preUpdate(String id, Plan model) throws Exception {
        Owner owner = getOwner();
        model.setOwner(owner.getId());
        model.setOwnerName(owner.getName());
        return super.preUpdate(id, model);
    }

    @Override
    protected EntityService<Plan> getService() {
        return getApplicationContext().getBean(PlanService.class);
    }
}
