package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.plan.IQCHistorySet;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.models.statistics.IQCHistoryGatherStatisticModel;
import com.stardust.easyassess.track.models.statistics.IQCHistoryUnitStatisticModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IQCPlanTemplateService extends EntityService<IQCPlanTemplate> {
    List<IQCHistorySet> getRecordsWithPlans(List<IQCPlan> plans, Date targetDate);

    IQCHistoryGatherStatisticModel getGatherStatisticData(String templateId,
                                                          Date targetDate,
                                                          int count,
                                                          Map<String, String> filters);

    Map<String,IQCHistoryUnitStatisticModel> getUnitStatisticCollection(String templateId,
                                                                        Date targetDate,
                                                                        int count,
                                                                        Map<String, String> filters);

}
