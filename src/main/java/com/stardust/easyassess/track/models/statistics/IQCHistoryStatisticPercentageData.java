package com.stardust.easyassess.track.models.statistics;


import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

import java.util.HashMap;
import java.util.Map;

public class IQCHistoryStatisticPercentageData extends IQCHistoryStatisticData {
    private Map<String, Long> valueCountMap = new HashMap();

    public Map<String, Long> getValueCountMap() {
        return valueCountMap;
    }

    @Override
    public void proceed(IQCPlanSpecimen item) {
        Long count = valueCountMap.containsKey(item.getValue())
                ? valueCountMap.get(item.getValue()) : new Long(0) ;
        valueCountMap.put(item.getValue(), count + 1);
    }
}
