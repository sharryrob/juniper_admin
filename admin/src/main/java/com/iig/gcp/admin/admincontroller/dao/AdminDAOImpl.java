package com.iig.gcp.admin.admincontroller.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.iig.gcp.admin.admincontroller.dto.Project;
import com.iig.gcp.admin.admincontroller.dto.UserAccount;
import com.iig.gcp.admin.admincontroller.dto.Group;
import com.iig.gcp.admin.admincontroller.dto.Feature;
import com.iig.gcp.admin.admincontroller.dto.CountryMaster;

import com.iig.gcp.admin.admincontroller.utils.ConnectionUtils;

@Component
public class AdminDAOImpl implements AdminDAO {

	@Autowired
	private ConnectionUtils ConnectionUtils;

	private static String SPACE = " ";
	private static String COMMA = ",";
	private static String QUOTE = "\'";
	private static String PROJECT_MASTER_TABLE = "JUNIPER_PROJECT_MASTER";
	private static String PROJECT_LINK_TABLE = "JUNIPER_PRO_U_FEAT_LINK";
	private static String USER_MASTER_TABLE = "JUNIPER_USER_MASTER";
	private static String USER_GROUP_MASTER_TABLE = "JUNIPER_USER_GROUP_MASTER";
	private static String GROUP_MASTER_TABLE = "JUNIPER_GROUP_MASTER";
	private static String UGROUP_GROUP_TABLE = "JUNIPER_UGROUP_USER_LINK";

	private static String SYSTEM_MASTER_TABLE = "JUNIPER_SYSTEM_MASTER";
	private static String COUNTRY_REGION_MAPPING_TABLE = "JUNIPER_REGION_COUNTRY_MASTER";

	// User_Group_Account_Constants
	private static String JUNIPER_JADMIN_GROUP = "JUNIPER_JADMIN";

	/**
	 * @return String
	 */
	@Override
	public String adminupdusrgrp(final String username, final String group_seq, final String project) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		int user_seq = 0; 
		int proj_seq = 0;
		String[] group_int = group_seq.split(",");
		String message = "";
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection
					.prepareStatement("select user_sequence from juniper_user_master where user_id='" + username + "'");
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				user_seq = rs.getInt(1);
			}
			if (!group_seq.isEmpty()) {
				PreparedStatement pstm2 = connection.prepareStatement(
						"select PROJECT_SEQUENCE from juniper_user_group_master where USER_GROUP_SEQUENCE='"
								+ Integer.parseInt(group_int[0]) + "'");
				ResultSet rs2 = pstm2.executeQuery();
				while (rs2.next()) {
					proj_seq = rs2.getInt(1);
				}
			}
			String pstm3;
			if (proj_seq == 0) {
				pstm3 = "delete from juniper_ugroup_user_link where USER_SEQUENCE=" + user_seq
						+ " and project_sequence in (select project_sequence from JUNIPER_PROJECT_MASTER where project_id ='"
						+ project + "')";
				Statement statement1 = connection.createStatement();
				statement1.executeUpdate(pstm3);
				message = "" + username + " revoked from group successfully";
			} else {
				pstm3 = "delete from juniper_ugroup_user_link where USER_SEQUENCE=" + user_seq
						+ " and project_sequence=" + proj_seq;
				Statement statement1 = connection.createStatement();
				statement1.executeUpdate(pstm3);
				int i = 0;
				String pstm1;
				while (i < group_int.length) {
					pstm1 = "insert into JUNIPER_UGROUP_USER_LINK (USER_GROUP_SEQUENCE,USER_SEQUENCE,PROJECT_SEQUENCE)"
							+ " values (" + Integer.parseInt(group_int[i]) + "," + user_seq + "," + proj_seq + ")";
					Statement statement = connection.createStatement();
					statement.executeUpdate(pstm1);
					i++;
					message = "" + username + " mapped with group successfully";
				}
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return message;
	}

	/**
	 * @return ArrayList<Group>
	 */
	@Override
	public ArrayList<Group> getGroups(final String userid, final String project) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Group group = null;
		ArrayList<Group> arrGroups = new ArrayList<Group>();
		try {
			connection = ConnectionUtils.getConnection();
			pstm = connection.prepareStatement(
					"select user_group_sequence,user_group_name from JUNIPER_USER_GROUP_MASTER where project_sequence = \r\n"
							+ "(select project_sequence from JUNIPER_PROJECT_MASTER where project_id='" + project
							+ "') and user_group_sequence not in "
							+ "(select user_group_sequence from JUNIPER_USER_GROUP_MASTER where USER_GROUP_SEQUENCE in "
							+ "(select USER_GROUP_SEQUENCE from JUNIPER_UGROUP_USER_LINK where user_sequence="
							+ "(select user_sequence from JUNIPER_USER_MASTER where user_id='" + userid + "')))");
			rs = pstm.executeQuery();
			while (rs.next()) {
				group = new Group();
				group.setGroup_name(rs.getString(2));
				group.setGroup_sequence(rs.getInt(1));
				arrGroups.add(group);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return arrGroups;
	}

	/**
	 * @return ArrayList<Group>
	 */
	@Override
	public ArrayList<Group> getGroupsAlready(final String userid, final String project) throws Exception {
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Group group = null;
		ArrayList<Group> arrGroups = new ArrayList<Group>();
		try {
			connection = ConnectionUtils.getConnection();
			pstm = connection
					.prepareStatement("select user_group_sequence,user_group_name from JUNIPER_USER_GROUP_MASTER "
							+ " where project_sequence in (select project_sequence from JUNIPER_PROJECT_MASTER where project_id='"
							+ project + "') "
							+ " and  USER_GROUP_SEQUENCE in (select USER_GROUP_SEQUENCE from JUNIPER_UGROUP_USER_LINK where user_sequence IN(select user_sequence from JUNIPER_USER_MASTER where user_id='"
							+ userid + "'))");
			rs = pstm.executeQuery();
			while (rs.next()) {
				group = new Group();
				group.setGroup_name(rs.getString(2));
				group.setGroup_sequence(rs.getInt(1));
				arrGroups.add(group);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return arrGroups;
	}

	/**
	 * @return ArrayList<String>
	 */
	@Override
	public ArrayList<String> getAllUsers() throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<String> userid = new ArrayList<String>();
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement("select user_id from juniper_user_master");
			rs = pstm.executeQuery();
			while (rs.next()) {
				userid.add(rs.getString(1));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return userid;
	}

	/**
	 * @return String
	 */
	@Override
	public String getUser(final String user) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int stat = 1;
		String userid = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement("select user_id from juniper_user_master where user_id='" + user + "'");
			rs = pstm.executeQuery();
			while (rs.next()) {
				userid = rs.getString(1);
				stat = 0;
				break;
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return stat + userid;
	}

	/*
	 * This method accepts inputs from project registration form and add in project
	 * master table. (non-Javadoc)
	 * 
	 * @see com.iig.gcp.project.dao.ProjectDAO#registerProject(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	/**
	 * @return String
	 */
	@Override
	public String registerProject(@Valid final String projectId, final String projectName, final String projectOwner,
			final String projectDescription, final String gsdGroup, final String gsdKey, final int userSequence)
			throws ClassNotFoundException, Exception {

		Connection conn = null;

		try {
			conn = this.ConnectionUtils.getConnection();
			String registerProjectQuery = "INSERT INTO" + SPACE + PROJECT_MASTER_TABLE + SPACE
					+ "(project_id,project_name,project_owner,project_description,gsd_group,gsd_key,created_by,created_date )VALUES ("
					+ QUOTE + projectId + QUOTE + COMMA + QUOTE + projectName + QUOTE + COMMA + QUOTE + projectOwner
					+ QUOTE + COMMA + QUOTE + projectDescription + QUOTE + COMMA + QUOTE + gsdGroup + QUOTE + COMMA
					+ QUOTE + gsdKey + QUOTE + COMMA + QUOTE + userSequence + QUOTE + COMMA + "SYSTIMESTAMP)";

			Statement statement = conn.createStatement();
			statement.execute(registerProjectQuery);
			return "Success";

		} catch (Exception e) {

			return "Failure";
		} finally {
			conn.close();
		}
	}

	/**
	 * @return ArrayList<Feature>
	 */
	@Override
	public ArrayList<Feature> getFeatures(final String userid, final String project) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Feature feature = null;
		ArrayList<Feature> arrFeatures = new ArrayList<Feature>();
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement("select  f.feature_sequence, f.feature_name,u.user_sequence "
					+ "from juniper_pro_u_feat_link l inner join juniper_user_master u on l.user_sequence=u.user_sequence inner join "
					+ "juniper_project_master p on l.project_sequence=p.project_sequence inner join "
					+ "juniper_feature_master f on l.feature_sequence=f.feature_sequence where u.user_id = ? and p.project_id = ? "
					+ "and (f.FEATURE_PRO_ADMIN = 'N' and f.FEATURE_ADMIN = 'N')");
			pstm.setString(1, userid);
			pstm.setString(2, project);
			rs = pstm.executeQuery();
			while (rs.next()) {
				feature = new Feature();
				feature.setFeature_sequence(rs.getInt(1));
				feature.setFeature_name(rs.getString(2));
				feature.setSelected_user_sequence(rs.getInt(3));
				arrFeatures.add(feature);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return arrFeatures;
	}

	/**
	 * @return ArrayList<Feature>
	 */
	@Override
	public ArrayList<Feature> getFeaturesAlready(final String userid, final String project) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Feature feature = null;
		ArrayList<Feature> arrFeatures = new ArrayList<Feature>();
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement(
					"select f.feature_sequence, f.feature_name from juniper_feature_master f left join (select l.feature_sequence from juniper_pro_u_feat_link l inner join juniper_user_master u on l.user_sequence=u.user_sequence inner join juniper_project_master p on l.project_sequence=p.project_sequence where u.user_id=? and p.project_id=?) feat on feat.feature_sequence = f.feature_sequence  where feat.feature_sequence is null and (f.FEATURE_PRO_ADMIN = 'N' AND f.FEATURE_ADMIN = 'N')");

			pstm.setString(1, userid);
			pstm.setString(2, project);
			rs = pstm.executeQuery();
			while (rs.next()) {
				feature = new Feature();
				feature.setFeature_sequence(rs.getInt(1));
				feature.setFeature_name(rs.getString(2));
				arrFeatures.add(feature);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return arrFeatures;
	}

	/**
	 * @return integer
	 */
	@Override
	public int getUserSequence(final String userid) throws Exception {
		int seq = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement("select user_sequence from  juniper_user_master where user_id=?");

			pstm.setString(1, userid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				seq = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return seq;
	}

	/**
	 * @return Null
	 */
	@Override
	public void deleteEntries(final int projectseq, final int selectUser_Seq) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement(
					"delete from juniper_pro_u_feat_link where user_sequence=? and project_sequence=?");
			pstm.setInt(1, selectUser_Seq);
			pstm.setInt(2, projectseq);
			pstm.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
	}

	/**
	 * @return Project
	 */
	public Project getProject(@Valid final String projectId) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		Project project = null;
		ResultSet rs = null;
		try {
			conn = this.ConnectionUtils.getConnection();
			project = new Project();
			String query = "select project_sequence,project_id, project_name,project_owner,project_description,gsd_group,gsd_key from juniper_project_master where project_id = ?";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectId);
			rs = pstm.executeQuery();

			while (rs.next()) {

				project.setProject_sequence(rs.getInt(1));
				project.setProject_id(rs.getString(2));
				project.setProject_name(rs.getString(3));
				project.setProject_owner(rs.getString(4));
				project.setProject_description(rs.getString(5));
				project.setGsd_group(rs.getString(6));
				project.setGsd_key(rs.getString(7));

			}
		} catch (SQLException e) {
			throw e;
		} finally {
			pstm.close();
			conn.close();
		}
		return project;
	}

	/**
	 * @return String
	 */
	@Override
	public String registerAddAdminAccess(final int projectSeq, final int user_sequence) throws Exception {
		Connection conn = null;
		PreparedStatement featurePstm = null;
		PreparedStatement adminPstm = null;
		ResultSet adminRs = null;
		String message = null;
		try {
			conn = this.ConnectionUtils.getConnection();
			String featureQuery = "select feature_sequence from juniper_feature_master order by feature_sequence";
			String adminQuery = "select user_sequence from juniper_user_master where is_admin='Y'";
			featurePstm = conn.prepareStatement(featureQuery);
			adminPstm = conn.prepareStatement(adminQuery);
			adminRs = adminPstm.executeQuery();

			while (adminRs.next()) {
				int adminId = adminRs.getInt(1);
				ResultSet featureRs = featurePstm.executeQuery();
				while (featureRs.next()) {
					int featureId = featureRs.getInt(1);
					String addProject = "INSERT INTO" + SPACE + PROJECT_LINK_TABLE + SPACE
							+ "(user_sequence,project_sequence,feature_sequence)" + "VALUES (" + QUOTE + adminId + QUOTE
							+ COMMA + QUOTE + projectSeq + QUOTE + COMMA + QUOTE + featureId + QUOTE + ")";
					Statement statement = conn.createStatement();
					statement.execute(addProject);
				}
				message = "Success";
			}
		} catch (SQLException e) {
			message = "Faliure";
			throw e;
		} finally {
			featurePstm.close();
			adminPstm.close();
			conn.close();
		}
		return message;
	}

	/**
	 * @return List<String>
	 */
	@Override
	public List<String> getProjects() throws Exception {
		List<String> projects = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement("select project_id  from juniper_project_master");
			rs = pstm.executeQuery();
			while (rs.next()) {
				projects.add(rs.getString(1));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			connection.close();
		}
		return projects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iig.gcp.admin.dao.AdminDAO#fetchProjectDetails(java.lang.String)
	 */
	/**
	 * @return Project
	 */
	@Override
	public Project fetchProjectDetails(@Valid final String projectId) throws Exception {
		Project project = new Project();
		Connection connection = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			String query = "select project_id, project_name,project_owner,project_description,gsd_group,gsd_key from juniper_project_master where project_id = ?";
			pstm = connection.prepareStatement(query);
			pstm.setString(1, projectId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				project.setProject_id(rs.getString(1));
				project.setProject_name(rs.getString(2));
				project.setProject_owner(rs.getString(3));
				project.setProject_description(rs.getString(4));
				project.setGsd_group(rs.getString(5));
				project.setGsd_key(rs.getString(6));
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			connection.close();
		}

		return project;
	}

	/**
	 * @return String
	 */
	@Override
	public String updateProject(@Valid final String projectId, final String projectName, final String projectOwner,
			final String projectDescription, final String gsdGroup, final String gsdKey) throws Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		String message = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			String updateProjectQuery = "update juniper_project_master " + "set project_name =" + QUOTE + projectName
					+ QUOTE + COMMA + "project_owner=" + QUOTE + projectOwner + QUOTE + COMMA + "project_description="
					+ QUOTE + projectDescription + QUOTE + COMMA + "gsd_group=" + QUOTE + gsdGroup + QUOTE + COMMA
					+ "gsd_key=" + QUOTE + gsdKey + QUOTE + " where project_id = ?";
			pstm = connection.prepareStatement(updateProjectQuery);
			pstm.setString(1, projectId);
			pstm.execute();
			message = "Success";
		} catch (Exception e) {
			message = "Failure";
			throw e;
		} finally {
			pstm.close();
			connection.close();
		}
		return message;
	}

	/**
	 * Commenting this code as of now, may be needed in future.
	 */

	/**
	 * @return String
	 */
	@Override
	public String getProAdminFeatures() throws Exception {
		ArrayList<String> arrfeatureAdmin = new ArrayList<String>();
		Connection conn = null;
		ResultSet featureRs = null;
		try {
			conn = this.ConnectionUtils.getConnection();
			String featureQuery = "select feature_sequence from juniper_feature_master where FEATURE_PRO_ADMIN = 'Y' order by feature_sequence";
			PreparedStatement featurePstm = conn.prepareStatement(featureQuery);

			featureRs = featurePstm.executeQuery();
			while (featureRs.next()) {
				arrfeatureAdmin.add(featureRs.getString(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			conn.close();
		}
		return arrfeatureAdmin.toString().replace("[", "").replace("]", "");

	};

	/**
	 * @return String
	 */
	@Override
	public String registerUser(@Valid final String userId, final String domain, final String onboardedUserFullName,
			final String userEmail, final String loggedInUser) throws ClassNotFoundException, SQLException, Exception {
		Connection conn = null;
		String message = null;
		try {
			conn = this.ConnectionUtils.getConnection();
			String registerUserQuery = "INSERT INTO" + SPACE + USER_MASTER_TABLE + SPACE
					+ "(user_id,user_domain,user_fullname,user_email )" + "VALUES (" + QUOTE + userId + QUOTE + COMMA
					+ QUOTE + domain + QUOTE + COMMA + QUOTE + onboardedUserFullName + QUOTE + COMMA + QUOTE + userEmail
					+ QUOTE + ")";
			Statement statement = conn.createStatement();
			statement.execute(registerUserQuery);
			message = "Success";

		} catch (ClassNotFoundException | SQLException e) {
			message = "Failure";
			throw e;
		} finally {
			conn.close();
		}
		return message;
	}

	/**
	 * @return String
	 */
	@Override
	public String updateUser(@Valid final String userId, final String domain, final String onboardedUserFullName,
			final String userEmail, final String loggedInUser) throws ClassNotFoundException, SQLException, Exception {
		Connection connection = null;
		PreparedStatement pstm = null;
		String message = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			String updateUserQuery = "update " + USER_MASTER_TABLE + " set user_domain =" + QUOTE + domain + QUOTE
					+ COMMA + "user_email=" + QUOTE + userEmail + QUOTE + COMMA + "user_fullname=" + QUOTE
					+ onboardedUserFullName + QUOTE + " where user_id = ?";
			pstm = connection.prepareStatement(updateUserQuery);
			pstm.setString(1, userId);
			pstm.execute();
			message = "Success";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Failure";
		} finally {
			pstm.close();
			connection.close();
		}
		return message;
	}

	/**
	 * @return List<String>
	 */
	@Override
	public List<String> fetchUserIds() throws ClassNotFoundException, SQLException, Exception {
		ArrayList<String> userIds = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet userIdsRs = null;
		try {
			conn = this.ConnectionUtils.getConnection();
			String featureQuery = "select user_id from " + USER_MASTER_TABLE + "";
			prepStmt = conn.prepareStatement(featureQuery);
			userIdsRs = prepStmt.executeQuery();
			while (userIdsRs.next()) {
				userIds.add(userIdsRs.getString(1));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			prepStmt.close();
			conn.close();
		}
		return userIds;
	}

	/**
	 * @return UserAccount
	 */
	@Override
	public UserAccount fetchUserAttributes(final String userId) throws ClassNotFoundException, SQLException, Exception {
		UserAccount userAccount = new UserAccount();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet userIdsRs = null;
		try {
			conn = this.ConnectionUtils.getConnection();
			String featureQuery = "select user_id,user_domain,user_fullname,user_email from " + USER_MASTER_TABLE
					+ " where user_id=?";
			prepStmt = conn.prepareStatement(featureQuery);
			prepStmt.setString(1, userId);
			userIdsRs = prepStmt.executeQuery();
			while (userIdsRs.next()) {
				userAccount.setUser_id(userIdsRs.getString(1));
				userAccount.setUser_domain(userIdsRs.getString(2));
				userAccount.setUser_fullname(userIdsRs.getString(3));
				userAccount.setUser_email(userIdsRs.getString(4));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			prepStmt.close();
			conn.close();
		}
		return userAccount;
	}

	/**
	 * @return String
	 */
	@Override
	public String linkUserGroupsToProject(final int userSequence, final Project project)
			throws ClassNotFoundException, SQLException, Exception {
		Connection connection = null;
		PreparedStatement prepStmt = null;
		String message = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			Statement statement = connection.createStatement();

			// Below query copy features from GROUP_MASTER template and insert records
			// USER_GROUP_MASTER.
			String linkUserGroupsToNewProjectQuery = "insert into  " + USER_GROUP_MASTER_TABLE
					+ "(user_group_name,feature_list,project_sequence,created_by)(" + "select CONCAT('"
					+ project.getProject_id() + "',group_name),feature_list" + COMMA + project.getProject_sequence()
					+ COMMA + "(select user_id from " + USER_MASTER_TABLE + " where USER_SEQUENCE='" + userSequence
					+ "') from " + GROUP_MASTER_TABLE + " where group_sequence not in ('1'))";
			statement.execute(linkUserGroupsToNewProjectQuery);

			// Below query add user to UGROUP_USER mapping table.
			String addUserGroupToUserMappingQuery = "insert into  " + UGROUP_GROUP_TABLE
					+ "(user_group_sequence,user_sequence,project_sequence)(" + "select user_group_sequence" + COMMA
					+ QUOTE + userSequence + QUOTE + COMMA + QUOTE + project.getProject_sequence() + QUOTE + " from "
					+ USER_GROUP_MASTER_TABLE + " where user_group_name not in('" + JUNIPER_JADMIN_GROUP
					+ "') and project_sequence='" + project.getProject_sequence() + "')";
			statement.execute(addUserGroupToUserMappingQuery);

			// Below query will add access to all existing JAdmins to existing project.
			List<UserAccount> jadminIds = new ArrayList<UserAccount>();
			String fetchAdminUsers = "select distinct user_sequence from " + UGROUP_GROUP_TABLE
					+ " where user_group_sequence in (select user_group_sequence from " + USER_GROUP_MASTER_TABLE
					+ " where user_group_name in('" + JUNIPER_JADMIN_GROUP + "'))";
			prepStmt = connection.prepareStatement(fetchAdminUsers);
			ResultSet jadminUserIds = prepStmt.executeQuery();
			while (jadminUserIds.next()) {
				UserAccount user = new UserAccount();
				user.setUser_sequence(Integer.parseInt(jadminUserIds.getString(1)));
				jadminIds.add(user);
			}

			for (UserAccount user : jadminIds) {
				String addAccessToAllJadmins = "insert into  " + UGROUP_GROUP_TABLE
						+ "(user_group_sequence,user_sequence,project_sequence)(" + "select user_group_sequence" + COMMA
						+ QUOTE + user.getUser_sequence() + QUOTE + COMMA + QUOTE + project.getProject_sequence()
						+ QUOTE + " from " + USER_GROUP_MASTER_TABLE + " where user_group_name in('"
						+ JUNIPER_JADMIN_GROUP + "'))";
				statement.execute(addAccessToAllJadmins);
			}
			message = "Success";
		} catch (Exception e) {
			message = "Failure";
			throw e;
		} finally {
			prepStmt.close();
			connection.close();
		}
		return message;
	}

	/**
	 * This method check if PSID already exists in database or not.
	 * 
	 * @param: String psid
	 * @return 1 if EIM already exists otherwise returns 0.
	 */
	/**
	 * @return integer
	 */
	@Override
	public int validatePSID(@Valid final String psid) throws Exception {
		Connection connection = null;
		int stat = 0;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			connection = this.ConnectionUtils.getConnection();
			pstm = connection.prepareStatement("select user_id from " + USER_MASTER_TABLE + " where user_id=?");
			pstm.setString(1, psid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				stat = 1;
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {

		} finally {
			connection.close();
			pstm.close();
		}
		return stat;
	}

	/**
	 * @return ArrayList<Project>
	 */
	@Override
	public ArrayList<Project> getProjects1(final String username) throws Exception {

		ArrayList<Project> arrProject = new ArrayList<Project>();
		String sql = "SELECT  DISTINCT p.PROJECT_ID ,p.project_sequence FROM " + UGROUP_GROUP_TABLE + " l inner join "
				+ "juniper_project_master p on l.project_sequence=p.project_sequence where user_sequence in "
				+ "(select user_sequence from " + USER_MASTER_TABLE
				+ " where user_id=?)order by p.project_sequence desc";

		Connection conn = null;
		PreparedStatement pstm = null;

		try {
			conn = this.ConnectionUtils.getConnection();
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, username);
			ResultSet rs = pstm.executeQuery();
			Project prj = null;
			while (rs.next()) {
				prj = new Project();
				prj.setProject_id(rs.getString(1));
				prj.setProject_sequence(rs.getInt(2));
				arrProject.add(prj);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		} finally {
			pstm.close();
			conn.close();
		}
		return arrProject;

	}

	/**
	 * This method fetch list of countries mapped with region from database
	 * 
	 * @param : String - region
	 * @return: List of countries
	 * @throws Exception
	 */
	@Override
	public List<CountryMaster> fetchCountries(@Valid final String region) throws Exception {
		CountryMaster cm = null;
		ArrayList<CountryMaster> countries = new ArrayList<CountryMaster>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = this.ConnectionUtils.getConnection();
			String query = "select country_code,country_name from " + COUNTRY_REGION_MAPPING_TABLE
					+ "  where region_name=? order by country_name ";
			pstm = conn.prepareStatement(query);
			pstm.setString(1, region);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cm = new CountryMaster();
				cm.setCountry_code(rs.getString(1));
				cm.setCountry_name(rs.getString(2));
				countries.add(cm);
			}
		} catch (ClassNotFoundException | SQLException e) {

		} finally {
			pstm.close();
			conn.close();
		}
		return countries;
	}

	/**
	 * This method check if EIM already exists in database or not.
	 * 
	 * @param: String eim
	 * @return 1 if EIM already exists otherwise returns 0.
	 * @throws Exception
	 */
	@Override
	public int checkEIM(@Valid final String system_eim) throws Exception {
		int stat = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = ConnectionUtils.getConnection();
			pstm = conn.prepareStatement("select system_eim from " + SYSTEM_MASTER_TABLE + " where system_eim=?");
			pstm.setString(1, system_eim);
			rs = pstm.executeQuery();
			while (rs.next()) {
				stat = 1;
				break;
			}
		} catch (ClassNotFoundException | SQLException e) {

		} finally {
			pstm.close();
			conn.close();
		}
		return stat;
	}

	/**
	 * This method register system form details and push them in database.
	 * 
	 * @throws Exception
	 */
	/**
	 * @return String
	 */
	@Override
	public String registerSystem(@Valid final String systemEIM, final String systemName, final String region,
			final String country, final String environmentType, final String userName) throws Exception {
		Connection conn = ConnectionUtils.getConnection();
		String registerProjectQuery;
		String message = null;
		try {
			registerProjectQuery = "INSERT INTO" + SPACE + SYSTEM_MASTER_TABLE + SPACE
					+ "(system_eim,system_name,system_region,system_country,environment_type,created_by,created_date)"
					+ "VALUES (" + QUOTE + systemEIM + QUOTE + COMMA + QUOTE + systemName + QUOTE + COMMA + QUOTE
					+ region + QUOTE + COMMA + QUOTE + country + QUOTE + COMMA + QUOTE + environmentType + QUOTE + COMMA
					+ QUOTE + userName + QUOTE + COMMA + "SYSTIMESTAMP" + ")";
			Statement statement = conn.createStatement();
			statement.execute(registerProjectQuery);
			message = "Success";
		} catch (Exception e) {
			message = "Failure";
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return message;
	}
}