package com.shdic.szhg.init;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 数据库处理类
 * @author zangql
 *
 */
public class DBAdapter {

	/**
	 * 获取连接
	 * 
	 * @param strDataSource
	 * 			jndi名称
	 * @return
	 * 			数据库连接对象
	 *            
	 */
	public static Connection getConnect(String strDataSource) 
	{
		Connection m_Connection = null;
		DataSource m_Datasource = null;
		Context m_Context = null;
		Hashtable m_Hashtable = null;
		try {
			m_Hashtable = new Hashtable();
			m_Context = new InitialContext(m_Hashtable);
			//m_Datasource = (javax.sql.DataSource) m_Context.lookup(strDataSource);was下调用
			m_Datasource = (javax.sql.DataSource) m_Context.lookup("java:comp/env/"+strDataSource);
		} catch (NamingException e) {
			System.out.println("DBAdapter gain the LOOPUP : " + e.getMessage());

		}
		try {
			if (m_Datasource != null)
				m_Connection = m_Datasource.getConnection();
			m_Connection.setAutoCommit(true); 
		} catch (SQLException e) 
		{

			System.out.println("DBAdapter gain the CONNECTION/STATEMENT : "
					+ e.getMessage());

		}

		return m_Connection;
	}

	/**
	 * 关闭数据库记录集对象
	 * 
	 * @param rs
	 *            ResultSet
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 关闭数据库会话
	 * 
	 * @param stmt
	 *            Statement
	 */
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 *            Connection
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

}
