package com.stardust.easyassess.track.models.statistics;



import java.util.HashMap;
import java.util.Map;

public class IQCHistoryStatisticSpecimen {
    private Map<String, IQCHistoryStatisticData> items = new HashMap();

    public Map<String, IQCHistoryStatisticData> getItems() {
        return items;
    }

    public void setItems(Map<String, IQCHistoryStatisticData> items) {
        this.items = items;
    }
}
