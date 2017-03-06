package com.gxcrj.www.services.WeixinService;

public class CrjWeixinGXProxy implements com.gxcrj.www.services.WeixinService.CrjWeixinGX {
  private String _endpoint = null;
  private com.gxcrj.www.services.WeixinService.CrjWeixinGX crjWeixinGX = null;
  
  public CrjWeixinGXProxy() {
    _initCrjWeixinGXProxy();
  }
  
  public CrjWeixinGXProxy(String endpoint) {
    _endpoint = endpoint;
    _initCrjWeixinGXProxy();
  }
  
  private void _initCrjWeixinGXProxy() {
    try {
      crjWeixinGX = (new com.gxcrj.www.services.WeixinService.CrjWeixinGXServiceLocator()).getWeixinService();
      if (crjWeixinGX != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)crjWeixinGX)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)crjWeixinGX)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (crjWeixinGX != null)
      ((javax.xml.rpc.Stub)crjWeixinGX)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.gxcrj.www.services.WeixinService.CrjWeixinGX getCrjWeixinGX() {
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX;
  }
  
  public java.lang.String gascReVisaSave(java.lang.String data, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.gascReVisaSave(data, openid, ip);
  }
  
  public java.lang.String saveYypd(java.lang.String data, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.saveYypd(data, openid, ip);
  }
  
  public java.lang.String getSldOrYyrqInfo(java.lang.String code, java.lang.String yyrq, java.lang.String flag, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getSldOrYyrqInfo(code, yyrq, flag, openid, ip);
  }
  
  public java.lang.String ycjApplySave(java.lang.String data, java.lang.String openid, java.lang.String ip) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.ycjApplySave(data, openid, ip);
  }
  
  public java.lang.String getWeChatPushInfo(java.lang.String sldw) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getWeChatPushInfo(sldw);
  }
  
  public java.lang.String updateWeChatPushInfo(java.lang.String data) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.updateWeChatPushInfo(data);
  }
  
  public java.lang.String getZwgklx() throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getZwgklx();
  }
  
  public java.lang.String getZwgkList(java.lang.String lx) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getZwgkList(lx);
  }
  
  public java.lang.String getContentById(java.lang.String id) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getContentById(id);
  }
  
  public java.lang.String getDownloadData(java.lang.String jhsj, java.lang.String type) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getDownloadData(jhsj, type);
  }
  
  public java.lang.String getFlowInfo(java.lang.String ywbh, java.lang.String zjhm, java.lang.String rylb) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getFlowInfo(ywbh, zjhm, rylb);
  }
  
  public java.lang.String getEmsInfo(java.lang.String ywbh) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getEmsInfo(ywbh);
  }
  
  public java.lang.String getPreInfo(java.lang.String zjhm, java.lang.String rylb, java.lang.String cldw) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getPreInfo(zjhm, rylb, cldw);
  }
  
  public java.lang.String getPdyyInfo(java.lang.String zjzl, java.lang.String zjhm) throws java.rmi.RemoteException{
    if (crjWeixinGX == null)
      _initCrjWeixinGXProxy();
    return crjWeixinGX.getPdyyInfo(zjzl, zjhm);
  }
  
  
}