package com.stardust.easyassess.track.models.statistics;


import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

public class IQCHistoryStatisticAverageData extends IQCHistoryStatisticData {
    private Double total = new Double(0);

    private Long count = new Long(0);

    public Double getTotal() {
        return total;
    }

    @Override
    public Long getCount() {
        return count;
    }

    public Double getAverageValue() {
        if (getCount() == 0) return new Double(0);
        return getTotal() / getCount();
    }

    @Override
    protected void statistic(IQCPlanSpecimen item) {
        try {
            if (item.getValue() != null) {
                total += Double.parseDouble(item.getValue());
                count += 1;
            }
        } catch (Exception e) {

        }
    }

    @Override
    public String toString() {
        String result = "平均数据:"
                + getAverageValue();
        return result;
    }
}
