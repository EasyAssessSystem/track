package com.stardust.easyassess.track.models.plan;

import java.util.ArrayList;
import java.util.List;

public class AdditionalItem {
    public enum Type {
        STRING, DATE, LISTING;

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private String name;

    private Type type;

    private List<String> values = new ArrayList();

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public int hashCode() {
        return (this.name + this.type.toString()).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AdditionalItem)) return false;
        AdditionalItem item = (AdditionalItem) obj;
        return this.name.equals(item.getName()) && this.type.equals(item.getType());
    }
}
