package com.stardust.easyassess.track.models.form;

import java.util.HashMap;
import java.util.Map;

public class GroupRow extends FormElement {
	
	private String guid;
	
	private TestSubject item;

	private Map<String, SpecimenOption> optionMap = new HashMap();

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public TestSubject getItem() {
		return item;
	}

	public void setItem(TestSubject item) {
		this.item = item;
	}

	public Map<String, SpecimenOption> getOptionMap() {
		return optionMap;
	}

	public void setOptionMap(Map<String, SpecimenOption> optionMap) {
		this.optionMap = optionMap;
	}
}
