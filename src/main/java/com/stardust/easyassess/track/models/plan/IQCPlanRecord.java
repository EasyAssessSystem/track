package com.stardust.easyassess.track.models.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stardust.easyassess.track.models.DataModel;
import com.stardust.easyassess.track.models.Owner;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.*;

@Document(collection = "records")
public class IQCPlanRecord extends DataModel {

    @Id
    private String id;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date lastModified;

    private String name;

    private Owner owner;

    private String tags;

    private String comments;

    @DBRef(lazy = true)
    private IQCPlan plan;

    private List<IQCPlanItem> items = new ArrayList();

    private Map<String, String> additionalData = new HashMap();

    private int version = 0;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, String> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map<String, String> additionalData) {
        this.additionalData = additionalData;
    }

    @JsonIgnore
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

    @JsonIgnore
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
