package com.stardust.easyassess.track.models.plan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stardust.easyassess.track.models.DataModel;
import com.stardust.easyassess.track.models.Owner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "plans")
public class IQCPlan extends DataModel implements IQCSubjectSpecimenMatrix {
    @Id
    private String id;

    private Owner owner;

    private String name;

    @DBRef(lazy = true)
    private List<IQCPlanRecord> records = new ArrayList();

    @DBRef(lazy = true)
    private IQCPlanTemplate template;

    @DBRef(lazy = true)
    private IQCPlanGroup group;

    private List<IQCPlanItem> items = new ArrayList();

    private List<AdditionalItem> additionalItems = new ArrayList();

    private int version = 0;

    public IQCPlan() {

    }

    public IQCPlan(IQCPlanTemplate template, Owner owner) {
        this.owner = owner;
        this.name = template.getName();
        this.items = template.getItems();
        this.template = template;
        this.additionalItems = template.getAdditionalItems();
    }

    public String getComment() {
        return this.template != null ? this.template.getComment() : "";
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<AdditionalItem> getAdditionalItems() {
        return additionalItems;
    }

    public void setAdditionalItems(List<AdditionalItem> additionalItems) {
        this.additionalItems = additionalItems;
    }

    @JsonIgnore
    public IQCPlanTemplate getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(IQCPlanTemplate template) {
        this.template = template;
    }

    @JsonIgnore
    public IQCPlanGroup getGroup() {
        return group;
    }

    @JsonProperty
    public void setGroup(IQCPlanGroup group) {
        this.group = group;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<IQCPlanItem> getItems() {
        return items;
    }

    public void setItems(List<IQCPlanItem> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<IQCPlanRecord> getRecords() {
        return records;
    }

    public void setRecords(List<IQCPlanRecord> records) {
        this.records = records;
    }
}
