package com.stardust.easyassess.track.services;

import com.stardust.easyassess.track.dao.repositories.PlanRepository;
import com.stardust.easyassess.track.dao.repositories.DataRepository;
import com.stardust.easyassess.track.dao.repositories.FormRepository;
import com.stardust.easyassess.track.models.Plan;
import com.stardust.easyassess.track.models.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.*;

@Service
@Scope("request")
public class PlanEntityServiceImpl extends AbstractEntityService<Plan> implements PlanService {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    FormRepository formRepository;

    @Autowired
    FormTemplateService formTemplateService;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    @Transactional
    public void createPlan(Plan plan) {
        plan.setStatus("A");
        plan.setId(UUID.randomUUID().toString());
        planRepository.save(plan);
    }

    @Override
    public Page<Plan> getParticipantPlanList(String participant) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Long owner = Long.parseLong(participant);
        return new PageImpl(planRepository.findByParticipant(owner), new PageRequest(0, 9999, sort), 1);
    }

    @Override
    protected DataRepository getRepository() {
        return planRepository;
    }
}
