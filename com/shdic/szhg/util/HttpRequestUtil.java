package com.shdic.szhg.util;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.net.URLConnection;  
  
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.HttpException;  
import org.apache.commons.httpclient.HttpMethod;  
import org.apache.commons.httpclient.NameValuePair;  
import org.apache.commons.httpclient.methods.GetMethod;  
import org.apache.commons.httpclient.methods.PostMethod;  
  
public class HttpRequestUtil {  
  
    public static String getRequestByUrl(String strurl){  
        String strjson = "";  
        try {  
            URL url = new URL(strurl);  
            URLConnection conn = url.openConnection();  
            HttpURLConnection http = (HttpURLConnection)conn;  
            http.setRequestMethod("GET");  
            http.setDoInput(true);  
            http.setDoOutput(true);  
            http.connect();  
            InputStream in = http.getInputStream();  
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));  
            String s = null;  
            while((s = br.readLine()) != null) {  
                strjson+=s;  
            }  
            br.close();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
        return strjson;  
    }  
      
      
    /**  
     * 封装请求的信息
     * @param latlng   坐标位置
     * @return
     * @throws IOException
     */
    private static HttpMethod getGetMethod(String latlng) throws IOException {  
       // PostMethod post = new PostMethod("/maps/api/geocode/json");  
        GetMethod get = new GetMethod("/maps/api/geocode/json");  
          
        NameValuePair simcard = new NameValuePair("latlng", latlng);  
        NameValuePair simcard1 = new NameValuePair("sensor", "false");  
        NameValuePair simcard2 = new NameValuePair("language", "zh-CN");  
        get.setQueryString(new NameValuePair[] { simcard, simcard1,simcard2});  
          
        //InputStream input = new FileInputStream(new File("/home/ubuntu/my.txt"));  
        //"".getBytes("ISO8859-1")  
        //InputStream input = new StringBufferInputStream("my test aaaaaaaaaa");  
        //post.setRequestBody(input);  
        return get;  
    }  
    
    /**  
     * 封装请求的信息
     * @param cityName   坐标位置
     * @return
     * @throws IOException
     */
    private static HttpMethod getGetMethod_CityName(String cityName) throws IOException {  
       // PostMethod post = new PostMethod("/maps/api/geocode/json");  
        GetMethod get = new GetMethod("/maps/api/geocode/json");  
          
        NameValuePair simcard = new NameValuePair("address", cityName);  
        NameValuePair simcard1 = new NameValuePair("sensor", "false");  
        NameValuePair simcard2 = new NameValuePair("language", "zh-CN");  
        get.setQueryString(new NameValuePair[] { simcard, simcard1,simcard2});  
          
        //InputStream input = new FileInputStream(new File("/home/ubuntu/my.txt"));  
        //"".getBytes("ISO8859-1")  
        //InputStream input = new StringBufferInputStream("my test aaaaaaaaaa");  
        //post.setRequestBody(input);  
        return get;  
    }  
    
    /**  
     * 封装请求的信息
     * @param latlng   坐标位置
     * @return
     * @throws IOException
     */
    private static HttpMethod getPostMethod(String latlng) throws IOException {  
        PostMethod post = new PostMethod("/maps/api/geocode/json");  
        //latlng=40.714224,-73.961452&sensor=false&&language=zh-TW  
        NameValuePair simcard = new NameValuePair("latlng", latlng);  
        NameValuePair simcard1 = new NameValuePair("sensor", "false");  
        NameValuePair simcard2 = new NameValuePair("language", "zh-CN");  
          
        post.setRequestBody(new NameValuePair[] { simcard, simcard1,simcard2});  
        //InputStream input = new FileInputStream(new File("/home/ubuntu/my.txt"));  
        //"".getBytes("ISO8859-1")  
        //InputStream input = new StringBufferInputStream("my test aaaaaaaaaa");  
        //post.setRequestBody(input);  
        return post;  
    }  
      
    /** 
     * 根据经纬度获取地址 （地图是以URL的形式传递Get方式）
     * @param latlng 坐标信息（31.1899209667,121.3918055000）
     * @return 
     */  
    public static  String getGoogleAddressBylatlng(String latlng){  
        String strAddress = "";  
        HttpClient client = new  HttpClient();  
        client.getHostConfiguration().setHost("ditu.google.com", 80, "http");  
        HttpMethod method = null;  
        try {  
            method = getGetMethod(latlng);  
        } catch (IOException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }// 使用GET方式提交数据  
        try {  
            client.executeMethod(method);  
        } catch (HttpException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        } catch (IOException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
            return "获取经纬度地址异常";  
        }  
        // 打印服务器返回的状态  
        int methodstatus = method.getStatusCode();  
        StringBuffer sb = new StringBuffer();  
        if(methodstatus == 200){  
            try {  
                BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"UTF-8"));  
                String line;  
                while ((line = rd.readLine()) != null) {  
                    sb.append(line);  
  
                }  
                System.out.println("----"+sb);
                JSONObject jo;  
                try {  
                    jo = JSONObject.fromObject(sb.toString());  
                    JSONArray ja = jo.getJSONArray("results");
                    JSONObject jo1 = ja.getJSONObject(0);  
                    System.out.println(jo1.getString("formatted_address"));  
                    strAddress = jo1.getString("formatted_address");  
                } catch (JSONException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                rd.close();  
            }catch (IOException e) {  
                throw new RuntimeException("error", e);  
            }  
        }  
        method.releaseConnection();  
        return strAddress;  
    }  
    
    /** 
     * 根据名称获取坐标 （地图是以URL的形式传递Get方式）
     * @param cityName 城市名称（郑州）
     * @return "{lat:"+lat+",lng:"+lng+",longName:"+long_name+"}";
     */  
    public static  String getGoogleAddressByCityName(String cityName){  
        String lng = ""; //经度 
        String lat = "";//纬度
        String long_name = "";//标准名称
        HttpClient client = new  HttpClient();  
        client.getHostConfiguration().setHost("ditu.google.com", 80, "http");  
          
        HttpMethod method = null;  
        try {  
            method = getGetMethod_CityName(cityName);  
        } catch (IOException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        }// 使用GET方式提交数据  
        try {  
            client.executeMethod(method);  
        } catch (HttpException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
        } catch (IOException e1) {  
            // TODO Auto-generated catch block  
            e1.printStackTrace();  
            System.out.println("获取经纬度地址异常");
            return "";  
        }  
        // 打印服务器返回的状态  
        int methodstatus = method.getStatusCode();  
        StringBuffer sb = new StringBuffer();  
        if(methodstatus == 200){  
            try {  
                BufferedReader rd = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"UTF-8"));  
                String line;  
                while ((line = rd.readLine()) != null) {  
                    sb.append(line);  
  
                }  
                //System.out.println("&&&&&&&&"+sb);
                JSONObject jo;  
                try {  
                    jo = JSONObject.fromObject(sb.toString());  
                    JSONArray ja = jo.getJSONArray("results");
                    JSONObject jo1 = ja.getJSONObject(0);  
                    JSONObject  geometry = jo1.getJSONObject("geometry");
                    JSONObject  location = geometry.getJSONObject("location");
                    lng = location.getString("lng");
                    lat = location.getString("lat");
                    
                    JSONArray  address_components = jo1.getJSONArray("address_components");
                    JSONObject locationInfo =(JSONObject) address_components.get(0);
                    long_name  = locationInfo.getString("long_name");
                    System.out.println(lng);  
                } catch (JSONException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                rd.close();  
            }catch (IOException e) {  
                throw new RuntimeException("error", e);  
            }  
        }  
        method.releaseConnection(); 
        if(!"".equals(long_name)){
        	 String retJson="{lat:"+lat+",lng:"+lng+",longName:'"+long_name+"'}";
        	 return retJson;  
        }else{
        	System.out.println("根据城市名称获取经纬度异常");
        	return "";  
        }
       
        
    }  
      
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
       /* String str = getRequestByUrl("http://ditu.google.com/maps/api/geocode/json?latlng=31.1899209667,121.3918055000&sensor=false&&language=zh-CN");  
        System.out.println("++++"+str);  
        String strhttp = HttpRequestUtil.getGoogleAddressBylatlng("31.1899209667,121.3918055000");  
        System.out.println(strhttp);  */
        
        String strhttp = HttpRequestUtil.getGoogleAddressByCityName("河南郑州");  
        System.out.println(strhttp);  
    }  
  
}  