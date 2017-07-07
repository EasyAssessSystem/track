package com.stardust.easyassess.track.models.statistics;


import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

import java.util.HashMap;
import java.util.Map;

public abstract class IQCHistoryStatisticData {
    private int outOfControl = 0;

    private int inControl = 0;

    private Map<String, Owner> participants = new HashMap();

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

    public void addParticipant(Owner owner) {
        if (!participants.containsKey(owner.getId())) {
            participants.put(owner.getId(), owner);
        }
    }

    public int getCountOfParticipants() {
        return participants.isEmpty() ? 0 : participants.keySet().size();
    }
}
