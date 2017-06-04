package com.stardust.easyassess.track.models.statistics;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

import java.util.HashMap;
import java.util.Map;

public class IQCHistoryStatisticItem {
    private Map<String, IQCHistoryStatisticData> data = new HashMap();

    public Map<String, IQCHistoryStatisticData> getData() {
        return data;
    }

    @JsonIgnore
    public IQCHistoryStatisticData getStatisticData(IQCPlanSpecimen specimen) {
        if (!data.containsKey(specimen.getNumber())) {
            if (specimen.getType().equals("S")) {
                data.put(specimen.getNumber(),
                        new IQCHistoryStatisticPercentageData());
            } else {
                data.put(specimen.getNumber(),
                        new IQCHistoryStatisticAverageData());
            }
        }

        return data.get(specimen.getNumber());
    }
}
