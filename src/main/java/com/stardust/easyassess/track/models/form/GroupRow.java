package com.stardust.easyassess.track.models.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupRow extends FormElement {
	
	private String guid;
	
	private TestSubject item;

	private Map<String, OptionValue> optionMap = new HashMap();

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

	public Map<String, OptionValue> getOptionMap() {
		return optionMap;
	}

	public void setOptionMap(Map<String, OptionValue> optionMap) {
		this.optionMap = optionMap;
	}
}
