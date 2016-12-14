package com.stardust.easyassess.track.models.plan;

import java.util.HashMap;
import java.util.Map;

public class IQCPlanSpecimen {
    public final static String SPECIMEN_TYPE_SELECTION = "S";
    public final static String SPECIMEN_TYPE_TARGET_WITH_FIX_FLOAT = "T";
    public final static String SPECIMEN_TYPE_TARGET_WITH_PERCENTAGE_FLOAT = "P";

    private String type = SPECIMEN_TYPE_SELECTION;
    private String number;
    private String value;
    private double targetValue;
    private double floatValue;
    private Map<String, Integer> enumValues = new HashMap();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Map<String, Integer> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(Map<String, Integer> enumValues) {
        this.enumValues = enumValues;
    }
}


