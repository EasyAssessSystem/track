package com.stardust.easyassess.track.models.statistics;


import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlanSpecimen;

import java.util.HashMap;
import java.util.Map;

public abstract class IQCHistoryStatisticData {
    private int outOfControl = 0;

    private int inControl = 0;

    private Map<String, Owner> participants = new HashMap();

    private String type;
    private double targetValue;
    private double floatValue;
    private Map<String, Integer> enumValues = new HashMap();

    public void proceed(IQCPlanSpecimen item) {
        targetValue = item.getTargetValue();
        floatValue = item.getFloatValue();
        enumValues = item.getEnumValues();
        type = item.getType();
        if (item.isInControl()) {
            inControl++;
        } else {
            outOfControl++;
        }
        statistic(item);
    }

    public void setOutOfControl(int outOfControl) {
        this.outOfControl = outOfControl;
    }

    public void setInControl(int inControl) {
        this.inControl = inControl;
    }

    public Map<String, Owner> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, Owner> participants) {
        this.participants = participants;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    public double getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(double floatValue) {
        this.floatValue = floatValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Integer> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(Map<String, Integer> enumValues) {
        this.enumValues = enumValues;
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
