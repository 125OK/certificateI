package com.shdic.szhg.init;

/**
 * 进行代码的组织
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: shdic</p>
 * @author zangql
 * @version 1.0
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.shdic.szhg.util.Tools;

public class OrganizeCode {
	
	public static Logger logger = Logger.getLogger(OrganizeCode.class
			.getName());
	
	//进行代码组织，将代码信息放到map对象中
	public HashMap OrganizeCode() {
		Connection connect = null;

		String strsql = null;
		HashMap hmcode = new HashMap();
		HashMap jsCode = new HashMap();
		HashMap jgCode = new HashMap();
		hmcode.put("全部警署", jsCode);
		hmcode.put("全部机构", jgCode);
		LinkedHashMap lhmcodeN = null;
		LinkedHashMap lhmcode = null;
		
		
		
			
		
		//加载system_code
		strsql = "select * from system_code order by dmlb,pxsx,dmz";
		try {
			connect = DBAdapter.getConnect(Constants.DS_SZHG);
			ResultSet rs = connect.createStatement().executeQuery(strsql);
			logger.info("加载system_code代码信息 strsql:"+strsql);
			String dmlb = null;
			while (rs.next()) {
				if (dmlb == null
						|| (dmlb != null && !dmlb.equals(rs.getString("dmlb")))) {
					dmlb = rs.getString("dmlb");
					lhmcode = new LinkedHashMap();
					lhmcodeN = new LinkedHashMap();
					hmcode.put(dmlb, lhmcode);
					hmcode.put(dmlb + "N", lhmcodeN);
				}

				lhmcode.put(rs.getString("dmz"), rs.getString("dmmc"));
				if (rs.getString("ywmc") != null
						&& rs.getString("ywmc").length() > 0)
					lhmcodeN.put(rs.getString("dmz"), rs.getString("ywmc"));
				if (dmlb != null && dmlb.endsWith("警署"))
					jsCode.put(rs.getString("dmz"), rs.getString("dmmc"));
				if (dmlb != null && dmlb.endsWith("机构"))
					jgCode.put(rs.getString("dmz"), rs.getString("dmmc"));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (connect != null && !connect.isClosed())
					connect.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return hmcode;
	}
	
	
	
	public HashMap getStructurePara() {
        Connection connect = null;

        String strsql = null;
        HashMap hmcode = new HashMap();
        LinkedHashMap lhmcode = null;
        strsql = "select * from structure_para order by jgdm";
        try {

            connect = DBAdapter.getConnect(Constants.DS_SZHG);

            ResultSet rs = connect.createStatement().executeQuery(strsql);
            String jgdm = null;
            while (rs.next()) {
                if (jgdm == null ||
                    (jgdm != null && !jgdm.equals(rs.getString("jgdm")))) {
                    jgdm = rs.getString("jgdm");
                    lhmcode = new LinkedHashMap();
                    hmcode.put(jgdm, lhmcode);
                }
                HashMap tmp = new HashMap();
                lhmcode.put(rs.getString("para"), rs.getString("csz"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (connect != null && !connect.isClosed()) {
                    connect.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //System.out.println(hmcode);
        return hmcode;
    }
	/**
	 * 查询message_window中的信息
	 */
	public HashMap getMessageCode()
	{
		 Connection connect = null;

	        String strsql = null;
	        HashMap mscode = new HashMap();
	        LinkedHashMap msLcode = null;
	        strsql = "select xxid, xxmc from mesage  order by xxid";
	        try {

	            connect = DBAdapter.getConnect(Constants.DS_SZHG);

	            ResultSet rs = connect.createStatement().executeQuery(strsql);
	            //logger.info("加载message_code代码信息 strsql:"+strsql);
	            String xxid = null;
	            while (rs.next()) {
	                if (xxid == null ||
	                    (xxid != null && !xxid.equals(rs.getString("xxid")))) {
	                	xxid = rs.getString("xxid");
	                	msLcode = new LinkedHashMap();
	                    mscode.put(xxid, msLcode);
	                }
	                HashMap tmp = new HashMap();
	                msLcode.put("xxmc", rs.getString("xxmc"));
	            }
	            rs.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        finally {
	            try {
	                if (connect != null && !connect.isClosed()) {
	                    connect.close();
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	       
	        return mscode;
	} 
	
	
}
