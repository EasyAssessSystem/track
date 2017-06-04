package com.stardust.easyassess.track.models.statistics;




public class IQCHistoryStatisticComparisonModel {
    private IQCHistoryStatisticSet target;
    private IQCHistoryStatisticSet base;

    public IQCHistoryStatisticComparisonModel(IQCHistoryStatisticSet target, IQCHistoryStatisticSet base) {
        this.target = target;
        this.base = base;
    }

    public IQCHistoryStatisticSet getTarget() {
        return target;
    }

    public void setTarget(IQCHistoryStatisticSet target) {
        this.target = target;
    }

    public IQCHistoryStatisticSet getBase() {
        return base;
    }

    public void setBase(IQCHistoryStatisticSet base) {
        this.base = base;
    }
}
