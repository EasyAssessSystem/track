package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.plan.IQCHistorySet;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;

import java.util.Date;
import java.util.List;

public interface IQCPlanTemplateService extends EntityService<IQCPlanTemplate> {
    List<IQCHistorySet> getRecordsWithPlans(List<IQCPlan> plans, Date targetDate);
}
