package com.shdic.szhg.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class XMLToMap {

	/**
	 * 将xml字符串转化成list
	 */
	public static List<Map<String, String>> xmlStrToList(String xmlStr) {
		System.out.println("获取的xml字符串:"+ xmlStr);
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		try {
			Document document = DocumentHelper.parseText(xmlStr);
			Element element = document.getRootElement();
			
			for(Iterator<Element> it0 = element.elementIterator();it0.hasNext();) {
				Element element2 = it0.next();
				Map<String, String> map = new HashMap<String, String>();
				for(Iterator<Element> i = element2.elementIterator();i.hasNext();){
					Element e = i.next();
					if("ywbh".equals(e.getName())){
						System.out.println(e.getText());
					}
					map.put(e.getName(), e.getText());
				}
				list.add(map);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
			System.out.println("转化xml失败");
		}
		return list;
	}
	
}
