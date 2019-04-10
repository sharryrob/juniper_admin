package com.iig.gcp.admin.admincontroller.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.iig.gcp.admin.admincontroller.dto.*;

public interface AdminDAO {
	String getUser(String user) throws Exception;
	ArrayList<String> getAllUsers() throws Exception;
	String adminupdusrgrp(String username,String group_seq,String project) throws Exception;

	ArrayList<Feature> getFeatures(String userid, String project) throws Exception;

	ArrayList<Feature> getFeaturesAlready(String userid, String project)throws Exception;

	ArrayList<Group> getGroups(String userid, String project) throws Exception;

	ArrayList<Group> getGroupsAlready(String userid, String project)throws Exception;
	
	int getUserSequence(String userid)throws Exception;

	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription,String gsdGroup,String gsdKey, int userSequence) throws ClassNotFoundException, Exception;
	Project getProject(@Valid String projectId) throws Exception;
	String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception;
	
	List<String> getProjects() throws Exception;
	Project fetchProjectDetails(@Valid String projectId) throws Exception;
	String updateProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription,String gsdGroup,String gsdKey) throws Exception;
	String getProAdminFeatures()throws Exception;
	void deleteEntries(int projectseq, int selectUser_Seq) throws Exception;

	String registerUser(@Valid String userId,  String domain,String onboardedUserFullName, String userEmail, String loggedInUser) throws ClassNotFoundException, SQLException, Exception;

	String updateUser(@Valid String userId, String domain, String onboardedUserFullName,String userEmail, String loggedInUser)throws ClassNotFoundException, SQLException, Exception;

	List<String> fetchUserIds() throws ClassNotFoundException, SQLException, Exception;

	UserAccount fetchUserAttributes(String userId) throws ClassNotFoundException, SQLException, Exception;
	String linkUserGroupsToProject(int userSequence, Project project) throws ClassNotFoundException, SQLException, Exception;
	int validatePSID(@Valid String psid) throws Exception;
	ArrayList<Project> getProjects1(String username) throws Exception;
	List<CountryMaster> fetchCountries(@Valid String region) throws Exception;
	int checkEIM(@Valid String system_eim) throws Exception;
	String registerSystem(@Valid String systemEIM, String systemName, String region, String country,
			String environmentType, String userName) throws ClassNotFoundException, SQLException, Exception;
	
}
