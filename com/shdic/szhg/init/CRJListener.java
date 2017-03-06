package com.shdic.szhg.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class CRJListener

    implements ServletContextListener {
  public static String sessionID;
  

  /**
   * 服务启动时，初始化所有配置库中的信息
   */
  public void contextInitialized(ServletContextEvent servletContextEvent) {

    InitialConfig.init(servletContextEvent);

  }
  /**
   * 服务关闭时，清空配置库中的信息
   */
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    InitialConfig.destroy();

  }
}
