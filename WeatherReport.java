import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class WeatherReport {

	/**
	 * 发送请求到GOOGLE获取天气信息页面
	 * 
	 * @param city
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private static String getInputStream(String city) {
		try {
			HttpClient httpClient = new HttpClient();
			String hostUrl = "http://www.google.com.hk/search?hl=zh-CN&q=tq%20"
					+ URLEncoder.encode(city);
			GetMethod getMethod = new GetMethod(hostUrl);
			// 使用系统提供的默认的恢复策略
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			try {
				// 执行getMethod
				int statusCode = httpClient.executeMethod(getMethod);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: "
							+ getMethod.getStatusLine());
				}
				//String result = getMethod.getResponseBodyAsString();
				InputStream is=getMethod.getResponseBodyAsStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));   

		        StringBuilder sb = new StringBuilder();   

		    

		        String line = null;   

		        try {   

		            while ((line = reader.readLine()) != null) {   

		                sb.append(line + "/n");   

		            }   

		        } catch (IOException e) {   

		            e.printStackTrace();   

		        } finally {   

		            try {   

		                is.close();   

		            } catch (IOException e) {   

		                e.printStackTrace();   

		            }   

		        }    
		        
				return sb.toString();
			} catch (HttpException e) {
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				System.out.println("Please check your provided http address!");
				e.printStackTrace();
			} catch (IOException e) {
				// 发生网络异常
				e.printStackTrace();
			} finally {
				// 释放连接
				getMethod.releaseConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 将获取的包含天气页面的信息解析出相关的天气信息
	 * 
	 * @param result
	 * @identifier
	 * @return
	 */
	public static String readText(String result, String identifier) {
		Pattern shopNumberPattern = Pattern.compile(identifier);
		Matcher shopNamMatcher = shopNumberPattern.matcher(result);
		if (shopNamMatcher.find())
			return shopNamMatcher.group(1);
		return "";
	}

	public static String getWeather(String city) {
		/*
		 * String result = readText(getInputStream(city),
		 * "<div style=\"padding:5px;float:left\">(.+?)</div></div>"); String
		 * weatherDetail = readText(result,
		 * "<div style=\"font-size:140%\">(.+?)%"); weatherDetail =
		 * weatherDetail.replaceAll("<div>", "").replaceAll( "</div>",
		 * "").replaceAll("<br>", "").replace("：", ":");
		 */

		String result = readText(getInputStream(city),"<span style=\"display:inline\" id=\"wob_tm\" class=\"wob_t\">([^<>]*)</span>");
				//"<div id=\"wob_wc\" class=\"vk_c\">(.+?)</div></div>");
		System.out.println(result);
		String weatherDetail = readText(result,
				"<div style=\"font-size:140%\">(.+?)%");
		weatherDetail = weatherDetail.replaceAll("<div>", "").replaceAll(
				"</div>", "").replaceAll("<br>", "").replace("：", ":");
		return weatherDetail;
	}

	public static void main(String[] args) {
		WeatherReport wr = new WeatherReport();
		String tq = wr.getWeather("郑州");
		System.out.println("tq==" + tq);
		
		//String ss ="";
		//System.out.println(readText(ss,"<span style=\"display:inline\" id=\"wob_tm\" class=\"wob_t\">([^<>]*)</span>"));;
	}
	
	

}