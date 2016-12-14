package com.stardust.easyassess.track.models.plan;



import java.util.ArrayList;
import java.util.List;

public class IQCPlanItem {
    private String subject;
    private String unit;
    private List<IQCPlanSpecimen> specimens = new ArrayList();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<IQCPlanSpecimen> getSpecimens() {
        return specimens;
    }

    public void setSpecimens(List<IQCPlanSpecimen> specimens) {
        this.specimens = specimens;
    }
}


