package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.dao.repositories.*;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("request")
public class IQCPlanGroupServiceImpl extends AbstractEntityService<IQCPlanGroup> implements IQCPlanGroupService {

    @Autowired
    private IQCPlanGroupRepository iqcPlanGroupRepository;

    @Autowired
    private IQCPlanRepository iqcPlanRepository;

    @Autowired
    private IQCPlanTemplateRepository iqcPlanTemplateRepository;

    @Override
    public void updateOwnerName(Owner owner) {
        List<IQCPlanGroup> groups = iqcPlanGroupRepository.findGroupsByOwnerId(owner.getId());
        for (IQCPlanGroup group : groups) {
            group.getOwner().setName(owner.getName());
            iqcPlanGroupRepository.save(group);
        }
    }

    @Override
    protected DataRepository<IQCPlanGroup, String> getRepository() {
        return iqcPlanGroupRepository;
    }

    @Override
    public void remove(String id) {
        IQCPlanGroup group = this.get(id);
        if (group.getPlans() != null) {
            for (IQCPlan plan : group.getPlans()) {
                if (plan != null) {
                    iqcPlanRepository.delete(plan);
                }
            }
        }

        if (group.getTemplate().getParticipants() != null) {
            group.getTemplate().getParticipants().remove(group.getOwner().getId());
        }

        iqcPlanTemplateRepository.save(group.getTemplate());

        super.remove(id);
    }
}
