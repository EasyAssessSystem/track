package com.stardust.easyassess.track.models.statistics;


import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

public class IQCHistoryStatisticAverageData extends IQCHistoryStatisticData {
    private Double total = new Double(0);

    private Long count = new Long(0);

    public Double getTotal() {
        return total;
    }

    public Long getCount() {
        return count;
    }

    public Double getAverageValue() {
        if (getCount() == 0) return new Double(0);
        return getTotal() / getCount();
    }

    @Override
    public void proceed(IQCPlanSpecimen item) {
        total += Double.parseDouble(item.getValue());
        count += 1;
    }
}
