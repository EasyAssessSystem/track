package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.plan.IQCPlanGroup;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface IQCPlanGroupRepository extends DataRepository<IQCPlanGroup, String> {
    default Class<IQCPlanGroup> getEntityClass() {
        return IQCPlanGroup.class;
    }

    @Query("{templateId:{$eq:?0}}")
    List<IQCPlanGroup> findGroupsByTemplateId(String planId);

    @Query("{owner.id:{$eq:?0}}")
    List<IQCPlanGroup> findGroupsByOwnerId(String ownerId);
}
