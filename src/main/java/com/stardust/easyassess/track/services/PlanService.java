package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.Plan;
import com.stardust.easyassess.track.models.form.Specimen;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PlanService extends EntityService<Plan> {
    void createPlan(Plan plan);

    Page<Plan> getParticipantPlanList(String participant);
}
