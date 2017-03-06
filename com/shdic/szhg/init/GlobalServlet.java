package com.shdic.szhg.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.struts.action.ActionServlet;


public class GlobalServlet extends ActionServlet {
	public static Logger logger = Logger.getLogger(GlobalServlet.class
			.getName());

	public void init() throws ServletException {
		super.init();
		
		ServletContext sc = this.getServletContext();
		String chartFilePath = sc.getRealPath("") + "chartfile\\";
		Constants.CHART_PATH = chartFilePath.replaceAll("\\\\", "\\\\\\\\");
		Constants.CHART_PATH_JAVA = chartFilePath;
		String log4jConfige = sc.getRealPath("")+"\\WEB-INF\\classes\\log4j.properties";
		//加载log4j的配置文件
		PropertyConfigurator.configure(log4jConfige);
		
		System.out.println("初始化加载代码...");
		
		
		try {
			
			
			/* 
			 * 代码信息
			 * 全部放入静态变量中
			 */
			
			//InitData.SystemCode = GlobalData.GlobalCode();
			//System.out.println("放入缓存InitData.SystemCode=="+InitData.SystemCode);
			//InitData.Structure = GlobalData.GlobalStructurepara();
			//InitData.Message = GlobalData.GlobalMessage();			
			/**
			 * sc.setAttribute(Constants.CODE, GlobalData.GlobalCode());
			 * sc.setAttribute(Constants.STRUCTURE, GlobalData.GlobalStructurepara());
			 * sc.setAttribute(Constants.MESSAGE, GlobalData.GlobalMessage());//消息表mesage的基本信息
			 * sc.setAttribute(Constants.TABLEMESSAGE,GlobalData.GlobalTableMessage(pluginPath));//表配置信息
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("初始化加载代码完成");
	}
}
