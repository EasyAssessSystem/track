package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface IQCPlanService extends EntityService<IQCPlan> {
    IQCPlan copyFromTemplate(IQCPlanTemplate template, Owner owner);
    IQCPlan copyFromTemplate(String templateId, Owner owner);
    IQCPlanRecord submitRecord(String planId, IQCPlanRecord record, Owner owner) throws ParseException;
    List<IQCPlanRecord> getRecords(String planId, Date targetDate);
    IQCPlanRecord getTodayRecord(String planId) throws ParseException;
    Page<IQCPlan> getPlansByOwner(Owner owner, int page, int size, String sortBy);
}
