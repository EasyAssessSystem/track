package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.plan.IQCPlanRecord;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface IQCPlanRecordRepository extends DataRepository<IQCPlanRecord, String> {
    default Class<IQCPlanRecord> getEntityClass() {
        return IQCPlanRecord.class;
    }

    @Query("{date:{$gte:?0,$lte:?1}, planId:{$eq:?2}}")
    List<IQCPlanRecord> findRecordsByPlanId(Date start, Date end, String planId);

    @Query("{planId:{$eq:?0}, date:{$regex:'/^?1/'}}")
    IQCPlanRecord findRecordByPlanIdAndDate(String planId, Date date);
}
