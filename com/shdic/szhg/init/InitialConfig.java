package com.shdic.szhg.init;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitialConfig 
{
    public static SessionFactory sessionFactory;
    public static Logger logger = Logger.getLogger(InitialConfig.class.getName());
    
    /**
     * 初始化，提取出配置库中的所有信息
     */
    public static void init(ServletContextEvent servletContextEvent)
    {
    	try
        {
//            /**
//             * 初始化SessionFactory
//             */
//            Configuration config = new Configuration().configure(
//                    "/hibernate.cfg.xml");
//          sessionFactory = config.buildSessionFactory();
    		WebApplicationContext ctx = WebApplicationContextUtils.
            getWebApplicationContext(servletContextEvent.
                                     getServletContext());
    		logger.info("配置hibernate信息");
    		sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");

            logger.info("hibernate配置设置正确");
        } catch (HibernateException e)
        {
            logger.error(e.getMessage() + "DICErrorCode=010001", e);
        }
    }

    /**
     * 生成Session
     * @return Session
     */
    private static Session createSession()
    {
        try
        {
            return sessionFactory.openSession();
        } catch (HibernateException e)
        {
            logger.error(e.getMessage() + "DICErrorCode=010002", e);

            return null;
        }
    }

    private static void closeSession(Session session)
    {
        try
        {
            if (session != null)
            {
                session.close();
            }
        } catch (Exception e)
        {
        }
    }

   

    
    /**
     * 清空内存
     *
     */
    public static void destroy()
    {
        try
        {
            sessionFactory.close();
        } catch (Exception e)
        {
            logger.error(e.getMessage() + "关闭配置库连接时发生错误");
        }
    }
}
