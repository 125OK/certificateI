package com.shdic.szhg.interfacer;

import java.util.Map;

public interface MessageInterface {

	/**
	 * 向message_mobile的添加信息
	 * @param mbMap  页面传递来的数据集合
	 * @return msBoolean 保存的结果
	 */
	public boolean saveMsMobile(Map mbMap)throws Exception;
	/**
	 * 向message_window的添加信息
	 * @param serMap  页面传递来的数据集合
	 * @return map 事务处理的结果
	 */
	public boolean saveMsWindow(Map mbMap)throws Exception;
	/**
	 * 
	 * @param xxid 消息id
	 * @param xxnr 消息内容
	 * @param bz   备注
	 * @param sfcs 是否判断重复
	 * @param jsdw   行政区划单位(接收单位)：查询message_config的条件
	 * @return
	 */
	public boolean saveMessage(String xxid,String xxnr,String bz,boolean sfcs,String jsdw)throws Exception;
	/**
	 * 
	 * @param xxid 消息id
	 * @param xxnr 消息内容
	 * @param bz   备注
	 * @param sfcs 是否判断重复
	 * @return
	 */
	public boolean saveMessage(String xxid,String xxnr,String bz,boolean sfcs)throws Exception;
}
