package com.stardust.easyassess.track.models.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupRow extends FormElement {
	
	private String guid;
	
	private TestSubject item;
	
	private List<OptionValue> options = new ArrayList();

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

	public List<OptionValue> getOptions() {
		return options;
	}

	public void setOptions(List<OptionValue> options) {
		this.options = options;
	}
}
