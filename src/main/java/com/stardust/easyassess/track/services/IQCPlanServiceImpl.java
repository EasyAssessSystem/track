package com.stardust.easyassess.track.services;


import com.stardust.easyassess.core.query.Selection;
import com.stardust.easyassess.track.dao.repositories.DataRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanRecordRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanTemplateRepository;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.*;
import com.stardust.easyassess.track.models.statistics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public IQCPlanRecord submitRecord(Date date, String planId, IQCPlanRecord record, Owner owner) throws ParseException {
        //DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Date today = formatter.parse(formatter.format(new Date()));
        IQCPlan plan = iqcPlanRepository.findOne(planId);
        IQCPlanRecord todayRecord = getTodayRecord(planId);
        if (todayRecord != null) {
            record.setId(todayRecord.getId());
        }
        record.setVersion(plan.getVersion());
        record.setPlan(plan);
        record.setOwner(owner);
        record.setName(plan.getName());
        record.setDate(date);
        return iqcPlanRecordRepository.save(record);
    }

    @Override
    public IQCHistoryStatisticSet getPeriodStatistic(List<IQCPlan> plans,
                                                     Date targetDate,
                                                     int count,
                                                     Map<String, String> filters) {
        List<IQCPlanRecord> records = new ArrayList();
        for (IQCPlan plan : plans) {
            records.addAll(getRecords(plan.getId(), targetDate, count));
        }

        IQCHistoryStatisticSet statisticSet = calculateStatisticDataSet(records, filters);
        statisticSet.setFilters(filters);
        statisticSet.setEndDate(targetDate);
        statisticSet.setScope(count);
        return statisticSet;
    }

    @Override
    public IQCHistoryStatisticSet getPeriodStatistic(IQCPlan plan,
                                                     Date targetDate,
                                                     int count,
                                                     Map<String, String> filters) {
        List<IQCPlanRecord> records = getRecords(plan.getId(), targetDate, count);
        IQCHistoryStatisticSet statisticSet =  calculateStatisticDataSet(records, filters);
        statisticSet.setFilters(filters);
        statisticSet.setEndDate(targetDate);
        statisticSet.setScope(count);
        return statisticSet;
    }


    private IQCHistoryStatisticSet calculateStatisticDataSet(List<IQCPlanRecord> records, Map<String, String> filters) {
        IQCHistoryStatisticSet model = new IQCHistoryStatisticSet();

        for (IQCPlanRecord record : records) {
            if (shouldFilterOut(filters, record)) continue;
            for (IQCPlanItem item : record.getItems()) {
                IQCHistoryStatisticItem statisticItem = model.getItem(item);
                for (IQCPlanSpecimen specimen : item.getSpecimens()) {
                    IQCHistoryStatisticData statisticData
                            = statisticItem.getStatisticData(specimen);
                    statisticData.proceed(specimen);
                }
            }
        }

        return model;
    }

    @Override
    public IQCHistoryStatisticComparisonModel getPeriodStatisticComparison(String planId,
                                                                           Date targetDate,
                                                                           int count,
                                                                           Map<String, String> filters) {
        IQCPlan plan = iqcPlanRepository.findOne(planId);
        List<IQCPlan> plans = iqcPlanRepository.findPlansByTemplateId(plan.getTemplate().getId());
        return new IQCHistoryStatisticComparisonModel(getPeriodStatistic(plan, targetDate, count, filters),
                                                      getPeriodStatistic(plans, targetDate, count, filters));
    }

    private boolean shouldFilterOut(Map<String, String> filters, IQCPlanRecord record) {
        if (filters == null) return false;
        for (String additionalDataName : filters.keySet()) {
            String additionalDataValue = filters.get(additionalDataName);
            if (additionalDataValue == null || additionalDataValue.isEmpty()) continue;
            if (!record.getAdditionalData().containsKey(additionalDataName)
                    || !additionalDataValue.equals(record.getAdditionalData().get(additionalDataName))) {
                return true;
            }
        }
        return false;
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
        List<IQCPlanRecord> records = iqcPlanRecordRepository.findRecordsByPlanId(calendar.getTime(), targetDate, planId);

        Collections.sort(records, new Comparator<IQCPlanRecord>() {
            @Override
            public int compare(IQCPlanRecord record1, IQCPlanRecord record2) {
                return record1.getDate().compareTo(record2.getDate());
            }
        });

        return records;
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


    @Override
    public IQCHistoryUnitStatisticModel getUnitStatistic(String planId,
                                                         Date targetDate,
                                                         int count,
                                                         Map<String, String> filters) {
        IQCPlan plan = iqcPlanRepository.findOne(planId);
        IQCHistoryStatisticSet statisticSet = getPeriodStatistic(plan, targetDate, count, filters);
        return new IQCHistoryUnitStatisticModel(new IQCHistorySpecimenStatisticSet(statisticSet),
                statisticSet.getStartDate(),
                statisticSet.getEndDate(),
                statisticSet.getFilters(),
                plan);
    }
}
