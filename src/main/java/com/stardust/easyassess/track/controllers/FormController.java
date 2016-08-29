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


@CrossOrigin("*")
@RestController
@RequestMapping({"{domain}/track/form"})
@EnableAutoConfiguration
public class FormController extends MaintenanceController<Form> {
    @Autowired
    protected ApplicationContext applicationContext;

    @Override
    protected EntityService<Form> getService() {
        return getApplicationContext().getBean(FormService.class);
    }

    @RequestMapping(path="/submit/{id}",
            method={RequestMethod.PUT})
    public ViewJSONWrapper submit(@PathVariable String id, @RequestBody FormData data) throws MinistryOnlyException {
        Form form = getOwnerFormById(id);
        form.setValues(data.getValues());
        form.setCodes(data.getCodes());
        ((FormService)getService()).submit(form);
        return new ViewJSONWrapper(form);
    }

    @Override
    public ViewJSONWrapper get(@PathVariable String id) throws MinistryOnlyException {
        Form form = getOwnerFormById(id);
        return new ViewJSONWrapper(form);
    }

    @RequestMapping(path="/submitted/list",
            method={RequestMethod.GET})
    public ViewJSONWrapper getClosedForms(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "4") Integer size,
                                         @RequestParam(value = "sort", defaultValue = "id") String sort,
                                         @RequestParam(value = "filterField", defaultValue = "") String field,
                                         @RequestParam(value = "filterValue", defaultValue = "") String value ) throws MinistryOnlyException {
        return buildFormList("C", page, size, sort, field, value);
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

    private ViewJSONWrapper buildFormList(String status, Integer page, Integer size, String sort, String field, String value) throws MinistryOnlyException {
        List<Selection> selections = new ArrayList();
        selections.add(new Selection(field, Selection.Operator.LIKE, value));
        selections.add(new Selection("status", Selection.Operator.EQUAL, status));
        if (getOwner() != null) {
            selections.add(new Selection("owner", Selection.Operator.EQUAL, getOwner().getId()));
        }
        return new ViewJSONWrapper(getService().list(page, size , sort, selections));
    }
}
