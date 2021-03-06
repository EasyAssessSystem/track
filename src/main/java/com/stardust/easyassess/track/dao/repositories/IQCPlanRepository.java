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

    @Query("{owner.id:{$eq:?0}}")
    List<IQCPlan> findPlansByOwnerId(String ownerId);

    @Query("{owner.id:{$eq:?0}, templateId:{$eq:?1}}")
    IQCPlan findPlanByOwnerIdAndTemplateId(String ownerId, String templateId);
}
