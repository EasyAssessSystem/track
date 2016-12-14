package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.core.exception.ESAppException;
import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.track.services.IQCPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

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
    protected boolean preList(List<Selection> selections) throws ESAppException {
        Owner owner = getNullableOwner();
        if (owner != null && owner.getId() != null && !owner.getId().isEmpty()) {
            selections.add(new Selection("owner.id", Selection.Operator.EQUAL, owner.getId()));
            selections.add(new Selection("owner", Selection.Operator.IS_NULL, null, Selection.Operand.OR));
        }
        return true;
    }
}
