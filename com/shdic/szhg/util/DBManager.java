package com.shdic.szhg.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.shdic.szhg.util.DatabaseMessage;

/**
 * ��ݿ������<br>
 * ʹ����ݿ��������ɶ���ݿ�Ĳ��������磺������ݿ⡢��ɾ�Ĳ顢�ͷ���ݿ���Դ��<br>
 * 
 * @author ������Դ�ϿƼ�������޹�˾�Ϻ��ֹ�˾
 * @version 1.0
 * @since 2007��7��2��
 */
public class DBManager {

	public DBManager() {
	}

	/**
	 * DBManager���캯��<br>
	 * ����ͨ��ָ��һ��JNDI������Զ�����һ����Ӧ�÷���������ݿ�����
	 * 
	 * @param jndiname
	 *            JNDI���
	 * @param url
	 *            Ӧ�÷�����JNDI URL
	 * @throws Exception
	 *             Ӧ�÷�����JNDI�����쳣
	 */
	public DBManager(String jndiname, String url) throws Exception {
		this.createConntectionByJndi(jndiname, url);
	}
	
	public DBManager(String jndiname) throws Exception {
//		this.createConntectionByJndi(jndiname, Jndi.URL);
		this.createConntectionByJndi(jndiname );
	}

	/**
	 * ͨ��Ӧ�÷�������JNDI������ݿ�����<br>
	 * ���DBManager�����е�Connection�Ѿ����ڣ��Ѿ���������ݿ����ӣ�<br>
	 * ��ô�÷������᷵���µ���ݿ�����
	 * 
	 * @param url
	 *            Ӧ�÷�����JNDI URL
	 * @param jndiname
	 *            JNDI���
	 * 
	 * @return Connection JNDIָ������ݿ�����
	 * @throws Exception
	 *             �����쳣
	 */
	public void createConntectionByJndi(String jndiname, String url)
			throws Exception {

		
		try {

			Hashtable props = new Hashtable();

			// ����JBOSS�������ĵĻ���
			props.put(Context.INITIAL_CONTEXT_FACTORY,
					//
			//"weblogic.jndi.WLInitialContextFactory");
			"org.jnp.interfaces.NamingContextFactory");

			props.put(Context.PROVIDER_URL, url);

			props.put("java.naming.rmi.security.manager", "yes");

			props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming");

			Context initCtx = new InitialContext(props);

			// ͨ��JNDI��ѯӦ�÷����������õ����Դt3://10.1.1.5:7089
			DataSource ds = (javax.sql.DataSource) initCtx.lookup(jndiname);
			
			
//			Hashtable   ht   =   new   Hashtable();   
//			  ht.put("java.naming.factory.initial",   "weblogic.jndi.WLInitialContextFactory");   
//			  ht.put("java.naming.provider.url",   "t3://localhost:7001");   
//			  ht.put(   "java.naming.security.principal",   "dtgk");   
//			  ht.put(   "java.naming.security.credentials",   "dtgk2007");   
//			  System.out.println(   "before   initialContext"   );   
//			  Context  initCtx   =   new   InitialContext(ht);   
//			  System.out.println(   "before   lookup"   );   
//			  DataSource ds   =   (DataSource)initCtx.lookup("jdbc/msdb");   
//			                    conn   =   ds.getConnection();   
			

			if (ds != null) {
				// ͨ�����Դ�����ݿ�����
				this.conn = ds.getConnection();
			}
		} catch (NamingException ex) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_JNDI, ex);
		} catch (SQLException ex) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_CONNTECTION, ex);
		}
		
	}
	
	
	
	
	public   void createConntectionByJndi(String jndiname  )  
    {
        //��ݿ�����
        Connection m_Connection = null;
        //���Դ
        DataSource m_Datasource = null;
        Context m_Context = null;
        Hashtable m_Hashtable = null; 
        //����JNDI�Ļ�������
        m_Hashtable = new Hashtable();
        //m_Hashtable.put(Context.INITIAL_CONTEXT_FACTORY,
        //                "weblogic.jndi.WLInitialContextFactory");
        //m_Hashtable.put(Context.PROVIDER_URL, "t3://localhost:7001");
        try {
			m_Context = new InitialContext(m_Hashtable);
			 //��JNDI�л�����Դ
	        m_Datasource = (javax.sql.DataSource) m_Context.lookup(
	        		jndiname);
	        //�����ݿ�����
	         
			m_Connection = m_Datasource.getConnection();
			 

			
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
       
        this.conn = m_Connection; 
    }
	
	
	public static Connection getConnction(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url ="jdbc:oracle:thin:@192.168.0.201:1521:orcl";
			String user="crjwgr";
			String password="crjwgr";
			conn =  DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		
		
		return conn;
		
	}
	public static Connection getConnctionByJndi(String jndiName){
		//��ݿ�����
        Connection m_Connection = null;
        //���Դ
        DataSource m_Datasource = null;
        Context m_Context = null;
        Hashtable m_Hashtable = null; 
        //����JNDI�Ļ�������
        m_Hashtable = new Hashtable();
        try {
			m_Context = new InitialContext(m_Hashtable);
			 //��JNDI�л�����Դ
	        m_Datasource = (javax.sql.DataSource) m_Context.lookup(jndiName);
	        //�����ݿ�����
			m_Connection = m_Datasource.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
        return m_Connection; 
	}

	/**
	 * ִ����ݿ��ѯ
	 * 
	 * @param sql
	 *            String ��ѯ���
	 * @return ResultSet ִ����ݿ��ѯ�󷵻صĽ��
	 * @throws Exception
	 *             ��ݿ��ѯ�쳣
	 */
	public ResultSet executeQuery(String sql) throws Exception {
		// �����ݿ����Ӷ���Ϊ�գ���ѯ���޷�����
		if (this.conn == null) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_CONNTECTION);
		}
		ResultSet rs = null;
		try {
			// ִ����ݿ��ѯ
			Statement stmt = conn.createStatement();
			// long start = System.currentTimeMillis();
			// System.out.println("begin executeQuery With: " + sql);
			rs = stmt.executeQuery(sql);
			// System.out.println("end executeQuery With: " + sql);
			// System.out.println("end executeQuery used: " +
			// (System.currentTimeMillis()-start));
		} catch (SQLException ex) {
			// ִ����ݿ��ѯ�쳣
			throw new Exception("ִ�в�ѯ��SQL����ǣ�" + sql
					+ DatabaseMessage.MSG_EXCEPTION_QUERY, ex);
		}
		return rs;
	}
	
	
	
	
	/**
	 * ִ����ݿ���£�������䣩�����磺Insert��Update��Delete��
	 * 
	 * @param sql
	 *            String ��ݿ����SQL���
	 * @throws Exception
	 *             ִ����ݿ�����쳣
	 */
	public void executeUpdate(String sql) throws Exception {
		// �����ݿ����Ӷ���Ϊ�գ����޷�ִ����ݿ����
		if (this.conn == null) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_CONNTECTION);
		}
		Statement stmt = null;
		try {
			// ִ����ݿ����
			stmt = conn.createStatement();
			// System.out.println("begin executeUpdate With: " + sql);
			stmt.executeUpdate(sql);
			// System.out.println("begin executeUpdate With: " + sql);
		} catch (SQLException ex) {
			// ִ����ݿ�����쳣
			throw new Exception("ִ�е�SQL����ǣ�" + sql
					+ DatabaseMessage.MSG_EXCEPTION_QUERY, ex);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}
			stmt = null;
		}
	}

	/**
	 * ִ����ݿ���£�����SQL��䣩�����磺Insert��Update��Delete��<br>
	 * ������SQL�������һ��ִ��ʧ�ܣ���������ع�
	 * 
	 * @param sqls
	 *            String ��ݿ���µĶ���SQL���
	 * @throws Exception
	 *             ִ����ݿ�����쳣
	 */
	public void executeUpdate(String[] sqls) throws Exception {

		// �����ݿ����Ӷ���Ϊ�գ����޷�ִ����ݿ����
		if (this.conn == null) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_CONNTECTION);
		}

		// ��¼��ǰִ�е�SQL���
		String currentsql = null;
		Statement stmt = null; 
		try {

			// Ϊ��ʹ����ݿ����񣬹ر��Զ��ύ
			this.conn.setAutoCommit(false);

			stmt = conn.createStatement();

			for (int i = 0; i < sqls.length; i++) {
				// ִ����ݿ����
				currentsql = sqls[i];
				// System.out.println("begin executeUpdate With: " +
				// currentsql);
				stmt.executeUpdate(currentsql);
				// System.out.println("end executeUpdate With: " + currentsql);
			}
			// ��δ�����쳣��������ύ�������
			this.conn.commit();

		} catch (SQLException ex) {
			// �ع��쳣֮ǰ����ݿ����
			this.conn.rollback();
			throw new Exception("ִ�г����SQL����ǣ�" + currentsql
					+ DatabaseMessage.MSG_EXCEPTION_UPDATE, ex);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
			}
			stmt = null;
			// Ϊ�˲�Ӱ����������ݿ��������Ҫ�����Զ��ύ
			this.conn.setAutoCommit(true);
		}
	}

	/**
	 * ִ����ݿ�洢���
	 * 
	 * @param sql
	 *            String ��ݿ�洢��̵�SQL���
	 * @throws Exception
	 *             ִ����ݿ�洢����쳣
	 */
	public void executeProcedures(String sql) throws Exception {

		// �����ݿ����Ӷ���Ϊ�գ����޷�ִ����ݿ����
		if (this.conn == null) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_CONNTECTION);
		}
		CallableStatement cstmt = null;
		try {
			// ִ����ݿ�洢���
			cstmt = conn.prepareCall(sql);
			cstmt.execute();
		} catch (SQLException ex) {
			// ִ����ݿ�洢����쳣
			throw new Exception("ִ�е�SQL����ǣ�" + sql
					+ DatabaseMessage.MSG_EXCEPTION_PROCEDURES, ex);
		} finally {
			try {
				if (cstmt != null)
					cstmt.close();
			} catch (Exception ex) {
			}
			cstmt = null;
		}
	}

	/**
	 * ִ����ݿ�洢���(��1���ַ����Ż�1���ַ���)
	 * 
	 * @param sql
	 *            String ��ݿ�洢��̵�SQL���
	 * @throws Exception
	 *             ִ����ݿ�洢����쳣
	 */
	public String executeProceduresReturn(String sql, String parameter)
			throws Exception {

		// �����ݿ����Ӷ���Ϊ�գ����޷�ִ����ݿ����
		if (this.conn == null) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_CONNTECTION);
		}
		CallableStatement cstmt = null;
		try {

			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.setString(1, parameter);
			cstmt.execute();
			return (cstmt.getString(2));
		} catch (SQLException ex) {
			// ִ����ݿ�洢����쳣
			throw new Exception("ִ�е�SQL����ǣ�" + sql
					+ DatabaseMessage.MSG_EXCEPTION_PROCEDURES, ex);
		}
	}
	
	/**
	 * ִ����ݿ�洢���(��1���ַ����Ż�1���ַ���)
	 * 
	 * @param sql
	 *            String ��ݿ�洢��̵�SQL���
	 * @throws Exception
	 *             ִ����ݿ�洢����쳣
	 */
	public String executeProceduresReturn(String sql, String parameter1, String parameter2)
			throws Exception {

		// �����ݿ����Ӷ���Ϊ�գ����޷�ִ����ݿ����
		if (this.conn == null) {
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_CONNTECTION);
		}
		CallableStatement cstmt = null;
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.setString(2, parameter1);
			cstmt.setString(3, parameter2);
			cstmt.execute();
			return (cstmt.getString(1));
		} catch (SQLException ex) {
			// ִ����ݿ�洢����쳣
			throw new Exception("ִ�е�SQL����ǣ�" + sql
					+ DatabaseMessage.MSG_EXCEPTION_PROCEDURES, ex);
		}
	}
	
	/**
	 * 
	 * @author: 
	 * Description:�Զ���SQL��ѯ
	 * @param sql
	 * @param jnti
	 * @return
	 *
	 */
	public List run(String sql) {
		System.out.println("��ԃSQL:"+sql);
		List valuess = new ArrayList();
		try {
			ResultSet rs = executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			List columnlist = new ArrayList();
			Map columnmap = new HashMap();
			for (int i = 1; i <= numberOfColumns; i++) {
				String ColumnName = rsmd.getColumnName(i).toLowerCase();
				String ColumnTypeName = rsmd.getColumnTypeName(i).toLowerCase();
				columnmap.put(ColumnName, ColumnTypeName);
				columnlist.add(ColumnName);
			}
			while (rs.next()) {
				Map values = new HashMap();
				for (int i = 0; i < numberOfColumns; i++) {
					String ColumnName = (String) columnlist.get(i);
					String ColumnTypeName = (String) columnmap.get(ColumnName);
					String value = "";
					if (ColumnTypeName.equals("date"))
						value = rs.getTimestamp(ColumnName).toString();
					else
						value = rs.getString(ColumnName);
					if (value == null)
						value = "";
					values.put(ColumnName, value);
				}
				valuess.add(values);
			}
			//release();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valuess;
	}

	/**
	 * �ͷ���ݿ���Դ<br>
	 * Statement��Connection��ResultSet����ݿ���Դ������Ҫ��ʾ�ͷ�<br>
	 * 
	 * @throws Exception
	 *             �ͷ���ݿ���Դ�쳣
	 */
	public void release() throws Exception {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			// �ͷ���ݿ���Դ�쳣
			throw new Exception(DatabaseMessage.MSG_EXCEPTION_RELEASE, ex);
		}
	}

	public void release(ResultSet rs) {
		Statement stmt = null;
		if (rs == null)
			return;
		try {
			stmt = rs.getStatement();
		} catch (Exception ex) {
		}
		try {
			rs.close();
		} catch (Exception ex) {
		}
		try {
			stmt.close();
		} catch (Exception ex) {
		}
	}

	/**
	 * ��ݿ�����
	 */
	public Connection conn = null;

}
