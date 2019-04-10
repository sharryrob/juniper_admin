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
import com.iig.gcp.admin.admincontroller.dto.UserAccount;;


public interface AdminService {

	String getUser(String user) throws Exception;
	
	ArrayList<String> getAllUsers() throws Exception;
	
	ArrayList<Group> getGroups(String user, String project) throws Exception;
	ArrayList<Group> getGroupsAlready(String user, String project) throws Exception;



String onBoardUser(@Valid String x, @Valid boolean isAdmin, @Valid String project, HttpServletRequest request)throws Exception;
	String registerUserInSystem(@Valid String userId,  String domain, String onboardedUserFullName,String userEmail, String loggedInUser) throws ClassNotFoundException, SQLException, Exception;

	ArrayList<Feature> getFeatures(String userid, String project)throws Exception;

	ArrayList<Feature> getFeaturesAlready(String userid, String project)throws Exception;

	int getUserSequence(String userid)throws Exception;
	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription,String gsdGroup,String gsdKey, int userSequence) throws ClassNotFoundException, Exception;
	Project getProject(@Valid String projectId) throws Exception;

	String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception;

	//Project
	List<String> getProjects() throws Exception;
	Project fetchProjectDetails(@Valid String projectId) throws Exception;
	String updateProject(@Valid String projectId, String projectName, String projectOwner, String projectDetails,String gsdGroup,String gsdKey,String user_id) throws Exception;

	String updateUser(@Valid String userId, String userPwd, String onboardedUserFullName,String userEmail, String loggedInUser) throws ClassNotFoundException, SQLException, Exception;
	List<String> fetchUserIds() throws ClassNotFoundException, SQLException, Exception;
	UserAccount fetchUserDetails(String user_val) throws ClassNotFoundException, SQLException, Exception;
	String linkUserGroupsToNewProject(int user_sequence, Project project) throws ClassNotFoundException, SQLException, Exception;

	int checkPSID(@Valid String psid) throws Exception;
	ArrayList<Project> getProjects1(String username) throws Exception;
	
	List<CountryMaster> fetchCountries(@Valid String region) throws Exception;
	int checkEIM(@Valid String system_eim) throws Exception;
	String registerSystem(@Valid String systemEIM, String systemName, String region, String country,
			String environmentType, String userName) throws ClassNotFoundException, SQLException, Exception;
	
}
