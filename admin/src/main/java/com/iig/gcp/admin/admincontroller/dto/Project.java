package com.iig.gcp.admin.admincontroller.dto;

public class Project {

	private int project_sequence;
	private String project_id;
	private String project_name;
	private String created_by;
	private String created_date;
	private String updated_by;
	private String updated_date;
	private String project_owner;
	private String project_description;
	private String gsd_group;
	private String gsd_key;

	/**
	 * @return integer
	 */
	public int getProject_sequence() {
		return this.project_sequence;
	}

	/**
	 * @param project_sequence
	 */
	public void setProject_sequence(final int project_sequence) {
		this.project_sequence = project_sequence;
	}

	/**
	 * @return String
	 */
	public String getProject_id() {
		return this.project_id;
	}

	/**
	 * @param project_id
	 */
	public void setProject_id(final String project_id) {
		this.project_id = project_id;
	}

	/**
	 * @return String
	 */
	public String getProject_name() {
		return this.project_name;
	}

	/**
	 * @param project_name
	 */
	public void setProject_name(final String project_name) {
		this.project_name = project_name;
	}

	/**
	 * @return String
	 */
	public String getCreated_by() {
		return this.created_by;
	}

	/**
	 * @param created_by
	 */
	public void setCreated_by(final String created_by) {
		this.created_by = created_by;
	}

	/**
	 * @return String
	 */
	public String getCreated_date() {
		return this.created_date;
	}

	/**
	 * @param created_date
	 */
	public void setCreated_date(final String created_date) {
		this.created_date = created_date;
	}

	/**
	 * @return String
	 */
	public String getUpdated_by() {
		return this.updated_by;
	}

	/**
	 * @param updated_by
	 */
	public void setUpdated_by(final String updated_by) {
		this.updated_by = updated_by;
	}

	/**
	 * @return String
	 */
	public String getUpdated_date() {
		return this.updated_date;
	}

	/**
	 * @param updated_date
	 */
	public void setUpdated_date(final String updated_date) {
		this.updated_date = updated_date;
	}

	/**
	 * @return String
	 */
	public String getProject_owner() {
		return this.project_owner;
	}

	/**
	 * @param project_owner
	 */
	public void setProject_owner(final String project_owner) {
		this.project_owner = project_owner;
	}

	/**
	 * @return String
	 */
	public String getProject_description() {
		return this.project_description;
	}

	/**
	 * @param project_description
	 */
	public void setProject_description(final String project_description) {
		this.project_description = project_description;
	}

	/**
	 * @return String
	 */
	public String getGsd_group() {
		return this.gsd_group;
	}

	/**
	 * @param gsd_group
	 */
	public void setGsd_group(final String gsd_group) {
		this.gsd_group = gsd_group;
	}

	/**
	 * @return String
	 */
	public String getGsd_key() {
		return this.gsd_key;
	}

	/**
	 * @param gsd_key
	 */
	public void setGsd_key(final String gsd_key) {
		this.gsd_key = gsd_key;
	}
}
