package com.shdic.szhg.interfacer;

import java.util.*;

public interface ProcessScheduleInterface {
	/**
	   * 自定义类的方法调用
	   * @return HashMap
	   */
	  public HashMap scheduleBusiness() throws Exception;


	  /**
	   * 自定义类的方法调用,带参数
	   * @return HashMap
	   */
	  public HashMap scheduleBusiness(Map map) throws Exception;
	  
	  /**
	   * 具体业务的操作方法(是否操作成功)
	   */
	  public boolean scheduleOperMethod() throws Exception;
	  
}
