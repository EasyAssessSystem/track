package com.stardust.easyassess.track.models.form;

import java.util.List;

public class GroupSection extends FormElement {
	
	private String guid;
	
	private String name;
	
	private List<Specimen> specimens;

	private List<CodeGroup> codeGroups;

	private List<GroupRow> rows;

	public List<CodeGroup> getCodeGroups() {
		return codeGroups;
	}

	public void setCodeGroups(List<CodeGroup> codeGroups) {
		this.codeGroups = codeGroups;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Specimen> getSpecimens() {
		return specimens;
	}

	public void setSpecimens(List<Specimen> specimens) {
		this.specimens = specimens;
	}

	public List<GroupRow> getRows() {
		return rows;
	}

	public void setRows(List<GroupRow> rows) {
		this.rows = rows;
	}	
}
