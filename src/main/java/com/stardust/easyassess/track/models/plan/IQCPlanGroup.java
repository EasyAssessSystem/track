package com.stardust.easyassess.track.models.plan;

import com.stardust.easyassess.track.models.DataModel;
import com.stardust.easyassess.track.models.Owner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "plan_groups")
public class IQCPlanGroup extends DataModel {
    @Id
    private String id;

    private Owner owner;

    @DBRef(lazy = true)
    private List<IQCPlan> plans = new ArrayList();

    @DBRef(lazy = true)
    private IQCPlanTemplate template;

    public IQCPlanGroup() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<IQCPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<IQCPlan> plans) {
        this.plans = plans;
    }

    public IQCPlanTemplate getTemplate() {
        return template;
    }

    public void setTemplate(IQCPlanTemplate template) {
        this.template = template;
    }
}
