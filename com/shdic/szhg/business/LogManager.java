package com.shdic.szhg.business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.shdic.szhg.init.Constants;
import com.shdic.szhg.init.DBAdapter;
import com.shdic.szhg.init.InitialConfig;
import com.shdic.szhg.util.Tools;
/**
 * 系统日志处理类，保存表oper_log
 * @author zangql
 *
 */
public class LogManager {

	private static Logger logger = Logger.getLogger(LogManager.class.getName());

	/**
	 * 保存用户操作日志信息.
	 * @param operLog 
	 * 				操作日志对象，包括:{czlx=(0=登录，1=注销，2=下载，S=查询，I=新增，U=更新，D=删除),czsm=,czr=,czrxm=,czmk=,bmbh=,cldw=}
	 * @return 
	 * 				true-成功；false-失败
	 */
	public boolean info(Map operLog) {

		boolean flag = false;
//		// 获取session
//		Session session = null;
//		Transaction tr = null;
//		try {
//			if (operLog != null) {
//				session = InitialConfig.sessionFactory.openSession();
//				// 事务开始
//				tr = session.beginTransaction();
//				BaseDao baseDao = new BaseDao();
//				Tools tools = new Tools();
//				String sequence = null;
//
//				// 取序列 获取id
//				sequence = tools.getSequence("SEQ_OPER_LOG");
//				
//				String czsj = DateUtil.getCurrentDateFormat("yyyyMMddHHmmss");
//				operLog.put("id", sequence);
//				operLog.put("czsj", czsj);
//				logger.info("开始保存操作日志信息：" + operLog);
//				baseDao.save(session, "com.shdic.szhg.hibernate.OperLog",
//						operLog);// 保存配置的信息
//				// 事务提交
//				tr.commit();
//				flag = true;
//			}
//		} catch (Exception e) {
//			logger.info("插入日志异常！！！！！！！！！！");
//			if(tr!=null)
//				tr.rollback();
//			flag = false;
//			e.printStackTrace();
//		} finally {
//			if (session != null)
//				session.close();
//		}
		//zangql update 20110309
		Connection connect = null;
		Statement stmt = null;
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			Tools tools = new Tools();
			String sequence = null;
			String savesql = "insert into oper_log(id,czlx,czsm,czsj,czr,czrxm,czmk,bmbh,cldw) values(SEQ_OPER_LOG.Nextval,'"
				+operLog.get("czlx") + "','"+operLog.get("czsm")+"',to_char(sysdate,'yyyymmddHH24miss'),'"+operLog.get("czr")+"','"+operLog.get("czrxm")+"','"
				+operLog.get("czmk")+"','"+operLog.get("bmbh")+"','"+operLog.get("cldw")+"')";
			logger.info("保存操作日志信息:"+savesql);
			stmt = connect.createStatement();
            stmt.executeUpdate(savesql);
            connect.commit();	            
			connect.close();
			flag = true;
		} catch (Exception e) {
			logger.info("保存操作日志信息出错：" + e.getMessage());
			flag = false;
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (connect != null)
					connect.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//zangql update
		return flag;
	}
	
	/**
	 * 保存用户操作日志信息
	 * @param operLog 
	 * 				操作日志对象。包括:{czlx=(0=登录，1=注销，2=下载，S=查询，I=新增，U=更新，D=删除),czsm=,czmk=}
	 * @param request
	 * 				请求对象。
	 * @return 
	 * 				true-成功；false-失败
	 */
	public boolean info0(Map operLog,HttpServletRequest request) 
	{
		logger.info("Log_operLog:"+operLog);
		logger.info("Log_request:"+request);
		
		Map user = (HashMap)request.getSession().getAttribute(Constants.USER);
		if (user==null)
		{
			return false;
		}
		logger.info("Log_user:"+user);
		return this.info1(user,operLog);
	}
	
	/**
	 * 保存用户操作日志信息
	 * @param user
	 * 				用户对象。包括：{czr='',bmbh='',cldw='',czrxm=''}
	 * @param operLog 
	 * 				操作日志对象。包括:{czlx=(0=登录，1=注销，2=下载，S=查询，I=新增，U=更新，D=删除),czsm=,czmk=}
	 * @return 
	 * 				true-成功；false-失败
	 */
	public boolean info1(Map user,Map operLog) {
		operLog.put("czr",user.get("yhid"));
		operLog.put("bmbh",user.get("bmbh"));
		operLog.put("cldw",user.get("jgdm"));
		operLog.put("czrxm",user.get("xm"));
		return this.info(operLog);
	}
	
	/**
	 * 保存用户操作错误日志信息
	 * @param operLog 操作日志对象，包括:{czlx=(0=登录，1=注销，2=下载，S=查询，I=新增，U=更新，D=删除),czsm=,czr=,czrxm=,czmk=,bmbh=,cldw=}
	 * @return true-成功；false-失败
	 */
	public boolean error(Map operLog) {

		logger.info("页面获取的日志数据:   " + operLog);
		boolean flag = false;
		// 获取session
		Session session = null;
		Transaction tr = null;
		try {
			if (operLog != null) {
				session = InitialConfig.sessionFactory.openSession();
				// 事务开始
				tr = session.beginTransaction();
				BaseDao baseDao = new BaseDao();
				Tools tools = new Tools();
				String sequence = null;

				// 取序列 获取id
				sequence = tools.getSequence("SEQ_OPER_LOG");
				operLog.put("id", sequence);
				String clsj = "20100701111111";
				logger.info("开始保存操作日志信息：" + operLog);
				baseDao.save(session, "com.shdic.szhg.hibernate.OperLog",
						operLog);// 保存配置的信息
			}
			// 事务提交
			tr.commit();
			flag = true;
		} catch (Exception e) {
			tr.rollback();
			flag = false;
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return flag;
	}
}
