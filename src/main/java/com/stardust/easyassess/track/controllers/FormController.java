package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.form.Form;
import com.stardust.easyassess.track.models.form.FormData;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.track.services.FormService;
import com.stardust.easyassess.core.exception.MinistryOnlyException;
import com.stardust.easyassess.core.presentation.ViewJSONWrapper;
import com.stardust.easyassess.core.query.Selection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping({"{domain}/iqc/form"})
@EnableAutoConfiguration
public class FormController extends MaintenanceController<Form> {
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    protected EntityService<Form> getService() {
        return getApplicationContext().getBean(FormService.class);
    }

    @Override
    protected boolean preAdd(Form model) throws MinistryOnlyException {
        if (model.getPlan() != null && model.getId() == null) {
            model.setOwner(getOwner().getId());
            model.setSubmitDate(new Date());
            model.setStatus("A");
            return true;
        }
        return false;
    }

    @Override
    public ViewJSONWrapper get(@PathVariable String id) throws MinistryOnlyException {
        Form form = getOwnerFormById(id);
        return new ViewJSONWrapper(form);
    }

    @RequestMapping(path = "/{plan}/{owner}/list",
            method = {RequestMethod.GET})
    public ViewJSONWrapper getFormsByPlan(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @RequestParam(value = "size", defaultValue = "4") Integer size,
                                          @RequestParam(value = "sort", defaultValue = "id") String sort,
                                          @RequestParam(value = "filterField", defaultValue = "") String field,
                                          @RequestParam(value = "filterValue", defaultValue = "") String value,
                                          @RequestParam(value = "startDate", defaultValue = "") String startDate,
                                          @RequestParam(value = "endDate", defaultValue = "") String endDate,
                                          @PathVariable String plan,
                                          @PathVariable String owner) throws MinistryOnlyException {
        List<Selection> selections = new ArrayList();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        selections.add(new Selection("plan.id", Selection.Operator.EQUAL, plan));
        selections.add(new Selection("owner", Selection.Operator.EQUAL, owner));
        if (!startDate.isEmpty()) {
            selections.add(new Selection("submitDate", Selection.Operator.GREATER_EQUAL, getDateFromString(startDate)));
        }
        if (!endDate.isEmpty()) {
            selections.add(new Selection("submitDate", Selection.Operator.LESS_EQUAL, getDateFromString(endDate)));
        }
        return new ViewJSONWrapper(getService().list(page, size, sort, selections));
    }

    private Form getOwnerFormById(String id) throws MinistryOnlyException {
        Form form = getService().get(id);
        Owner owner = getOwner();
        if (owner != null) {
            if (!owner.getId().equals(form.getOwner())) {
                form = null;
            }
        }
        return form;
    }
}
