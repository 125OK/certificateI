/**
 * CrjWeixinGX.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.gxcrj.www.services.WeixinService;

public interface CrjWeixinGX extends java.rmi.Remote {
    public java.lang.String gascReVisaSave(java.lang.String data, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException;
    public java.lang.String saveYypd(java.lang.String data, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException;
    public java.lang.String getSldOrYyrqInfo(java.lang.String code, java.lang.String yyrq, java.lang.String flag, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException;
    public java.lang.String ycjApplySave(java.lang.String data, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException;
    public java.lang.String getWeChatPushInfo(java.lang.String sldw) throws java.rmi.RemoteException;
    public java.lang.String updateWeChatPushInfo(java.lang.String data) throws java.rmi.RemoteException;
    public java.lang.String getZwgklx() throws java.rmi.RemoteException;
    public java.lang.String getZwgkList(java.lang.String lx) throws java.rmi.RemoteException;
    public java.lang.String getContentById(java.lang.String id) throws java.rmi.RemoteException;
    public java.lang.String getDownloadData(java.lang.String jhsj, java.lang.String type) throws java.rmi.RemoteException;
    public java.lang.String getFlowInfo(java.lang.String ywbh, java.lang.String zjhm, java.lang.String rylb) throws java.rmi.RemoteException;
    public java.lang.String getEmsInfo(java.lang.String ywbh) throws java.rmi.RemoteException;
    public java.lang.String getPreInfo(java.lang.String zjhm, java.lang.String rylb, java.lang.String cldw) throws java.rmi.RemoteException;
    public java.lang.String getPdyyInfo(java.lang.String zjzl, java.lang.String zjhm) throws java.rmi.RemoteException;
}
