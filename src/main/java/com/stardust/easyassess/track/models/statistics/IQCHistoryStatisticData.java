package com.stardust.easyassess.track.models.statistics;


import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

public abstract class IQCHistoryStatisticData {
    private int outOfControl = 0;

    private int inControl = 0;

    public void proceed(IQCPlanSpecimen item) {
        if (item.isInControl()) {
            inControl++;
        } else {
            outOfControl++;
        }
        statistic(item);
    }

    public abstract Long getCount();

    protected abstract void statistic(IQCPlanSpecimen item);

    public int getOutOfControl() {
        return outOfControl;
    }

    public int getInControl() {
        return inControl;
    }
}
