package com.iig.gcp.admin.admincontroller.dto;

public class CountryMaster {

	private int country_id;
	private String country_code;
	private String country_name;
	private String region_code;
	private String region_name;

	/**
	 * @return integer
	 */
	public int getCountry_id() {
		return country_id;
	}

	/**
	 * @param country_id
	 */
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	/**
	 * @return String
	 */
	public String getCountry_code() {
		return country_code;
	}

	/**
	 * @param country_code
	 */
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	/**
	 * @return String
	 */
	public String getCountry_name() {
		return country_name;
	}

	/**
	 * @param country_name
	 */
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	/**
	 * @return String
	 */
	public String getRegion_code() {
		return region_code;
	}

	/**
	 * @param region_code
	 */
	public void setRegion_code(String region_code) {
		this.region_code = region_code;
	}

	/**
	 * @return String
	 */
	public String getRegion_name() {
		return region_name;
	}

	/**
	 * @param region_name
	 */
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
}
