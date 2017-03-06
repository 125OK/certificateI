package com.shdic.szhg.newServicesImpl;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.gxcrj.www.services.WeixinService.CrjWeixinGX;
import com.gxcrj.www.services.WeixinService.CrjWeixinGXServiceLocator;

public class Test {
	public static void main(String[] args) throws ServiceException, RemoteException {
		CrjWeixinGXServiceLocator ss= new CrjWeixinGXServiceLocator();
		CrjWeixinGX wx=ss.getWeixinService();
		System.out.println(wx.getDownloadData("20161222000000", "")); 
	}
}
