package com.stardust.easyassess.track.models.statistics;

import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;

import java.util.Date;
import java.util.Map;

public class IQCHistoryGatherStatisticModel extends AbstractIQCHistoryStatisticModel {

    private IQCPlanTemplate template;

    public IQCHistoryGatherStatisticModel(IQCHistorySpecimenStatisticSet data,
                                          Date startDate,
                                          Date endDate,
                                          Map<String, String> filters,
                                          IQCPlanTemplate template) {
        super(data, startDate, endDate, filters);
        this.template = template;
    }

    public IQCPlanTemplate getTemplate() {
        return template;
    }
}
