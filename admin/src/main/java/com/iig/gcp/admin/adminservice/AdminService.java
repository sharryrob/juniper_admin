package com.iig.gcp.admin.adminservice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.iig.gcp.admin.admincontroller.dto.CountryMaster;
import com.iig.gcp.admin.admincontroller.dto.Feature;
import com.iig.gcp.admin.admincontroller.dto.Group;
import com.iig.gcp.admin.admincontroller.dto.Project;
import com.iig.gcp.admin.admincontroller.dto.UserAccount;

public interface AdminService {

	/**
	 * @param user
	 * @return String
	 * @throws Exception
	 */
	String getUser(String user) throws Exception;

	/**
	 * @return ArrayList<String>
	 * @throws Exception
	 */
	ArrayList<String> getAllUsers() throws Exception;

	/**
	 * @param user
	 * @param project
	 * @return ArrayList<Group>
	 * @throws Exception
	 */
	ArrayList<Group> getGroups(String user, String project) throws Exception;

	/**
	 * @param user
	 * @param project
	 * @return ArrayList<Group>
	 * @throws Exception
	 */
	ArrayList<Group> getGroupsAlready(String user, String project) throws Exception;

	/**
	 * @param x
	 * @param isAdmin
	 * @param project
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	String onBoardUser(@Valid String x, @Valid boolean isAdmin, @Valid String project, HttpServletRequest request)
			throws Exception;

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
	String registerUserInSystem(@Valid String userId, String domain, String onboardedUserFullName, String userEmail,
			String loggedInUser) throws ClassNotFoundException, SQLException, Exception;

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
	 * @return integer
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
	 * @return String
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

	// Project
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
	 * @param projectDetails
	 * @param gsdGroup
	 * @param gsdKey
	 * @param user_id
	 * @return String
	 * @throws Exception
	 */
	String updateProject(@Valid String projectId, String projectName, String projectOwner, String projectDetails,
			String gsdGroup, String gsdKey, String user_id) throws Exception;

	/**
	 * @param userId
	 * @param userPwd
	 * @param onboardedUserFullName
	 * @param userEmail
	 * @param loggedInUser
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	String updateUser(@Valid String userId, String userPwd, String onboardedUserFullName, String userEmail,
			String loggedInUser) throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @return List<String>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	List<String> fetchUserIds() throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @param user_val
	 * @return UserAccount
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	UserAccount fetchUserDetails(String user_val) throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @param user_sequence
	 * @param project
	 * @return String
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	String linkUserGroupsToNewProject(int user_sequence, Project project)
			throws ClassNotFoundException, SQLException, Exception;

	/**
	 * @param psid
	 * @return integer
	 * @throws Exception
	 */
	int checkPSID(@Valid String psid) throws Exception;

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
