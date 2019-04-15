package com.iig.gcp.admin.admincontroller.dto;

public class Feature {

	private String feature_name;
	private int feature_order;
	private int feature_level;
	private int feature_sequence;
	private int selected_user_sequence;

	/**
	 * @return integer
	 */
	public int getSelected_user_sequence() {
		return selected_user_sequence;
	}

	/**
	 * @param selected_user_sequence
	 */
	public void setSelected_user_sequence(int selected_user_sequence) {
		this.selected_user_sequence = selected_user_sequence;
	}

	/**
	 * @return String
	 */
	public String getFeature_name() {
		return feature_name;
	}

	/**
	 * @param feature_name
	 */
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}

	/**
	 * @return integer
	 */
	public int getFeature_order() {
		return feature_order;
	}

	/**
	 * @param feature_order
	 */
	public void setFeature_order(int feature_order) {
		this.feature_order = feature_order;
	}

	/**
	 * @return integer
	 */
	public int getFeature_level() {
		return feature_level;
	}

	/**
	 * @param feature_level
	 */
	public void setFeature_level(int feature_level) {
		this.feature_level = feature_level;
	}

	/**
	 * @return integer
	 */
	public int getFeature_sequence() {
		return feature_sequence;
	}

	/**
	 * @param feature_sequence
	 */
	public void setFeature_sequence(int feature_sequence) {
		this.feature_sequence = feature_sequence;
	}

}
