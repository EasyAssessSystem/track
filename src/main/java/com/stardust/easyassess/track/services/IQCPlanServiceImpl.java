package com.stardust.easyassess.track.services;


import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.dao.repositories.DataRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanRecordRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanTemplateRepository;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Scope("request")
public class IQCPlanServiceImpl extends AbstractEntityService<IQCPlan> implements IQCPlanService {

    @Autowired
    private IQCPlanRepository iqcPlanRepository;

    @Autowired
    private IQCPlanTemplateRepository iqcPlanTemplateRepository;

    @Autowired
    private IQCPlanRecordRepository iqcPlanRecordRepository;

    @Override
    public IQCPlan copyFromTemplate(IQCPlanTemplate template, Owner owner) {
        IQCPlan plan = new IQCPlan(template, owner);
        return iqcPlanRepository.save(plan);
    }

    @Override
    public IQCPlan copyFromTemplate(String templateId, Owner owner) {
        IQCPlanTemplate template = iqcPlanTemplateRepository.findOne(templateId);
        return copyFromTemplate(template, owner);
    }

    @Override
    public IQCPlanRecord submitRecord(String planId, IQCPlanRecord record, Owner owner) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = formatter.parse(formatter.format(new Date()));
        IQCPlan plan = iqcPlanRepository.findOne(planId);
        IQCPlanRecord todayRecord = getTodayRecord(planId);
        if (todayRecord != null) {
            record.setId(todayRecord.getId());
        }
        record.setPlan(plan);
        record.setOwner(owner);
        record.setName(plan.getName());
        record.setDate(today);
        return iqcPlanRecordRepository.save(record);
    }

    @Override
    public List<IQCPlanRecord> getRecords(String planId, Date targetDate) {
        return getRecords(planId, targetDate, 20);
    }

    @Override
    public List<IQCPlanRecord> getRecords(String planId, Date targetDate, int count) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(Calendar.DAY_OF_MONTH, -count);
        return iqcPlanRecordRepository.findRecordsByPlanId(calendar.getTime(), targetDate, planId);
    }

    @Override
    public IQCPlanRecord getTodayRecord(String planId) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = formatter.parse(formatter.format(new Date()));

        List<IQCPlanRecord> records = iqcPlanRecordRepository.findRecordsByPlanId(today, today, planId);
        if (records != null && !records.isEmpty()) {
            return records.get(0);
        }

        return null;
    }

    @Override
    public Page<IQCPlan> getPlansByOwner(Owner owner, int page, int size, String sortBy) {
        List<Selection> selections = new ArrayList();
        selections.add(new Selection("owner.id", Selection.Operator.EQUAL, owner.getId()));
        return this.list(page, size, sortBy, selections);
    }

    @Override
    public void updateOwnerName(Owner owner) {
        List<IQCPlan> plans = iqcPlanRepository.findPlansByOwnerId(owner.getId());
        for (IQCPlan plan : plans) {
            plan.getOwner().setName(owner.getName());
            iqcPlanRepository.save(plan);
        }
    }

    @Override
    protected DataRepository<IQCPlan, String> getRepository() {
        return iqcPlanRepository;
    }
}
