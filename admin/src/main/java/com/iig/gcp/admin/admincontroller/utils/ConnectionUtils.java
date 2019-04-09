package com.iig.gcp.admin.admincontroller.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ConnectionUtils {

	@Autowired
	private DataSource dataSource;

	/**
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException, Exception {

		return dataSource.getConnection();
	}

	/**
	 * @param conn
	 */
	public static void rollbackQuietly(final Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
		}
	}

	/**
	 * @param rs
	 */
	public static void closeResultSet(final ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
		}
	}

	/**
	 * @param ps
	 */
	public static void closePrepareStatement(final PreparedStatement ps) {
		try {
			ps.close();
		} catch (Exception e) {
		}
	}
}
