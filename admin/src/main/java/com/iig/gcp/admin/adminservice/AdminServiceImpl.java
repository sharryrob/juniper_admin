package com.iig.gcp.admin.adminservice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.admin.admincontroller.dao.AdminDAO;
import com.iig.gcp.admin.admincontroller.dto.CountryMaster;
import com.iig.gcp.admin.admincontroller.dto.Feature;
import com.iig.gcp.admin.admincontroller.dto.Group;
import com.iig.gcp.admin.admincontroller.dto.Project;
import com.iig.gcp.admin.admincontroller.dto.UserAccount;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO admindao;

	/**
	 * @return String
	 */
	@Override
	public String getUser(final String user) throws Exception {
		return admindao.getUser(user);
	}

	/**
	 * @return ArrayList<String>
	 */
	@Override
	public ArrayList<String> getAllUsers() throws Exception {
		return admindao.getAllUsers();
	}

	/**
	 * @return String
	 */
	@Override
	public String onBoardUser(@Valid final String x, @Valid final boolean isAdmin, @Valid final String project,
			final HttpServletRequest request) throws Exception {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(x);
		} catch (JSONException e) {
			throw new Exception("Please Select User ID for Onboarding");
		}
		String group_seq = jsonObject.getString("group");
		String username = jsonObject.getString("username");
		return admindao.adminupdusrgrp(username, group_seq, project);

	}

	/**
	 * @return String
	 */
	@Override
	public String registerProject(@Valid final String projectId, final String projectName, final String projectOwner,
			final String projectDetails, final String gsdGroup, final String gsdKey, final int userSequence)
			throws ClassNotFoundException, Exception {
		return admindao.registerProject(projectId, projectName, projectOwner, projectDetails, gsdGroup, gsdKey,
				userSequence);
	}

	/**
	 * @return Project
	 */
	@Override
	public Project getProject(@Valid final String projectId) throws Exception {
		return admindao.getProject(projectId);
	}

	/**
	 * @return String
	 */
	@Override
	public String registerAddAdminAccess(final int projectSeq, final int user_sequence) throws Exception {
		return admindao.registerAddAdminAccess(projectSeq, user_sequence);
	}

	/**
	 * @return ArrayList<Feature>
	 */
	@Override
	public ArrayList<Feature> getFeatures(final String userid, final String project) throws Exception {

		return admindao.getFeatures(userid, project);
	}

	/**
	 * @return ArrayList<Feature>
	 */
	@Override
	public ArrayList<Feature> getFeaturesAlready(final String userid, final String project) throws Exception {

		return admindao.getFeaturesAlready(userid, project);
	}

	/**
	 * @return integer
	 */
	@Override
	public int getUserSequence(final String userid) throws Exception {

		return admindao.getUserSequence(userid);
	}

	/**
	 * @return ArrayList<Group>
	 */
	@Override
	public ArrayList<Group> getGroups(final String user, final String project) throws Exception {

		return admindao.getGroups(user, project);
	}

	/**
	 * @return ArrayList<Group>
	 */
	@Override
	public ArrayList<Group> getGroupsAlready(final String user, final String project) throws Exception {

		return admindao.getGroupsAlready(user, project);
	}

	/**
	 * @return List<String>
	 */
	@Override
	public List<String> getProjects() throws Exception {
		return admindao.getProjects();
	}

	/**
	 * @return Project
	 */
	@Override
	public Project fetchProjectDetails(@Valid final String projectId) throws Exception {
		return admindao.fetchProjectDetails(projectId);
	}

	/**
	 * @return String
	 */
	@Override
	public String updateProject(@Valid final String projectId, final String projectName, final String projectOwner,
			final String projectDescription, final String gsdGroup, final String gsdKey, final String user_id)
			throws Exception {
		return admindao.updateProject(projectId, projectName, projectOwner, projectDescription, gsdGroup, gsdKey);
	}

	/**
	 * @return String
	 */
	@Override
	public String registerUserInSystem(@Valid final String userId, final String domain,
			final String onboardedUserFullName, final String userEmail, final String loggedInUser) throws Exception {
		return admindao.registerUser(userId, domain, onboardedUserFullName, userEmail, loggedInUser);
	}

	/**
	 * @return String
	 */
	@Override
	public String updateUser(@Valid final String userId, final String domain, final String onboardedUserFullName,
			final String userEmail, final String loggedInUser) throws Exception {
		return admindao.updateUser(userId, domain, onboardedUserFullName, userEmail, loggedInUser);
	}

	/**
	 * @return List<String>
	 */
	@Override
	public List<String> fetchUserIds() throws Exception {
		return admindao.fetchUserIds();
	}

	/**
	 * @return UserAccount
	 */
	@Override
	public UserAccount fetchUserDetails(final String userId) throws Exception {
		return admindao.fetchUserAttributes(userId);
	}

	/**
	 * @return String
	 */
	@Override
	public String linkUserGroupsToNewProject(final int userSequence, final Project project) throws Exception {
		return admindao.linkUserGroupsToProject(userSequence, project);
	}

	/**
	 * @return integer
	 */
	@Override
	public int checkPSID(@Valid final String psid) throws Exception {
		return admindao.validatePSID(psid);
	}

	/**
	 * @return ArrayList<Project>
	 */
	@Override
	public ArrayList<Project> getProjects1(final String username) throws Exception {
		return admindao.getProjects1(username);
	}

	/**
	 * @return List<CountryMaster>
	 */
	@Override
	public List<CountryMaster> fetchCountries(@Valid final String region) throws Exception {
		return admindao.fetchCountries(region);
	}

	/**
	 * @return integer
	 */
	@Override
	public int checkEIM(@Valid final String system_eim) throws Exception {
		return admindao.checkEIM(system_eim);
	}

	/**
	 * @return String
	 */
	@Override
	public String registerSystem(@Valid final String systemEIM, final String systemName, final String region,
			final String country, final String environmentType, final String userName)
			throws ClassNotFoundException, SQLException, Exception {
		return admindao.registerSystem(systemEIM, systemName, region, country, environmentType, userName);
	}

}
