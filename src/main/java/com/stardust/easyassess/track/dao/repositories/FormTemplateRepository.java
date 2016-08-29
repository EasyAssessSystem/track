package com.stardust.easyassess.track.dao.repositories;

import com.stardust.easyassess.track.models.form.FormTemplate;

public interface FormTemplateRepository extends DataRepository<FormTemplate, String> {
    default Class<FormTemplate> getEntityClass() {
        return FormTemplate.class;
    }
}
