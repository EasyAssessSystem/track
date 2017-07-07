package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.dao.repositories.DataRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanRepository;
import com.stardust.easyassess.track.dao.repositories.IQCPlanTemplateRepository;
import com.stardust.easyassess.track.models.Owner;
import com.stardust.easyassess.track.models.plan.IQCHistorySet;
import com.stardust.easyassess.track.models.plan.IQCPlan;
import com.stardust.easyassess.track.models.plan.IQCPlanTemplate;
import com.stardust.easyassess.track.models.statistics.IQCHistorySpecimenStatisticSet;
import com.stardust.easyassess.track.models.statistics.IQCHistoryGatherStatisticModel;
import com.stardust.easyassess.track.models.statistics.IQCHistoryStatisticSet;
import com.stardust.easyassess.track.models.statistics.IQCHistoryUnitStatisticModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("request")
public class IQCPlanTemplateServiceImpl extends AbstractEntityService<IQCPlanTemplate> implements IQCPlanTemplateService {

    @Autowired
    private IQCPlanService iqcPlanService;

    @Autowired
    private IQCPlanRepository iqcPlanRepository;

    @Autowired
    private IQCPlanTemplateRepository iqcPlanTemplateRepository;

    @Override
    protected DataRepository<IQCPlanTemplate, String> getRepository() {
        return iqcPlanTemplateRepository;
    }

    @Override
    public void remove(String id) {
        super.remove(id);
        List<IQCPlan> plans = iqcPlanRepository.findPlansByTemplateId(id);
        if (plans != null) {
            for (IQCPlan plan : plans) {
                iqcPlanService.remove(plan.getId());
            }
        }
    }


    @Override
    public IQCPlanTemplate save(IQCPlanTemplate model) {
        IQCPlanTemplate template = getRepository().save(model);

        List<IQCPlan> plans = iqcPlanRepository.findPlansByTemplateId(model.getId());
        if (plans.size() > 0) {
            for (IQCPlan plan : plans) {
                plan.setName(template.getName());
                plan.setItems(template.getItems());
                plan.setAdditionalItems(template.getAdditionalItems());
                plan.setVersion(plan.getVersion() + 1);
                iqcPlanService.save(plan);
            }
        } else {
            for (String ownerId : model.getParticipants().keySet()) {
                Owner owner = new Owner(ownerId, model.getParticipants().get(ownerId));
                iqcPlanService.copyFromTemplate(template, owner);
            }
        }

        return template;
    }

    @Override
    public List<IQCHistorySet> getRecordsWithPlans(List<IQCPlan> plans, Date targetDate) {
        List<IQCHistorySet> results = new ArrayList();
        for (IQCPlan plan : plans) {
            IQCHistorySet result = new IQCHistorySet();
            result.setOwner(plan.getOwner());
            result.setPlanName(plan.getName());
            result.setTargetRecords(iqcPlanService.getRecords(plan.getId(), targetDate, 20));
            results.add(result);
        }
        return results;
    }

    @Override
    public IQCHistoryGatherStatisticModel getGatherStatisticData(String templateId,
                                                                 Date targetDate,
                                                                 int count,
                                                                 Map<String, String> filters) {
        List<IQCPlan> plans = iqcPlanRepository.findPlansByTemplateId(templateId);
        IQCPlanTemplate template = iqcPlanTemplateRepository.findOne(templateId);
        IQCHistoryStatisticSet statisticSet = iqcPlanService.getPeriodStatistic(plans, targetDate, count, filters);
        return new IQCHistoryGatherStatisticModel(new IQCHistorySpecimenStatisticSet(statisticSet),
                statisticSet.getStartDate(),
                statisticSet.getEndDate(),
                statisticSet.getFilters(),
                template);
    }

    @Override
    public Map<String, IQCHistoryUnitStatisticModel> getUnitStatisticCollection(String templateId,
                                                                                Date targetDate,
                                                                                int count,
                                                                                Map<String, String> filters) {
        Map<String, IQCHistoryUnitStatisticModel> model = new HashMap();
        List<IQCPlan> plans = iqcPlanRepository.findPlansByTemplateId(templateId);
        for (IQCPlan plan : plans) {
            IQCHistoryStatisticSet statisticSet = iqcPlanService.getPeriodStatistic(plan, targetDate, count, filters);
            model.put(plan.getOwner().getName(), new IQCHistoryUnitStatisticModel(new IQCHistorySpecimenStatisticSet(statisticSet),
                    statisticSet.getStartDate(),
                    statisticSet.getEndDate(),
                    statisticSet.getFilters(),
                    plan));
        }
        return model;
    }
}
