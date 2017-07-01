package com.stardust.easyassess.track.models.statistics;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

import java.util.HashMap;
import java.util.Map;

public class IQCHistoryStatisticPercentageData extends IQCHistoryStatisticData {
    private Map<String, Long> valueCountMap = new HashMap();

    public Map<String, Long> getValueCountMap() {
        return valueCountMap;
    }

    @JsonIgnore
    @Override
    public Long getCount() {
        final Long[] total = {new Long(0)};
        valueCountMap.forEach((k, v) -> {
            total[0] +=v;
        });
        return total[0];
    }

    @Override
    public void proceed(IQCPlanSpecimen item) {
        if (item.getValue() != null && !item.getValue().isEmpty()) {
            Long count = valueCountMap.containsKey(item.getValue())
                    ? valueCountMap.get(item.getValue()) : new Long(0) ;
            valueCountMap.put(item.getValue(), count + 1);
        }
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (String optionVal : valueCountMap.keySet()) {
            result.append(optionVal);
            result.append(":");
            result.append(valueCountMap.get(optionVal) + "次 ");
        }
        return result.toString();
    }
}
