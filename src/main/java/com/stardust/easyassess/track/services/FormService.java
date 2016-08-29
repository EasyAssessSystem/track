package com.stardust.easyassess.track.services;


import com.stardust.easyassess.track.models.form.Form;


public interface FormService extends EntityService<Form> {
    Form submit(Form form);
}
