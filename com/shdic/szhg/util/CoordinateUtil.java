package com.shdic.szhg.util;

import java.net.URL;

import org.codehaus.xfire.client.Client;

/**
 * 获取坐标服务工具类
 * @author Administrator
 *
 */
public class CoordinateUtil {

	public static void main(String[] args) {
		try {
			String newurl ="http://maps.googleapis.com/maps/api/geocode/json?address=%E9%83%91%E5%B7%9E&sensor=true&language=cn";
			
			Client client = new Client(new URL(newurl));	
			Object[] results0 = client.invoke("getWeatherbyCityName", new Object[]{"北京"});
			System.out.println("获取："+results0[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
