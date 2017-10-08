package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlanGroup;

public interface IQCPlanGroupService extends EntityService<IQCPlanGroup> {
    void updateOwnerName(Owner owner);
}
