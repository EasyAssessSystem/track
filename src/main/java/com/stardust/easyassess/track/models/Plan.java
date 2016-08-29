package com.stardust.easyassess.track.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.stardust.easyassess.track.models.form.Form;
import com.stardust.easyassess.track.models.form.FormTemplate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Embedded;
import java.util.*;

@Document(collection = "plans")
public class Plan extends DataModel {
    @Id
    private String id;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
    private String owner;
    private String ownerName;
    private String status;
    private int duration;

    @Embedded
    private Map<String, String> participants;

    private FormTemplate template;

    @Embedded
    private Map<String, List<String>> specimenCodes = new HashMap();

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getParticipants() {
        return participants;
    }

    public void setParticipants(Map<String, String> participants) {
        this.participants = participants;
    }

    public FormTemplate getTemplate() {
        return template;
    }

    public void setTemplate(FormTemplate template) {
        this.template = template;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Map<String, List<String>> getSpecimenCodes() {
        return specimenCodes;
    }

    public void setSpecimenCodes(Map<String, List<String>> specimenCodes) {
        this.specimenCodes = specimenCodes;
    }
}
