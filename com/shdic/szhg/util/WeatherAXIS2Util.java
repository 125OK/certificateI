package com.shdic.szhg.util;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;


/**
 * 获取天气工具类
 * @author Administrator
 *
 */
public class WeatherAXIS2Util{
    private static String url = "http://webservice.webxml.com.cn/WebServices/WeatherWebService.asmx";
	//private static String url = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl";
    //端点引用 指接口位置
    private static EndpointReference targetEpr = new EndpointReference(url);
    //有抽象OM工厂获取OM工厂，创建request SOAP包
    private static OMFactory fac = OMAbstractFactory.getOMFactory();
   
    public static OMElement getOMMethod(String methodStr,String namespace,String tns,String[] pars,String[] vals){
        //创建命名空间
        OMNamespace nms = fac.createOMNamespace(namespace, tns);
        //创建OMElement方法 元素，并指定其在nms指代的名称空间中
        OMElement method = fac.createOMElement(methodStr, nms);
        //添加方法参数名和参数值
        for(int i=0;i<pars.length;i++){
            //创建方法参数OMElement元素
            OMElement param = fac.createOMElement(pars[i],nms);
            //设置键值对 参数值
            param.setText(vals[i]);
            //讲方法元素 添加到method方法元素中
            method.addChild(param);
        }
        return method;
    }
   
    public static Options getClientOptions(String action){
        //创建request soap包 请求选项
        Options options = new Options();
        //设置options的soapAction
        options.setAction(action);
        //设置request soap包的端点引用(接口地址)
        options.setTo(targetEpr);
        //如果报错提示Content-Length，请求内容长度
        options.setProperty(HTTPConstants.CHUNKED,"false");//把chunk关掉后，会自动加上Content-Length。
        return options;
    }


    public static OMElement getWeather(String action,String methodStr,String namespace,String tns,String[] pars,String[] vals){
        OMElement result = null;
        try {
            ServiceClient client = new ServiceClient();
            client.setOptions(getClientOptions(action));
            result =  client.sendReceive(getOMMethod(methodStr,namespace,tns,pars,vals));
            try {
            	client.cleanupTransport();
			} catch (Exception e) {
				
			}
            
        } catch (AxisFault e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据城市名称获取天气信息
     * @param cityName
     * @return
     */
   public static String getWeatherInfo(String cityName){
	   try{
		   String action  = "http://WebXml.com.cn/getWeatherbyCityName";
	       String methodStr = "getWeatherbyCityName";
	       
		   //String action  = "http://WebXml.com.cn/getWeather";
		   //String methodStr = "getWeather";
	        
	       
	       String namespace = "http://WebXml.com.cn/";
	       String tns = "xsd";
	       String[] pars = {"theCityName"};
	       //String[] pars = {"theCityCode"};
	       String[] vals = {cityName};
	       OMElement result = null;
	       result = getWeather(action, methodStr, namespace, tns, pars, vals);
	       String resultJson= Xml2JsonUtil.xml2JSON(result.toString()) ;
	       System.out.println("resultJson::"+resultJson);
	       JSONObject cityJson = JSONObject.fromObject(resultJson);
	       JSONObject getWeatherbyCityNameResponse = (JSONObject) cityJson.get("getWeatherbyCityNameResponse");//  getWeatherResponse
	       JSONArray getWeatherbyCityNameResult = getWeatherbyCityNameResponse.getJSONArray("getWeatherbyCityNameResult");//  getWeatherResult
	       JSONObject infoObj= (JSONObject) getWeatherbyCityNameResult.get(0);
	       JSONArray stringArray =infoObj.getJSONArray("string");
	       System.out.println("stringArray--"+stringArray);
	       String state = stringArray.getString(6).split(" ")[1];//7
	       String retJson = "{weather:'"+state+"',temperature:'"+stringArray.getString(5)+"'}";//8
	       return retJson;
	   }catch (Exception e) {
		   e.printStackTrace();
		   System.out.println("WeatherAXIS2Util获取天气异常，请检查天气是否标准");
		   return "";
	   }
	   
   }
    
    //////ok
  public static void main(String[] args) {
        /*String action  = "http://WebXml.com.cn/getWeatherbyCityName";
        String methodStr = "getWeatherbyCityName";
        String namespace = "http://WebXml.com.cn/";
        String tns = "xsd";
        String[] pars = {"theCityName"};
        String[] vals = {"杭州"};
        OMElement result = null;
        result = getWeather(action, methodStr, namespace, tns, pars, vals);
        String s= Xml2JsonUtil.xml2JSON(result.toString()) ;
        System.out.println(s);*/
	    System.out.println(getWeatherInfo("郑州"));
    }
   
}
