package com.stardust.easyassess.track.models.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;

import java.util.Date;
import java.util.Map;

public abstract class AbstractIQCHistoryStatisticModel {

    private IQCHistorySpecimenStatisticSet data;

    private Date endDate;

    private Date startDate;

    private Map<String, String> filters;


    public AbstractIQCHistoryStatisticModel(IQCHistorySpecimenStatisticSet data,
                                            Date startDate,
                                            Date endDate,
                                            Map<String, String> filters) {
        this.data = data;
        this.filters = filters;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public IQCHistorySpecimenStatisticSet getData() {
        return data;
    }

    public void setData(IQCHistorySpecimenStatisticSet data) {
        this.data = data;
    }
}
