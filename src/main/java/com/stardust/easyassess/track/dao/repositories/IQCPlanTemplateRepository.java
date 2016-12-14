package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;

public interface IQCPlanTemplateRepository extends DataRepository<IQCPlanTemplate, String> {
    default Class<IQCPlanTemplate> getEntityClass() {
        return IQCPlanTemplate.class;
    }
}
