package com.stardust.easyassess.track.controllers;


import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.services.EntityService;
import com.stardust.easyassess.track.services.IQCPlanTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;



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
}
