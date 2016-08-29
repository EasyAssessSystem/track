package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.Plan;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PlanRepository extends DataRepository<Plan, String> {
    default Class<Plan> getEntityClass() {
        return Plan.class;
    }

    List<Plan> findPlansByOwner(String owner);

    @Query("{participants.?0:{$exists:true}}")
    List<Plan> findByParticipant(Long participant);
}
