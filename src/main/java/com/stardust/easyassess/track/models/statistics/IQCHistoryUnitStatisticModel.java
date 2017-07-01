package com.stardust.easyassess.track.models.statistics;

import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;

import java.util.Date;
import java.util.Map;

public class IQCHistoryUnitStatisticModel extends AbstractIQCHistoryStatisticModel {

    private IQCPlan plan;

    public IQCHistoryUnitStatisticModel(IQCHistorySpecimenStatisticSet data,
                                        Date startDate,
                                        Date endDate,
                                        Map<String, String> filters,
                                        IQCPlan plan) {
        super(data, startDate, endDate, filters);
        this.plan = plan;
    }

    public IQCPlan getPlan() {
        return plan;
    }
}
