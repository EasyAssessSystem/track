package com.stardust.easyassess.track.models.statistics;

import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;

import java.util.Date;
import java.util.Map;

public class IQCHistoryGatherStatisticModel extends AbstractIQCHistoryStatisticModel {
    private int branchCount;

    private IQCPlanTemplate template;

    public IQCHistoryGatherStatisticModel(IQCHistorySpecimenStatisticSet data,
                                          Date startDate,
                                          Date endDate,
                                          Map<String, String> filters,
                                          int branchCount,
                                          IQCPlanTemplate template) {
        super(data, startDate, endDate, filters);
        this.branchCount = branchCount;
        this.template = template;
    }

    public int getBranchCount() {
        return branchCount;
    }

    public IQCPlanTemplate getTemplate() {
        return template;
    }
}
