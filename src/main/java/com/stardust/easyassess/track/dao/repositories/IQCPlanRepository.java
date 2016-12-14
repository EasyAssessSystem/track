package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.plan.IQCPlan;

public interface IQCPlanRepository extends DataRepository<IQCPlan, String> {
    default Class<IQCPlan> getEntityClass() {
        return IQCPlan.class;
    }
}
