package com.stardust.easyassess.track.models.statistics;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stardust.easyassess.track.models.plan.IQCPlanItem;

import java.util.HashMap;
import java.util.Map;

public class IQCHistoryStatisticSet {
    private Map<String, IQCHistoryStatisticItem> items = new HashMap();

    public Map<String, IQCHistoryStatisticItem> getItems() {
        return items;
    }

    @JsonIgnore
    public IQCHistoryStatisticItem getItem(IQCPlanItem item) {
        if (!items.containsKey(item.getSubject())) {
            items.put(item.getSubject(), new IQCHistoryStatisticItem());
        }

        return items.get(item.getSubject());
    }
}
