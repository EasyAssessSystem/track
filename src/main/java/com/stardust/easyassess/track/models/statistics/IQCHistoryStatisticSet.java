package com.stardust.easyassess.track.models.statistics;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stardust.easyassess.track.models.plan.IQCPlanItem;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IQCHistoryStatisticSet {
    private Map<String, IQCHistoryStatisticItem> items = new HashMap();

    private Date endDate;

    private int scope;

    private Map<String, String> filters;

    public IQCHistoryStatisticSet(Date endDate, int scope, Map<String, String> filters) {
        this.endDate = endDate;
        this.scope = scope;
        this.filters = filters;
    }

    public IQCHistoryStatisticSet() {

    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public Date getStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_MONTH, -scope);
        return calendar.getTime();
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public Map<String, IQCHistoryStatisticItem> getItems() {
        return items;
    }

    @JsonIgnore
    public IQCHistoryStatisticItem getItem(IQCPlanItem item) {
        if (!items.containsKey(item.getSubject())) {
            items.put(item.getSubject(), new IQCHistoryStatisticItem());
        }

        return items.get(item.getSubject());
    }
}
