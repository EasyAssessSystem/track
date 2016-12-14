package com.stardust.easyassess.track.models.plan;

import com.stardust.easyassess.track.models.DataModel;
import com.stardust.easyassess.track.models.Owner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "plans")
public class IQCPlan extends DataModel {
    @Id
    private String id;

    private Owner owner;

    private String name;

    @DBRef(lazy = true)
    private List<IQCPlanRecord> records = new ArrayList();

    private List<IQCPlanItem> items = new ArrayList();

    public IQCPlan() {

    }

    public IQCPlan(IQCPlanTemplate template, Owner owner) {
        this.owner = owner;
        this.name = template.getName();
        this.items = template.getItems();
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
}