package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.form.Form;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormRepository extends DataRepository<Form, String> {
    @Query("SELECT f FROM forms f WHERE f.plan.id = :id")
    List<Form> findFormsByPlanId(String id);

    default Class<Form> getEntityClass() {
        return Form.class;
    }
}
