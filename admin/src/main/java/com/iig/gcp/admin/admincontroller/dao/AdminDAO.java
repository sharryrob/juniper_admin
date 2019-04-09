package com.iig.gcp.admin.admincontroller.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.iig.gcp.admin.admincontroller.dto.*;

public interface AdminDAO {
	/**
	 * @param user
	 * @return String
	 * @throws Exception
	 */
	String getUser(String user) throws Exception;

	/**
	 * @return String
	 * @throws Exception
	 */
	ArrayList<String> getAllUsers() throws Exception;

	/**
	 * @param username
	 * @param group_seq
	 * @param project
	 * @return ArrayList<String>
	 * @throws Exception
	 */
	String adminupdusrgrp(String username, String group_seq, String project) throws Exception;

	/**
	 * @param userid
	 * @param project
	 * @return ArrayList<Feature>
	 * @throws Exception
	 */
	ArrayList<Feature> getFeatures(String userid, String project) throws Exception;

	/**
	 * @param userid
	 * @param project
	 * @return ArrayList<Feature>
	 * @throws Exception
	 */
	ArrayList<Feature> getFeaturesAlready(String userid, String project) throws Exception;

	/**
	 * @param userid
	 * @param project
	 * @return ArrayList<Group>
	 * @throws Exception
	 */
	ArrayList<Group> getGroups(String userid, String project) throws Exception;

	/**
	 * @param userid
	 * @param project
	 * @return ArrayList<Feature>
	 * @throws Exception
	 */
	ArrayList<Group> getGroupsAlready(String userid, String project) throws Exception;

	/**
	 * @param userid
	 * @return ArrayList<Feature>
	 * @throws Exception
	 */
	int getUserSequence(String userid) throws Exception;

	/**
	 * @param projectId
	 * @param projectName
	 * @param projectOwner
	 * @param projectDescription
	 * @param gsdGroup
	 * @param gsdKey
	 * @param userSequence
	 * @return integer
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public String registerProject(@Valid String projectId, String projectName, String projectOwner,
			String projectDescription, String gsdGroup, String gsdKey, int userSequence)
			throws ClassNotFoundException, Exception;

	/**
	 * @param projectId
	 * @return Project
	 * @throws Exception
	 */
	Project getProject(@Valid String projectId) throws Exception;

	/**
	 * @param projectSeq
	 * @param user_sequence
	 * @return String
	 * @throws Exception
	 */
	String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception;

	/**
	 * @return List<String>
	 * @throws Exception
	 */
	List<String> getProjects() throws Exception;

	/**
	 * @param projectId
	 * @return Project
	 * @throws Exception
	 */
	Project fetchProjectDetails(@Valid String projectId) throws Exception;

	/**
	 * @param projectId
	 * @param projectName
	 * @param projectOwner
	 * @param projectDescription
	 * @param gsdGroup
	 * @param gsdKey
	 * @return Project
	 * @throws Exception
	 */
	String updateProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription,
			String gsdGroup, String gsdKey) throws Exception;

	/**
	 * @return String
	 * @throws Exception
	 */
	String getProAdminFeatures() throws Exception;

	/**
	 * @param projectseq
	 * @param selectUser_Seq
	 * @throws Exception
	 */
	void deleteEntries(int projectseq, int selectUser_Seq) throws Exception;

	/**
	 * @param userId
	 * @param domain
	 * @param onboardedUserFullName
	 * @param userEmail
	 * @param loggedInUser
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	String registerUser(@Valid String userId, String domain, String onboardedUserFullName, String userEmail,
			String loggedInUser) throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @param userId
	 * @param domain
	 * @param onboardedUserFullName
	 * @param userEmail
	 * @param loggedInUser
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	String updateUser(@Valid String userId, String domain, String onboardedUserFullName, String userEmail,
			String loggedInUser) throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @return List<String>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	List<String> fetchUserIds() throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @param userId
	 * @return UserAccount
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	UserAccount fetchUserAttributes(String userId) throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @param userSequence
	 * @param project
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	String linkUserGroupsToProject(int userSequence, Project project)
			throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @param psid
	 * @return integer
	 * @throws Exception
	 */
	int validatePSID(@Valid String psid) throws Exception;

	/**
	 * @param username
	 * @return ArrayList<Project>
	 * @throws Exception
	 */
	ArrayList<Project> getProjects1(String username) throws Exception;

	/**
	 * @param region
	 * @return List<CountryMaster>
	 * @throws Exception
	 */
	List<CountryMaster> fetchCountries(@Valid String region) throws Exception;

	/**
	 * @param system_eim
	 * @return integer
	 * @throws Exception
	 */
	int checkEIM(@Valid String system_eim) throws Exception;

	/**
	 * @param systemEIM
	 * @param systemName
	 * @param region
	 * @param country
	 * @param environmentType
	 * @param userName
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	String registerSystem(@Valid String systemEIM, String systemName, String region, String country,
			String environmentType, String userName) throws ClassNotFoundException, SQLException, Exception;

}
