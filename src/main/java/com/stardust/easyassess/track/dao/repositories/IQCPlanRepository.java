package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.plan.IQCPlan;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IQCPlanRepository extends DataRepository<IQCPlan, String> {
    default Class<IQCPlan> getEntityClass() {
        return IQCPlan.class;
    }

    @Query("{templateId:{$eq:?0}}")
    List<IQCPlan> findPlansByTemplateId(String planId);
}
