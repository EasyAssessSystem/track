package com.stardust.easyassess.track.models.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stardust.easyassess.track.models.DataModel;
import com.stardust.easyassess.track.models.Owner;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "records")
public class IQCPlanRecord extends DataModel {

    @Id
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    private String name;

    private Owner owner;

    @DBRef(lazy = true)
    private IQCPlan plan;

    private List<IQCPlanItem> items = new ArrayList();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IQCPlan getPlan() {
        return plan;
    }

    public void setPlan(IQCPlan plan) {
        this.plan = plan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
