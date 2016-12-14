package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.dao.repositories.DataRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanTemplateRepository;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("request")
public class IQCPlanTemplateServiceImpl extends AbstractEntityService<IQCPlanTemplate> implements IQCPlanTemplateService {

    @Autowired
    private IQCPlanTemplateRepository iqcPlanTemplateRepository;

    @Override
    protected DataRepository<IQCPlanTemplate, String> getRepository() {
        return iqcPlanTemplateRepository;
    }
}
