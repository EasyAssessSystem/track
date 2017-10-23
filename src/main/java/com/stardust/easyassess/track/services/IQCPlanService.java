package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.AdditionalItem;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticComparisonModel;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticSet;
import com.stardust.easyassess.track.models.statistics.IQCHistoryUnitStatisticModel;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IQCPlanService extends EntityService<IQCPlan> {
    IQCPlan copyFromTemplate(IQCPlanTemplate template, Owner owner);
    IQCPlan copyFromTemplate(String templateId, Owner owner);
    IQCPlanRecord submitRecord(Date date, String planId, IQCPlanRecord record, Owner owner) throws ParseException;
    IQCPlanRecord addComment(String recordId, String comment) throws ParseException;
    List<IQCPlanRecord> getRecords(String planId, Date targetDate);
    List<IQCPlanRecord> getRecords(String planId, Date targetDate, int count);
    IQCPlanRecord getTodayRecord(String planId) throws ParseException;
    List<IQCPlanRecord> getRecord(String planId, Date targetDate);
    Page<IQCPlan> getPlansByOwner(Owner owner, int page, int size, String sortBy);
    void updateOwnerName(Owner owner);
    IQCHistoryStatisticComparisonModel getPeriodStatisticComparison(String planId,
                                                                    Date targetDate,
                                                                    int count,
                                                                    Map<String, String> filters);
    IQCHistoryStatisticSet getPeriodStatistic(List<IQCPlan> plans,
                                              Date targetDate,
                                              int count,
                                              Map<String, String> filters);
    IQCHistoryStatisticSet getPeriodStatistic(IQCPlan plan,
                                              Date targetDate,
                                              int count,
                                              Map<String, String> filters);

    IQCHistoryUnitStatisticModel getUnitStatistic(String planId,
                                                  Date targetDate,
                                                  int count,
                                                  Map<String, String> filters);
}
