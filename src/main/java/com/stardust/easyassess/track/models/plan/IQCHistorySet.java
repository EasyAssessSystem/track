package com.stardust.easyassess.track.models.plan;


import com.stardust.easyassess.track.models.Owner;

import java.util.List;

public class IQCHistorySet {
    private Owner owner;

    private String planName;

    private List<IQCPlanRecord> targetRecords;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<IQCPlanRecord> getTargetRecords() {
        return targetRecords;
    }

    public void setTargetRecords(List<IQCPlanRecord> targetRecords) {
        this.targetRecords = targetRecords;
    }
}
