package com.shdic.szhg.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import oracle.sql.CLOB;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shdic.szhg.init.Constants;
import com.shdic.szhg.init.DBAdapter;
/**
 * 
 * 项目名称：<br>
 * 类名称：com.shdic.szhg.util.Tools<br>
 * 类描述：工具类<br>

 * 创建人：zangql<br>
 * 创建时间：2010-7-29 下午03:28:43<br>

 * 修改人：<br>
 * 修改时间：<br>
 * 修改备注：<br>
 * @version  v1.0.0.1
 */
@SuppressWarnings("unchecked")
public class Tools {

	public static Logger logger = Logger.getLogger(Tools.class.getName());
	public static final int NUM_FOURTH_TIME=4;
	public static final int NUM_SIXTH_TIME=6;
	public static final int NUM_EIGHTH_TIME=8;
	public static final int NUM_TENTH_TIME=10;
	public static final int NUM_TWELFTH_TIME=12;
	public static final int NUM_FOURTEENTH_TIME=14;
	public static final int NUM_PERCENT=100;

    /**
     * 
     * @Title: cDate 
     * @Description: TODO(这里用一句话描述这个方法的作用) <br>
     * @param @param dateStr
     * @param @return    设定文件 <br>
     * @return Date    返回类型 <br>
     * @throws
     */
	public static Date cDate(String dateStr) {
		SimpleDateFormat d;
		if (dateStr == null || "".equals(dateStr)) {
			return null;
		}
		d = new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date f = null;
		try {
			f = d.parse(dateStr);
			return Date.valueOf(d.format(f));
		} catch (ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 首字符转大写<br>
	 * @param @param colName  要转换的字符串
	 * @return String 
	 * @throws
	 */
	public static String FormatColName(String colName) {
		return colName.substring(0, 1).toUpperCase() + colName.substring(1);
	}
	/**
	 * 
	 * 将一个map中的key全部转换成小写<br>
	 * @param @param map 要转换的map
	 * @param @return  
	 * @return Map 转换好的map
	 * @throws
	 */
	public static Map FormatMap(Map map){
		Set newKeySet = new HashSet();
		newKeySet = map.keySet();
		Iterator iterator = newKeySet.iterator();
		Map newMap = new HashMap();
		while(iterator.hasNext())
		{
			String keyStr = (String)iterator.next();
			newMap.put(keyStr.toLowerCase(),map.get(keyStr) );
		}
		return newMap;
	}
	
	/**
	 * 
	 * 将一个List中的key全部转换成小写 linkedList\\HashMap<br>
	 * @param @param map 要转换的map
	 * @param @return  
	 * @return Map 转换好的map
	 * @throws
	 */
	public static List FormatList(List list){
		List retList = new LinkedList();
		for(int i=0;i<list.size();i++){
			Map map = (HashMap)list.get(i);
			Set newKeySet = new HashSet();
			newKeySet = map.keySet();
			Iterator iterator = newKeySet.iterator();
			Map newMap = new LinkedHashMap();
			while(iterator.hasNext())
			{
				String keyStr = (String)iterator.next();
				newMap.put(keyStr.toLowerCase(),map.get(keyStr) );
			}
			retList.add(newMap);
		}
		return retList;
	}
	
	/**
	 * 根据jdbc方式查询数据
	 * 
	 * @param strsql
	 *            查询sql语句
	 * @return list 返回列表
	 */
	public List find(String strsql) {
		List list = new LinkedList();
		Connection connect = null;
		ResultSet rs = null;
		logger.info("jdbc查询数据");
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			logger.info("Jdbc查询语句：" + strsql);
			rs = connect.createStatement().executeQuery(strsql);
			ResultSetMetaData metadata = rs.getMetaData();
			while (rs.next()) {
				HashMap result = new HashMap();
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					String key = metadata.getColumnName(i);
					result.put(key, rs.getObject(i));
				}
				list.add(result);
			}
			logger.info("Jdbc查询结果：" + list.size());
			rs.close();
			connect.close();
		} catch (Exception e) {
			logger.info("jdbc查询数据出错：" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
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
		return list;
	}
	
	
	/**
	 * 根据jdbc方式查询数据
	 * 
	 * @param strsql
	 *            查询sql语句
	 * @return list 返回列表
	 */
	
	public List<LinkedHashMap<Object,Object>> findLinkedMap(String strsql) {
		List list = new LinkedList();
		Connection connect = null;
		ResultSet rs = null;
		logger.info("jdbc查询数据");
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			logger.info("Jdbc查询语句：" + strsql);
			rs = connect.createStatement().executeQuery(strsql);
			ResultSetMetaData metadata = rs.getMetaData();
			while (rs.next()) {
				LinkedHashMap result = new LinkedHashMap();
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					String key = metadata.getColumnName(i);
					result.put(key, rs.getObject(i));
				}
				list.add(result);
			}
			logger.info("Jdbc查询结果：" + list.size());
			rs.close();
			connect.close();
		} catch (Exception e) {
			logger.info("jdbc查询数据出错：" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
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
		return list;
	}
	
	public int  getCount(String strsql) {
		Connection connect = null;
		ResultSet rs = null;
		int  result = 0;
		logger.info("jdbc查询数据");
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			logger.info("Jdbc查询语句：" + strsql);
			rs = connect.createStatement().executeQuery(strsql);
			ResultSetMetaData metadata = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					String key = metadata.getColumnName(i);
					result = Integer.parseInt(rs.getObject(i).toString());
				}
			}
			rs.close();
			connect.close();
		} catch (Exception e) {
			logger.info("jdbc查询数据出错：" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
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
		return result;
	}
	/**
	 * 根据jdbc方式查询数据
	 * 
	 * @param strsql
	 *            查询sql语句
	 * @return list 返回列表
	 */
	public List find(String strsql,Connection connect) {
		List list = new LinkedList();
		ResultSet rs = null;
		logger.info("jdbc查询数据");
		try {
			logger.info("Jdbc查询语句：" + strsql);
			rs = connect.createStatement().executeQuery(strsql);
			ResultSetMetaData metadata = rs.getMetaData();
			while (rs.next()) {
				HashMap result = new HashMap();
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					String key = metadata.getColumnName(i);
					result.put(key, rs.getObject(i));
				}
				list.add(result);
			}
			logger.info("Jdbc查询结果：" + list.size());
			rs.close();
		} catch (Exception e) {
			logger.info("jdbc查询数据出错：" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 根据jdbc方式更新数据
	 * @param tableName 表名
	 * @updateMap 需要更新的数据
	 * @condtionMap 更新的条件 
	 */
	public boolean update(String tableName,Map updateMap,Map condtionMap) {
		logger.info("更新的tableName=="+tableName);
		logger.info("更新的updateMap=="+updateMap);
		logger.info("更新的condtionMap=="+condtionMap);
		List list = new LinkedList();
		Connection connect = null;
		boolean updateRS = true;
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			String updateSql = "  ";
			Set updateMapkeySet = updateMap.keySet();
			Iterator iterator = updateMapkeySet.iterator();
			String setStr = " ";
			int i=0;
			while(iterator.hasNext())
			{
				String keyStr = (String)iterator.next();
				String valStr = (String)updateMap.get(keyStr);
				if(i==0)
				{
					setStr=" set "+ keyStr+"='"+valStr+"'";
					i++;
				}else
				{
					setStr+=","+keyStr+"='"+valStr+"'";
				}
			}
			Set condtionMapKetySet=condtionMap.keySet();
			Iterator it = condtionMapKetySet.iterator();
			int t=0;
			String whereStr = "";
			while(it.hasNext())
			{
				String keyStr = (String)it.next();
				String valStr = (String)condtionMap.get(keyStr);
				if(t==0)
				{
					if(valStr!=null&&!"".equals(valStr))
					{
						whereStr=" where "+keyStr+"='"+valStr+"'";
					}else
					{
						whereStr=" where "+keyStr+" is null";
					}
					
					t++;
				}else
				{
					if(valStr!=null&&!"".equals(valStr))
					{
						whereStr+=" and "+keyStr+"='"+valStr+"'";
					}else
					{
						whereStr+=" and "+keyStr+" is null ";
					}
					
				}
			}
			updateSql=" update "+tableName+" "+setStr+whereStr;
			logger.info("Jdbc更新的语句：" + updateSql);
			int influenceRow = connect.createStatement().executeUpdate(updateSql);			
			logger.info("Jdbc更新结果影响的语句：" +influenceRow );
			connect.close();
		} catch (Exception e) {
			logger.info("jdbc更新数据出错：" + e.getMessage());
			updateRS = false;
			e.printStackTrace();
		} finally 
			{
				try 
				{
					if (connect != null)
						connect.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
		     }
		return updateRS;
	}
	
	/**
	 * 根据jdbc方式查询数据
	 *  @param dataSourceStr
	 *  	数据源的名称
	 * @param strsql
	 *            查询sql语句
	 * @return list 返回列表
	 */
	public List findByDataSource(String strsql,String dataSourceStr) {
		List list = new LinkedList();
		Connection connect = null;
		ResultSet rs = null;
		logger.info("jdbc查询数据");
		try {
			connect = DBAdapter.getConnect(dataSourceStr);
			logger.info("Jdbc查询语句：" + strsql);
			rs = connect.createStatement().executeQuery(strsql);
			ResultSetMetaData metadata = rs.getMetaData();
			while (rs.next()) {
				HashMap result = new HashMap();
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					String key = metadata.getColumnName(i);
					result.put(key, rs.getObject(i));
				}
				list.add(result);
			}
			logger.info("Jdbc查询结果：" + list.size());
			rs.close();
			connect.close();
		} catch (Exception e) {
			logger.info("jdbc查询数据出错：" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
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
		return list;
	}
	
	public boolean executeUpdate(String sql) {
		Connection connect = null;
		Statement state = null;		
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			logger.info("Jdbc更新语句：" + sql);
			return connect.createStatement().executeUpdate(sql)!=-1;			
		} catch (Exception e) {
			logger.info("jdbc更新数据出错：" + e.getMessage());
			e.printStackTrace();
		}finally
		{
			try
			{
				if (connect != null)
					connect.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 数据库是否顺利执行
	 * @param ArrayList
	 * @return
	 */
	public boolean executeUpdateList(List ArrayList) {
		Connection connect = null;
		Statement state = null;		
		
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			connect.setAutoCommit(false);  
			state =  connect.createStatement();
			for(Object obj:ArrayList){
				logger.info("Jdbc更新语句：" + obj.toString());
				if(!ObjectUtils.isEmptyObj(obj))
				  state.addBatch(obj.toString());
			}
			state.executeBatch();
			connect.commit();
		} catch (Exception e) {
			try {
				connect.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			logger.info("jdbc更新数据出错：" + e.getMessage());
			e.printStackTrace();
			return false;
		}finally
		{
			try
			{
				if (connect != null)
					connect.close();
				if(state!=null)
					state.close();
			} catch (SQLException e)
			{
				try {
					connect.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 封装页面数据为Map
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map orgRequest(HttpServletRequest request) throws Exception {
		Map map = new HashMap();
		Map values = request.getParameterMap();
		Iterator iter = values.keySet().iterator();
		while (iter.hasNext()) {
			String keyStr = (String) iter.next();
			String value = new String(request.getParameter(keyStr).getBytes(
					"8859_1"), "GBK");
			// 把页面所有的值保存到Map中
			map.put(keyStr, value);
		}
		return map;
	}

	/**
	 * 取序列
	 * 
	 * @param sequenceName
	 *            序列名
	 * @return
	 * @throws Exception
	 */
	public static String getSequence(String sequenceName) throws Exception {
		String strSequence = null;
		Connection conn = null;
		/** 序列的定义 */
		Long packageid = null;

		try {
			conn = DBAdapter.getConnect("CRJWGR");
			String sql = "select " + sequenceName + ".nextval from dual";
			PreparedStatement stmt = conn.prepareStatement(sql);
			logger.info("seqSql:" + sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				packageid = new Long(rs.getLong(1));
			}
		} catch (Exception e) {
			logger.error("取序列操作出错", e);
			e.printStackTrace();
			return null;
		} finally {
			DBAdapter.close(conn);
		}
		return strSequence.valueOf(packageid.longValue());
	}
	
	/**
	 * 获取系统菜单的xml文件
	 * @return
	 */
	public String getMenuTree()
	{
		String treeStr = null;
		try {
			//查询一级菜单
			String menu1Sql = "select * from menu t where parentmenu='0' and sfky='1' order by pxsx";
			List menu1List = this.find(menu1Sql);
			if( menu1List!=null && !menu1List.isEmpty())
			{
				treeStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
				treeStr += "<tree>\n";
				for(int i=0;i<menu1List.size();i++)
				{
					//查询二级菜单
					Map m = (HashMap)menu1List.get(i);
					treeStr += "<menu1 hurl=\"#\" name=\""+m.get("NAME")+"\">\n";
					String menu2Sql = "select * from menu t where parentmenu='"+m.get("MENUID")+"'  and sfky='1'  order by pxsx";
					List menu2List = this.find(menu2Sql);
					if( menu2List!=null && !menu2List.isEmpty())
					{
						for(int j=0;j<menu2List.size();j++)
						{
							Map m2 = (HashMap)menu2List.get(j);
							treeStr += "<menu2 hurl=\""+m2.get("HURL")+"\" name=\""+m2.get("NAME")+"\" qxid=\""+m2.get("QXID")+"\"></menu2>\n";
						}
					}
					treeStr += "</menu1>\n";
				}
				treeStr += "</tree>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return treeStr;
	}
	/**
	 * 合并list
	 * @param list1  第一个list
	 * @param list2  第二个list
	 * @param key 	 唯一键值，为null时则整个对象比较
	 * @return  合并后的list
	 */
	public List combList(List list1,List list2,String key)
	{
		List retList = new ArrayList();
		if(list1 == null || list1.isEmpty())
		{
			retList.addAll(list2);
			return retList;
		}
		if(list2 == null || list2.isEmpty())
		{
			retList.addAll(list1);
			return retList;
		}
		logger.info("开始合并list..");
		logger.info("list1:"+list1);
		logger.info("list2:"+list2);
		logger.info("key:"+key);
		
		retList.addAll(list1);		
		Iterator it = list2.iterator();
		while(it.hasNext())
		{
			Map m = (HashMap)it.next();
			if(key!=null && !"".equals(key))
			{
				String value = (String)m.get(key);
				int index = hasListValue(retList,key,value);
				if(index == -1)
					retList.add(m);
				else
				{
					HashMap retMap = (HashMap)retList.get(index);
					Iterator it0 = m.keySet().iterator();
					while(it0.hasNext())
					{
						String mkey = (String)it0.next();
						retMap.put(mkey, m.get(mkey));
					}
				}
			}else
			{
				if(!retList.contains(m))
					retList.add(m);
			}
		}
		logger.info("合并结束，合并结果为:"+retList);
		return retList;
	}

	/**
	 * 转换List：将形如[{sjbh=1001,jhbs=1,count=2},{sjbh=1001,jhbs=2,count=3},{sjbh=1002,jhbs=1,count=4}...]<br>
	 * 转为形如：[{sjbh=1001,1=2,2=3},{{sjbh=1002,1=4}],即同一数据库字段不同状态换为不同的list列<br>
	 * @param @param list 要合并的List
	 * @param @param key 要合并的字段名
	 * @param @param bs 标识
	 * @param @param count 数据
	 * @param @return  
	 * @return List 
	 * @throws
	 */
	public List transList(List list,String key,String bs,String count)
	{
		List retList = new ArrayList();
		if(list == null || list.isEmpty())
		{
			return retList;
		}
	
		Iterator it = list.iterator();
		while(it.hasNext())
		{
			Map m = (HashMap)it.next();
			if(key!=null && !"".equals(key))
			{
				String value = (String)m.get(key);
				int index = hasListValue(retList,key,value);
				if(index == -1)
				{
					m.put(m.get(bs), m.get(count));
					retList.add(m);
				}
				else
				{
					HashMap retMap = (HashMap)retList.get(index);
					Iterator it0 = m.keySet().iterator();
					while(it0.hasNext())
					{
						String mkey = (String)it0.next();
						retMap.put(mkey, m.get(mkey));
						retMap.put(m.get(bs), m.get(count));
					}
				}
			}else
			{
				if(!retList.contains(m))
					retList.add(m);
			}
		}
		logger.info("合并结束，合并结果为:"+retList);
		return retList;
	}
	
	
	public int hasListValue(List list ,String key,String value)
	{
		int retIndex = -1;
		if(list!=null)
		for(int i=0;i<list.size();i++)
		{
			Map m = (HashMap)list.get(i);
			if( m.get(key)!= null && m.get(key).equals(value))
			{
				retIndex = i;
				break;
			}
		}
		return retIndex;
	}
	
	/**
	 * 取得两个数相除的百分比
	 * @param fenZi 除数（分子）
	 * @param fenMu 被除数（分母）
	 * @return 返回百分比
	 * @example Tools.getPercent("1","5") 结果：20%
	 */
	public static String getPercent(String fenZi,String fenMu)
	{
		String resultString="";
		double resultDouble=0;
		
		if (fenZi==null||"".equals(fenZi.trim())||"0.0".equals(fenZi.trim())||"0".equals(fenZi.trim()))
		{
			resultString="0";
		}
		else if (fenMu==null||"".equals(fenMu.trim())||"0.0".equals(fenMu.trim())||"0".equals(fenMu.trim())) 
		{
			resultString="/";
		}
		else if ("0.0".equals(fenMu.trim())&&"0.0".equals(fenZi.trim())&&"0".equals(fenMu.trim())&&"0".equals(fenZi.trim())) 
		{
			resultString="0";
		}		
		else
		{
			double dblfenZi=0;
			double dblfenMu=1;
			if (isNumeric(fenZi)) 
			{
				dblfenZi=Double.parseDouble(fenZi);
			}
			if (isNumeric(fenMu)) 
			{
				dblfenMu=Double.parseDouble(fenMu);
			}
			NumberFormat nFormat = NumberFormat.getIntegerInstance();
			nFormat.setMinimumFractionDigits(2);
			resultDouble=(dblfenZi/dblfenMu)*NUM_PERCENT;
			resultString = nFormat.format(resultDouble)+"%";
		}
		return resultString;
	}
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 * @是数字返回true
	 */
	public static boolean isNumeric(String str)
	{ 
		  for (int i = str.length();--i>=0;)
		  {  
		   if (!Character.isDigit(str.charAt(i)) && !".".equals(str.substring(i, i+1))&&!"-".equals(str.substring(i, i+1)))
		   { 
			   return false; 
		   } 
		  } 
		  return true; 
	} 
	
	public static List getPartialPageList(int firstResult, int maxResults,String tableString) 
	{
		List list = new LinkedList();
		Connection connect = null;
		ResultSet rs = null;
		String sqlString=null;
		sqlString+="select * from (";
			      //--先用一个select把待查sql包围起来，此时rownum已经形成
			       sqlString += "select row_.*, rownum rownum_ from ( select * from "+tableString+" ) row_ ";
			       sqlString += ") where rownum_ >="+firstResult+" and rownum_ <= 50";
		logger.info("jdbc查询数据"+sqlString);
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			rs = connect.createStatement().executeQuery(sqlString);
			ResultSetMetaData metadata = rs.getMetaData();
			while (rs.next()) {
				HashMap result = new HashMap();
				for (int i = 1; i <= metadata.getColumnCount(); i++) {
					String key = metadata.getColumnName(i);
					result.put(key, rs.getObject(i));
				}
				list.add(result);
			}
			logger.info("Jdbc查询结果：" + list.size());
			rs.close();
			connect.close();
		} catch (Exception e) {
			logger.info("jdbc查询数据出错：" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
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
		return list;	
	}
	
	
	 /**  
     * 判断是否是一个中文汉字  
     *   
     * @param c  
     *            字符  
     * @return true表示是中文汉字，false表示是英文字母  
     * @throws UnsupportedEncodingException  
     *             使用了JAVA不支持的编码格式  
     */  
    public static boolean isChineseChar(char c) throws UnsupportedEncodingException 
    {   
        // 如果字节数大于1，是汉字   
        // 以这种方式区别英文字母和中文汉字并不是十分严谨，但在这个题目中，这样判断已经足够了   
        return String.valueOf(c).getBytes("GB2312").length > 1;   
    }   
	/**  
     * 按字节截取字符串  
     *   
     * @param orignal  
     *            原始字符串  
     * @param count  
     *            截取位数  
     * @return 截取后的字符串  
     * @throws UnsupportedEncodingException  
     *             使用了JAVA不支持的编码格式  
     */  
    public static String getSubString(String orignal, int count) throws UnsupportedEncodingException 
    {   
        // 原始字符不为null，也不是空字符串   
        if (orignal != null && !"".equals(orignal)) 
        {   
            // 将原始字符串转换为GBK编码格式   
            orignal = new String(orignal.getBytes(), "GB2312");   
            // 要截取的字节数大于0，且小于原始字符串的字节数   
            if (count > 0 && count < orignal.getBytes("GB2312").length) 
            {   
                StringBuffer buff = new StringBuffer();   
                char c;   
                for (int i = 0; i < count; i++) 
                {   
                    // charAt(int index)也是按照字符来分解字符串的   
                    c = orignal.charAt(i);   
                    buff.append(c);   
                    if (isChineseChar(c)) 
                    {   
                        // 遇到中文汉字，截取字节总数减1   
                        --count;   
                    }   
                }   
                return buff.toString();   
            }
        }   
        return orignal;   
    }   
	
	/**
	 * 
	 * 拼接sql语句<br>
	 * @param @param tableName  查询的表名 
	 * @param @param whereMap   查询条件Map
	 * @param @param orderStr   排序字段 age desc
	 * @param @param  rqq 日期起: bksj,20080202
	 * @param @param  rqz 日起止  bksj,20080302
	 * @param @return  
	 * @return retString 返回的sql语句
	 * @throws
	 */
	public String linkSql(String tableName,Map whereMap,String orderStr,String rqq,String rqz)
	{
		logger.info("tableName="+tableName);
		logger.info("whereMap=="+whereMap);
		logger.info("orderStr=="+orderStr);
		logger.info("rqq=="+rqq);
		logger.info("rqz=="+rqz);
		String sqlString = "";
		try{
			
			if(tableName!=null&&!"".equals(tableName))
			{
				int t = 0;
				sqlString = "select *  from "+tableName;
				String whereString = "";
				if(!whereMap.isEmpty())
				{
					Set kset = whereMap.keySet();
					Iterator iterator = kset.iterator();
					
					while(iterator.hasNext())
					{
						String keyString  = (String)iterator.next();
						String valString  = (String)whereMap.get(keyString);
						if(valString!=null&&!"".equals(valString))
						{
							if(t==0)
							{
								logger.info("进入t==0");
								whereString+=" where "+keyString+"= '"+valString+"' ";
							}else
							{
								logger.info("进入t!=0");
								whereString+= " and "+keyString+"='"+valString+"'";
							}
							t++;
						}
					}
				}
				if(orderStr!=null&&!"".equals(orderStr))
					orderStr=" order by "+orderStr;
				if(rqq!=null&&!"".equals(rqq))
				{
					String rqqStrs[] = rqq.split(",");
					if(t==0)
					{
						whereString+=" where "+rqqStrs[0].toString()+">='"+rqqStrs[1]+"'";
					}else
					{
						whereString+=" and "+rqqStrs[0].toString()+">='"+rqqStrs[1]+"'";
					}
					t++;
				}
				if(rqz!=null&&!"".equals(rqz))
				{
					String rqzStrs[] = rqz.split(",");
					if(t==0)
					{
						whereString+=" where "+rqzStrs[0].toString()+"<='"+rqzStrs[1]+"'";
					}else
					{
						whereString+=" and "+rqzStrs[0].toString()+"<='"+rqzStrs[1]+"'";
					}
				}
				sqlString+=whereString+orderStr;
			}
			else
			{
				logger.info("最基本的tableName没有输入");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("拼接好的字符串sql=="+sqlString);
		return sqlString;
	}
	
	
	/**
	 * 
	 * 拼接sql语句<br>
	 * @param @param tableName  查询的表名 
	 * @param @param whereMap   查询条件Map
	 * @param @param orderStr   排序字段 age desc
	 * @param @param  rqq 日期起: bksj,20080202
	 * @param @param  rqz 日起止  bksj,20080302
	 * @param @return  
	 * @return retString 返回的sql语句
	 * @throws
	 */
	public String linkSql(String tableName,Map whereMap,String orderStr,String rqq,String rqz,Map inWhereMap)
	{
		logger.info("tableName="+tableName);
		logger.info("whereMap=="+whereMap);
		logger.info("orderStr=="+orderStr);
		logger.info("rqq=="+rqq);
		logger.info("rqz=="+rqz);
		String sqlString = "";
		try{
			
			if(tableName!=null&&!"".equals(tableName))
			{
				int t = 0;
				sqlString = "select *  from "+tableName+" where 1=1 ";
				String whereString = "";
				if(!whereMap.isEmpty())
				{
					Set kset = whereMap.keySet();
					Iterator iterator = kset.iterator();
					
					while(iterator.hasNext())
					{
						String keyString  = (String)iterator.next();
						String valString  = (String)whereMap.get(keyString);
						if(valString!=null&&!"".equals(valString))
						{

							whereString+= " and "+keyString+"='"+valString+"'";
						
						}
					}
				}
				
				if(!inWhereMap.isEmpty())
				{
					Set kset = inWhereMap.keySet();
					Iterator iterator = kset.iterator();
					
					while(iterator.hasNext())
					{
						String keyString  = (String)iterator.next();
						String valString  = (String)whereMap.get(keyString);
						if(valString!=null&&!"".equals(valString))
						{

							whereString+= " and "+keyString+" in ('"+valString+"') ";
						}
					}
				}
				
				if(orderStr!=null&&!"".equals(orderStr))
					orderStr=" order by "+orderStr;
				if(rqq!=null&&!"".equals(rqq))
				{
					String rqqStrs[] = rqq.split(",");
					whereString+=" and "+rqqStrs[0].toString()+">='"+rqqStrs[1]+"'";
				
				}
				if(rqz!=null&&!"".equals(rqz))
				{
					String rqzStrs[] = rqz.split(",");
					whereString+=" and "+rqzStrs[0].toString()+"<='"+rqzStrs[1]+"'";
				}
				sqlString+=whereString+orderStr;
			}
			else
			{
				logger.info("最基本的tableName没有输入");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("拼接好的字符串sql=="+sqlString);
		return sqlString;
	}
	
	/**
	 * 将字符串封装成insert的信息
	 * @param json  {name:wwx,age:10}
	 * @return   (name,age) values('wwx','10')
	 */
	public static String linkInsertCon(String json){
		try{
			GsonBuilder gb = new GsonBuilder();
			Gson g = gb.create();
			Map<String, String> map = g.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
			Iterator it =map.keySet().iterator();
			String key = "";
			String keyStr="(";
			String valStr="values(";
			while(it.hasNext()){
				key =it.next().toString();
				keyStr+=key+",";
				valStr+="'"+map.get(key)+"',";
			}
			return keyStr.substring(0, keyStr.length()-1)+") "+valStr.substring(0, valStr.length()-1)+") ";
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("linkInsertCon请检查字符串"+e.getMessage());
		}
		return "";
	}
	
	public static String linkUpdateCon(Map<String,String> updateMap,String []whereCon){
		StringBuffer retSql =new StringBuffer();
		String wheresql = "";
		String retStr  ="";
		try {
			Iterator<String> it  =updateMap.keySet().iterator();
			String key = "";
			boolean isExit = false;
			retSql.append(" set ");
			
			while(it.hasNext()){
				isExit = false;
				key = it.next();
				for(int i=0;i<whereCon.length;i++){
					if(key.equals(whereCon[i])){
						isExit=true;
						break;
					}
				}
				if(!isExit){
					retSql.append(key+"='"+updateMap.get(key)+"',");
				}
			}
			retStr  = retSql.toString();
			retStr = retStr.substring(0, retStr.length()-1);
			wheresql=" where ";
			for(int i=0;i<whereCon.length;i++){
				wheresql+=" and "+whereCon[i]+"='"+updateMap.get(whereCon[i])+"'";
			}
			wheresql = wheresql.replaceFirst("and ", "");
			
		} catch (Exception e) {
			e.printStackTrace();
			retSql.setLength(0);
		}
		
		return retStr.toString()+wheresql;
	}
	
	/**
	 * 
	 * 对List中的Map对象的数字列进行排序<br>
	 * @param @param sortList 需要排序的list
	 * @param @param sortStr  排序的字段
	 * @param @param orderStr  
	 * @return void 
	 * @throws
	 */
	public static void sortList(List sortList,String sortStr,String orderStr){
		ContentComparator comparator = new ContentComparator(sortStr,orderStr);
		Collections.sort(sortList, comparator);		
	}
	
	/**
	 * 将数据库中为14：20101008121015位或8：20101208位的日期，转为标准日期格式的字符串14位：2010-10-08 12:10:15和8位：2010-12-08<br>
	 * @param @param timeStr 要转换的从数据库中取出的日期字符串
	 * @param @param timeLong 时间日期的长度，为14或8
	 * @param @return  
	 * @return String 
	 * @throws
	 */
	public static String formatDateString(String timeStr,String timeLong)
	{
		String formatedDate="";
		if (timeStr!=null&&!"".equals(timeStr)) 
		{
			if (timeLong!=null&&timeLong.equals("14")&&timeStr.length()==NUM_FOURTEENTH_TIME)
			{
				formatedDate=timeStr.substring(0,NUM_FOURTH_TIME)+"-"+timeStr.substring(NUM_FOURTH_TIME,NUM_SIXTH_TIME)+"-"+timeStr.substring(NUM_SIXTH_TIME,NUM_EIGHTH_TIME)+" "+timeStr.substring(NUM_EIGHTH_TIME,NUM_TENTH_TIME)+":"+timeStr.substring(NUM_TENTH_TIME,NUM_TWELFTH_TIME)+":"+timeStr.substring(NUM_TWELFTH_TIME,NUM_FOURTEENTH_TIME);
			}
			else if (timeLong!=null&&timeLong.equals("8")&&timeStr.length()==NUM_EIGHTH_TIME) 
			{
				formatedDate=timeStr.substring(0,NUM_FOURTH_TIME)+"-"+timeStr.substring(NUM_FOURTH_TIME,NUM_SIXTH_TIME)+"-"+timeStr.substring(NUM_SIXTH_TIME,NUM_EIGHTH_TIME);
			}
			else
			{
				formatedDate=timeStr;
			}
		}
		else
		{
			formatedDate=timeStr;
		}
		
		return formatedDate;
	}
	
	
	/**
	 * 
	 * 对list中的字段进行按需整合<br>
	 * @param list 需要整合的List
	 * @param @param key 整合的标准
	 * @param @param bs 整合的属性列
	 * @param @param count 整合的数量列
	 * @param @return  
	 * @return List 结果集合
	 * @throws
	 */
	public List confirmList(List list,String key,String bs,String count)
	{
		List returnList = new ArrayList();//返回页面的结果集合
		Map monitorMap = new HashMap();//用于监控的map
		Map storageMap = new HashMap();//用于存放进List的map
		for(int i =0;i<list.size();i++)
		{
			Map map = (HashMap)list.get(i);
			//logger.info("当前的Map---"+map);	
			String rckbh = "";
			if(map.get(key) instanceof Number){
				rckbh = String.valueOf(map.get(key));
			}else
			{
				rckbh = (String)map.get(key);
			}
			String attributeStr = "";//证件使用状态
			String countStr = "";//数量
			if(map.get(bs) instanceof Number)
			{
				attributeStr = String.valueOf(map.get(bs));
			}else
			{
				attributeStr = (String)map.get(bs);
			}
			
			if(map.get(count) instanceof Number)
			{
				countStr = String.valueOf(map.get(count));
			}else
			{
				countStr = (String)map.get(count);
			}
			
			//刚进来的时候
			if(monitorMap.isEmpty())
			{
				monitorMap.put(key,rckbh);
				map.put(attributeStr,countStr);
				map.remove(bs);
				map.remove(count);						
				storageMap.putAll(map);//将该对象中的所有数据copy到用于存放的storageMap对象中
				//logger.info("处理完！！！！！！！！！！！！！"+storageMap);					
			}else {	
				//再次捡来，用于监控的monitorMap不为空					
				//rckbh不相等
				if(rckbh.equals((String)monitorMap.get(key))==false)
				{
					//logger.info("马上变换---"+storageMap);
					//logger.info("原来的数据--"+returnList);
					returnList.add(storageMap);
					//logger.info("封装完一个数据=="+returnList);														
					monitorMap.put(key,rckbh);
					map.put(attributeStr,countStr);
					map.remove(bs);
					map.remove(count);	
					//logger.info("重新封装的map::::::"+map);
					//logger.info("新对象--"+map);
					storageMap=new HashMap();//先清空数据
					storageMap.putAll(map);
					//logger.info("处理完---------"+storageMap);
				}else {
					//rckbh相等，对原有的List中的数据进行修改
					storageMap.put(attributeStr,countStr);
					//logger.info("处理完++++++++"+storageMap);
				}
			}
		}
		//添加最后一个
		returnList.add(storageMap);
		return returnList;
	}
	
	/**
	 * Ping一台主机，看是否能够连通.<br>
	 * @param @param host
	 * @param @param timer Ping的时间间隔，单位毫秒
	 * @param @return  
	 * @return boolean Ping通返回True，Ping不通返回false
	 * @throws Exception
	 */
	public boolean ping(String host,int timer)
	{
	boolean ret = false;
	try 
	{
            InetAddress address = InetAddress.getByName(host);
            ret = address.isReachable(timer);
        } 
	catch (UnknownHostException e) 
	{
            e.printStackTrace();
        } 
	catch (IOException e) 
	{
            e.printStackTrace();
        }
        return ret;
	}
	/**
	 * 
	 * 判断指定时间与前时间的间隔（当前时间-指定时间>间隔时间，返回ture）<br>
	 * @param @param pastDateTimeString  指定的时间
	 * @param @param gapTime 指定的间隔时间
	 * @param @return  
	 * @return boolean  
	 * @throws
	 */
	public boolean getGapTime(String pastDateTimeString,String gapTime){
		boolean result = true;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String currentDateStr = DateUtil.current();
			java.util.Date currentDate = simpleDateFormat.parse(currentDateStr);
			java.util.Date pastDate = simpleDateFormat.parse(pastDateTimeString);
			long getGapTime = (currentDate.getTime()-pastDate.getTime())/(60*1000);//间隔分钟
			int gapIntTime =Integer.parseInt(String.valueOf(getGapTime));
			if(gapIntTime<Integer.parseInt(gapTime)){
				result = false;
			}
		} catch (Exception e) {
			logger.error("时间差出错，出错信息如下："+e.getMessage());
		}
		return result;
	}
	
	public static void main(String[] args) {
		Tools tools = new Tools();
		
		System.out.println(tools.getCurrentTime(10));
	}
	
	public static Map<String,String> jsonToMap(String json){
		 GsonBuilder gb = new GsonBuilder();
	     Gson g = gb.create();
	     System.out.println("jsonToMap:::"+json);
	     Map<String, String> map = g.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
	     return map;
	}
	
	
	public static String getCurrentTime(int daynum){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DAY_OF_MONTH, daynum);
		String current = sdf.format(cd.getTime());
		System.out.println(current);
		return current;
	}
	
	
	public static final String TYPE_STRING="value"; // 查询返回单个值
	public static final String TYPE_MAP="obj";// 查询返回map
	public static final String TYPE_LIST="list"; // 查询返回list
	public static final String TYPE_UPDATE="update"; // 查询返回list
	public static final String TYPE_ERROR="ERROR"; // jdbc 操作出错
	public  static Object execJdbc(String type, String sql, String... param) throws Exception {
		return execJdbcZx( Constants.DSXA_CRJPRE_HB, type,  sql, param);
	}
	public static Object execJdbc(String source,String type, String sql, String... param) throws Exception {
		return execJdbcZx( source, type,  sql, param);
	}
	
	public static Object execJdbcZx(String source,String type, String sql, String... param)throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Object returnObj = null;
		String name = "";
		String value = "";
		try {
			System.out.println("type:" + type);
			System.out.println("sql:" + sql);
			System.out.println("param:" + JSON.toJSONString(param));
			conn = DBAdapter.getConnect(source);
			ps = conn.prepareStatement(sql.toString());
			if(param!=null){
				for (int i = 0, zt = param.length; i < zt; i++) {
					ps.setString(i + 1, param[i]);
				}
			}
			
			
			if(!TYPE_UPDATE.equals(type)){
				
			
				rs = ps.executeQuery();
				ResultSetMetaData md = rs.getMetaData();
				int colunmnCount = md.getColumnCount() + 1;
				// 获取查询 出只有一个数据一个字段的值 比如 count(1) con
				if (TYPE_STRING.equals(type)) {
					if (rs.next()) {
						returnObj = rs.getString(md.getColumnName(1));
					} else {
						returnObj = "";
					}
					System.out.println("execJdbc return :" + returnObj);
					// 获取查询 出只有一个数据
				} else if (TYPE_MAP.equals(type)) {
					Map map = null;
					if (rs.next()) {
						map = new HashMap();
						for (int i = 1; i < colunmnCount; i++) {
							name = md.getColumnName(i);
							value = rs.getString(name);
							map.put(name.toLowerCase(), value);
						}
					}
					if (map != null) {
						returnObj = map;
					}
					System.out.println("execJdbc return :" + JSON.toJSONString(returnObj));
					// 获取查询 出多条数据
				} else if (TYPE_LIST.equals(type)) {
					List list = new ArrayList();
					while (rs.next()) {
						Map map = new HashMap();
						for (int i = 1; i < colunmnCount; i++) {
							name = md.getColumnName(i);
							value = rs.getString(name);
							map.put(name.toLowerCase(), value);
						}
						list.add(map);
					}
					if (list.size() > 0) {
						returnObj = list;
					}
				
				
				}
			}else{
				System.out.println("执行修改操作！");
				returnObj=ps.executeUpdate();
			}
			System.out.println("execJdbcc --"+type+"-- return :" + JSON.toJSONString(returnObj));

		} catch (Exception e) {
			returnObj = TYPE_ERROR;
			e.printStackTrace();
			throw e;

		} finally {
			DBAdapter.close(rs);
			DBAdapter.close(ps);
			DBAdapter.close(conn);

		}

		return returnObj;
	}
	
//	/**
//	 * Base64码转换
//	 * 
//	 * 
//	 */
//	public String byteArrayToString(byte[] b) {
//		String str = "";
//		try {
//			str = Base64.encode(b);
//		} catch (Exception e) {
//			System.out.println("Base64码转换................");
//			e.printStackTrace();
//		}
//		return str;
//	}
	
	/**
	 * 将数据集转换成JSON字符串
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static String getResultSetToJsonString(ResultSet rs) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map rowData = new HashMap();
		if (!rs.next()) {

			rowData.put("flag", "0");
			rowData.put("errorMsg", "未查询到数据！");
			list.add(rowData);
		} else {
			ResultSetMetaData md = rs.getMetaData();
			int colunmnCount = md.getColumnCount();
			String key = "";
			String value = "";
			do {
				rowData = new HashMap(colunmnCount);
				for (int i = 1; i <= colunmnCount; i++) {
					key = md.getColumnName(i).toLowerCase();
					if (key.contains("clob_")) {

						Clob clobValue = rs.getClob(key);
						key = key.substring(5);
						if (clobValue != null) {
							value = clobValue.getSubString(1, (int) clobValue
									.length());
							
							//value=value.replaceAll("\"", "'").trim();
							logger.info("=======clobValue=" + value);
						} else {
							value = "";
						}
					} else {
						value = rs.getString(key)==null?"":rs.getString(key).trim();;
					}
					rowData.put(key, value);
				}
				list.add(rowData);
			} while (rs.next());
		}
		
		return JSON.toJSONString(list);
	}
	
	public static void insClob(Connection conn,String str ,String sql)throws Exception {
		conn.setAutoCommit(false);
		PreparedStatement ps = conn.prepareStatement(sql);
		Writer outStream = null;
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			CLOB clob = (CLOB) rs.getClob("XZNR");
			outStream = clob.getCharacterOutputStream();
			char[] c = str.toCharArray();
			outStream.write(c, 0, c.length);
		}
		outStream.flush();
		outStream.close();
		rs.close();
		conn.commit();
		ps.close();
	}
}
    
    
    
    
