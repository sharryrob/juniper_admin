package com.iig.gcp.admin.admincontroller.dto;

public class Group {

	private String group_name;
	private int group_sequence;

	/**
	 * @return String
	 */
	public String getGroup_name() {
		return group_name;
	}

	/**
	 * @param group_name
	 */
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	/**
	 * @return integer
	 */
	public int getGroup_sequence() {
		return group_sequence;
	}

	/**
	 * @param group_sequence
	 */
	public void setGroup_sequence(int group_sequence) {
		this.group_sequence = group_sequence;
	}

}
