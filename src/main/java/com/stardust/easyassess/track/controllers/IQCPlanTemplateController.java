package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.track.services.IQCPlanService;
import com.stardust.easyassess.track.services.IQCPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

    @RequestMapping(path="/{id}/plan/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper planList(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") Integer page,
                                              @RequestParam(value = "size", defaultValue = "4") Integer size,
                                              @RequestParam(value = "sort", defaultValue = "id") String sort,
                                              @RequestParam(value = "filterField", defaultValue = "") String field,
                                              @RequestParam(value = "filterValue", defaultValue = "") String value ) throws Exception {

        List<Selection> selections = new ArrayList();
        selections.add(new Selection("template.id", Selection.Operator.EQUAL, id));
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        return new ViewJSONWrapper(applicationContext.getBean(IQCPlanService.class).list(page, size , sort, selections));
    }
}
