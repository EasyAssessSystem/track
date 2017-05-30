package com.stardust.easyassess.track.models.plan;

import com.stardust.easyassess.track.models.DataModel;
import com.stardust.easyassess.track.models.Owner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Embedded;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "plan_templates")
public class IQCPlanTemplate extends DataModel {
    @Id
    private String id;

    private Owner owner;

    private String name;

    @Embedded
    private Map<String, String> participants;

    private List<AdditionalItem> additionalItems = new ArrayList();

    private List<IQCPlanItem> items = new ArrayList();

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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<AdditionalItem> getAdditionalItems() {
        return additionalItems;
    }

    public void setAdditionalItems(List<AdditionalItem> additionalItems) {
        this.additionalItems = additionalItems;
    }

    public Map<String, String> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, String> participants) {
        this.participants = participants;
    }
}
