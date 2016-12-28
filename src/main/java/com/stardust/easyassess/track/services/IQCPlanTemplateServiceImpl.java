package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.dao.repositories.DataRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanTemplateRepository;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("request")
public class IQCPlanTemplateServiceImpl extends AbstractEntityService<IQCPlanTemplate> implements IQCPlanTemplateService {

    @Autowired
    private IQCPlanService iqcPlanService;

    @Autowired
    private IQCPlanRepository iqcPlanRepository;

    @Autowired
    private IQCPlanTemplateRepository iqcPlanTemplateRepository;

    @Override
    protected DataRepository<IQCPlanTemplate, String> getRepository() {
        return iqcPlanTemplateRepository;
    }

    @Override
    public void remove(String id) {
        super.remove(id);
        List<IQCPlan> plans = iqcPlanRepository.findPlansByTemplateId(id);
        if (plans != null) {
            for (IQCPlan plan : plans) {
                iqcPlanService.remove(plan.getId());
            }
        }
    }


    @Override
    public IQCPlanTemplate save(IQCPlanTemplate model) {
        IQCPlanTemplate template = getRepository().save(model);

        for (String ownerId : model.getParticipants().keySet()) {
            Owner owner = new Owner(ownerId, model.getParticipants().get(ownerId));
            iqcPlanService.copyFromTemplate(template, owner);
        }

        return template;
    }
}
