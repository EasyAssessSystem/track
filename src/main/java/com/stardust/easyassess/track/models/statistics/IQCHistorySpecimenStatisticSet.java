package com.stardust.easyassess.track.models.statistics;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

public class IQCHistorySpecimenStatisticSet {
    private Map<String, IQCHistoryStatisticSpecimen> model = new HashMap();

    public IQCHistorySpecimenStatisticSet(IQCHistoryStatisticSet statisticSet) {
        for (String subject : statisticSet.getItems().keySet()) {
            IQCHistoryStatisticItem statisticItemItem = statisticSet.getItems().get(subject);
            for (String specimen : statisticItemItem.getData().keySet()) {
                IQCHistoryStatisticData data = statisticItemItem.getData().get(specimen);
                IQCHistoryStatisticSpecimen statisticSpecimen = getSpecimen(specimen);
                statisticSpecimen.getItems().put(subject, data);
            }
        }
    }

    @JsonIgnore
    public IQCHistoryStatisticSpecimen getSpecimen(String specimen) {
        if (!model.containsKey(specimen)) {
            model.put(specimen, new IQCHistoryStatisticSpecimen());
        }
        return model.get(specimen);
    }

    public Map<String, IQCHistoryStatisticSpecimen> getModel() {
        return model;
    }
}
