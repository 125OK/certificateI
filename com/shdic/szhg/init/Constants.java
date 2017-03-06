package com.shdic.szhg.init;
/**
 * 静态变量或常量类
 * @author zangql
 *
 */
public class Constants {
	/**
	 * jndi 数据源名 常量 
	 */
	public final static String DS_SZHG = "DS_SZG";
	/**
	 * jndi 数据源名 常量 
	 */
	public final static String CRJWGR = "CRJWGR";
	/**
	 * jndi 数据源名 常量 
	 */
	public final static String DSXA_CRJPRE_HB = "DSXA_CRJPRE_HB";
	/**
	 * application 缓存代码存放的key值（系统代码表）
	 */
	public final static String CODE = "code";
	/**
	 * application 缓存代码存放的message_window（消息代码表）
	 */
	public final static String MESSAGE = "message";
	/**
	 * application 缓存代码存放的key值（机构信息）
	 */
	public final static String STRUCTURE = "structure";
	/**
	 * session 缓存代码存放的key值（用户信息，包括用户基本信息和权限信息）
	 */
	public final static String USER = "user";
	/**
	 * 需定时清除的服务器端临时文件地址，为服务器上的绝对地址（双斜杠）
	 */
	public static String CHART_PATH = "";
	/**
	 * 需定时清除的服务器端临时文件地址，为服务器上的绝对地址(单斜杠)
	 */
	public static String CHART_PATH_JAVA = "";
	/**
	 * 将“多张表的配置信息”放入缓存中
	 */
	public static String TABLEMESSAGE="tableMessage";
	

}
