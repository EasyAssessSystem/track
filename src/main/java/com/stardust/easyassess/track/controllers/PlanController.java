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
@RequestMapping({"{domain}/track/plan"})
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

    @RequestMapping(path="/mine/activated/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper listActivated(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                @RequestParam(value = "size", defaultValue = "4") Integer size,
                                @RequestParam(value = "sort", defaultValue = "id") String sort,
                                @RequestParam(value = "filterField", defaultValue = "") String field,
                                @RequestParam(value = "filterValue", defaultValue = "") String value ) throws MinistryOnlyException {

        List<Selection> selections = new ArrayList();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        selections.add(new Selection("status", Selection.Operator.EQUAL, "A"));
        Owner owner = getNullableOwner();
        if (owner != null) {
            selections.add(new Selection("participants." + owner.getId(), Selection.Operator.EXSITS, true));
            return new ViewJSONWrapper(getService().list(page, size , sort, selections));
        } else {
            return new ViewJSONWrapper(null);
        }
    }
}
