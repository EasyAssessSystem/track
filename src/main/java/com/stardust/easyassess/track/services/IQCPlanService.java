package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface IQCPlanService extends EntityService<IQCPlan> {
    IQCPlan copyFromTemplate(IQCPlanTemplate template, Owner owner);
    IQCPlan copyFromTemplate(String templateId, Owner owner);
    IQCPlanRecord submitRecord(String planId, IQCPlanRecord record, Owner owner);
    List<IQCPlanRecord> getRecords(String planId, Date targetDate);
    Page<IQCPlan> getPlansByOwner(Owner owner, int page, int size, String sortBy);
}