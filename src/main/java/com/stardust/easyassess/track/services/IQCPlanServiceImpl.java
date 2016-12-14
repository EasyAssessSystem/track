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
    public IQCPlanRecord submitRecord(String planId, IQCPlanRecord record, Owner owner) {
        IQCPlan plan = iqcPlanRepository.findOne(planId);
        record.setPlan(plan);
        record.setOwner(owner);
        record.setName(plan.getName());
        record.setDate(new Date());
        return iqcPlanRecordRepository.save(record);
    }

    @Override
    public List<IQCPlanRecord> getRecords(String planId, Date targetDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        return iqcPlanRecordRepository.getRecordsByDateAndPlanId(targetDate, calendar.getTime(), planId);
    }

    @Override
    public Page<IQCPlan> getPlansByOwner(Owner owner, int page, int size, String sortBy) {
        List<Selection> selections = new ArrayList();
        selections.add(new Selection("owner.id", Selection.Operator.EQUAL, owner.getId()));
        return this.list(page, size, sortBy, selections);
    }

    @Override
    protected DataRepository<IQCPlan, String> getRepository() {
        return iqcPlanRepository;
    }
}
