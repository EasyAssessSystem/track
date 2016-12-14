package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IQCPlanRecordRepository extends DataRepository<IQCPlanRecord, String> {
    default Class<IQCPlanRecord> getEntityClass() {
        return IQCPlanRecord.class;
    }

    @Query("SELECT r FROM records r WHERE r.plan.id = :planId and r.date >= :start and r.date <= :end")
    List<IQCPlanRecord> getRecordsByDateAndPlanId(Date start, Date end, String planId);
}
