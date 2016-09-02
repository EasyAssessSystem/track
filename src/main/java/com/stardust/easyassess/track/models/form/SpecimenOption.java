package com.stardust.easyassess.track.models.form;

import java.util.ArrayList;
import java.util.List;

public class SpecimenOption extends FormElement {
    private String type;

    private List<OptionValue> optionValues = new ArrayList<OptionValue>();

    public List<OptionValue> getOptionValues() {
        return optionValues;
    }

    public void setOptionValues(List<OptionValue> optionValues) {
        this.optionValues = optionValues;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
