package com.shdic.szhg.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

/**
 * 
 * Description:将list转化成有结构形态的json字符串 NodeUtil.java Create on 2013-7-10
 * 下午11:14:22
 * 
 * @author wangwx
 * @version 1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 */
public class NodeUtil {
	private static List resultList = new ArrayList();
	/**
	 * ID:记录自身的ID
	 */
	private static String id;

	/**
	 * pid:记录自身的父ID
	 */
	private static String pid;

	public static List getResultList() {
		return resultList;
	}

	public static void setResultList(List resultList) {
		NodeUtil.resultList = resultList;
	}

	public static String getId() {
		return id;
	}

	public static void setId(String id) {
		NodeUtil.id = id;
	}

	public static String getPid() {
		return pid;
	}

	public static void setPid(String pid) {
		NodeUtil.pid = pid;
	}

	/**
	 * 将list转化成json字符串
	 * 
	 * @return
	 * @throws Exception 
	 */
	public  String listToNodesJson() throws Exception {
		if(ObjectUtils.isEmptyObj(id)){
			System.out.println("NodeUtil中id不可为空，必须setID");
			throw new Exception("NodeUtil中id不可为空，必须setID");
		}
		
		if(ObjectUtils.isEmptyObj(pid)){
			System.out.println("NodeUtil中pid不可为空，必须setPid");
			throw new Exception("NodeUtil中pid不可为空，必须setPid");
		}
		
		if(ObjectUtils.isEmptyObj(resultList)){
			System.out.println("NodeUtil中ResultList不可为空，必须setResultList");
			throw new Exception("NodeUtil中ResultList不可为空，必须setResultList");
		}
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (ObjectUtils.isEmptyObj(resultList)) {
			return "[]";
		} else {
			for (int i=0;i<resultList.size();i++) {
				Map aMap = (Map<?, ?>) resultList.get(i);

				String guid = (String) aMap.get(id);
				String fzbmc = (String) aMap.get(pid);
				if (ObjectUtils.isEmptyObj(fzbmc)) {
					json.append("{");
					for (Object key : aMap.keySet()) {
						json.append("'" + key + "'");
						json.append(":");
						json.append("'" + ObjectUtils.getStringValue(aMap.get(key)) + "'");
						json.append(",");
					}
					json.append("childs:[");
					String str = getChildNodesJson(guid);
					json.append(str);
					json.append("]}");
					if(i!=resultList.size()-1){
						json.append(",");
					}
					// json.setCharAt(json.length() - 1, '}');

				}
			}
			json.append("]");
		}
		
		//字符串格式化，去除非必须字符
		JSONArray dataArray = new JSONArray();
		dataArray = dataArray.fromString(json.toString());
		return  dataArray.toString();
	}

	/**
	 * 获取子节点
	 * 
	 * @param guid
	 * @return
	 */
	public static String getChildNodesJson(String guid) {
		StringBuilder json = new StringBuilder();
		for (Object obj : resultList) {
			Map aMap = (HashMap) obj;
			String tep_id = (String) aMap.get(id);
			String tep_pid = (String) aMap.get(pid);
			if (!ObjectUtils.isEmptyObj(tep_pid)&&tep_pid.equals(guid)) {
				json.append("{");
				for (Object key : aMap.keySet()) {
					json.append("'" + key + "'");
					json.append(":");
					json.append("'" + ObjectUtils.getStringValue(aMap.get(key)) + "'");
					json.append(",");
				}
				json.append("childs:[");
				String str = getChildNodesJson(tep_id);
				json.append(str);
				json.append("]},");
			}
		}
		return json.toString();
	}

	public static void main(String args[]) {
		List list = new ArrayList();
		Map aMap = new HashMap();
		aMap.put("GUID", "1");
		aMap.put("FZBMC", "");
		list.add(aMap);
		aMap = new HashMap<String, String>();
		aMap.put("GUID", "1_1");
		aMap.put("FZBMC", "1");
		list.add(aMap);
		aMap = new HashMap<String, String>();
		aMap.put("GUID", "1_1_1");
		aMap.put("FZBMC", "1_1");
		list.add(aMap);

		aMap = new HashMap<String, String>();
		aMap.put("GUID", "1_1_2");
		aMap.put("FZBMC", "1_1");
		list.add(aMap);

		aMap = new HashMap<String, String>();
		aMap.put("GUID", "2");
		aMap.put("FZBMC", "");
		list.add(aMap);
		aMap = new HashMap<String, String>();
		aMap.put("GUID", "2_1");
		aMap.put("FZBMC", "2");
		list.add(aMap);
		aMap = new HashMap<String, String>();
		aMap.put("GUID", "2_1_1");
		aMap.put("FZBMC", "2_1");
		list.add(aMap);
		NodeUtil aa = new NodeUtil();
		aa.resultList = list;
		aa.setId("GUID");
		aa.setPid("FZBMC");
		String ss="";
		try {
			ss = new NodeUtil().listToNodesJson();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ss);

		JSONArray jsonArray = JSONArray.fromObject(ss);
		System.out.println(jsonArray.toString());

	}
}
