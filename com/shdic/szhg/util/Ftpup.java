package com.shdic.szhg.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;

public class Ftpup {
	/**
	 * 
	 * 此方法是为了ftp协议下载文件到本地
	 * 
	 * @param rometoPath
	 * @return
	 * @throws Exception
	 */
	public long download(String rometoPath) throws Exception {
		System.out.println("download:"+rometoPath);
		long result = 0;
		InputStream is = null;
		FileOutputStream os = null;
		try {
			// File file=new File("");
			FTPClient ftpClient = new FTPClient();
			// 服务器IP地址
			ftpClient.connect("27.115.58.110");
			ftpClient.login("anonymous", "331");
			String retopath = rometoPath.substring(rometoPath.indexOf("3323"));
			String path = retopath.replaceAll("/", "");
			System.out.println("path:~~~~~~~~~~~~~~~~~~~~~"+path);
			// 获取远程文件夹的文件名 retopath=3323_tigercafe/_3323_001.png
			is = ftpClient.retrieveFileStream(retopath);
			File outfile = null;
			File a = new File("");
			String ss=a.getAbsoluteFile().toString().replaceAll("bin", "webapps");
			
			// TODO 这个地址只是暂时的，以后必须的换
			outfile = new File(ss + File.separator + "Server"
					+ File.separator + "pic" + File.separator + path);
			System.out.println(ss + File.separator + "Server"
					+ File.separator + "pic" + File.separator + path);
			os = new FileOutputStream(outfile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
				result = result + c;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return result;
	}

	/**
	 * 
	 * 将本地的地址更新到表中 对SHOPENVIRONMENT 表的操作
	 */
	public String UpShopEnviroment() {
		
		String json = "";
		String sql = "";
		List<Map<String, String>> seatlist = null;
		List<Map<String, String>> Updatelist = null;
		Tools tools = new Tools();
		Ftpup ftpup = new Ftpup();
		String  msg=null;
		try {
			sql = "select pic from SHOPENVIRONMENT";
			seatlist =tools.find(sql);
			System.out.println("seatlist:~~~~~~~~~~~~~~~"+seatlist);
			if(seatlist.size()==0||seatlist==null){
				msg="查找图片结果:未查找到任何图片";
				System.out.println("查找图片结果:未查找到任何图片");
			}
			for (int i = 0; i < seatlist.size(); i++) {
				
				System.out.println("seatlist2~~~~~~~~~~~~~~~~~~~:"+seatlist.get(i).get("PIC"));
				// 头拼接文件
				String ftp = "http://192.168.0.82:8080/Server/pic/";
				// ftp地址
				String rometoPath = seatlist.get(i).get("PIC");
				// 将这些地址的图片下载到本地
				ftpup.download(seatlist.get(i).get("PIC"));
				String retopath = rometoPath.substring(rometoPath.indexOf("3323"));
				// 这是替换后的本地文件的尾部
				String path = retopath.replaceAll("/", "");
				String pic2=ftp+path;
				String picc=pic2.toString();
				System.out.println("picc:"+picc);
				// 拼接地址
				String updateSql = "update SHOPENVIRONMENT set PIC2='"+picc+"' where PIC='" + rometoPath + "'";
				
				tools.executeUpdate(updateSql);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新图片结果:更新图片失败");
			msg="更新图片结果:更新图片失败";
		}
              return msg;
	}
	
	/**
	 * 
	 * 将本地的地址更新到表中 对SHOPENVIRONMENT 表的操作
	 */
	public String UpUSERS() {
		
		String json = "";
		String sql = "";
		List<Map<String, String>> seatlist = null;
		Tools tools = new Tools();
		Ftpup ftpup = new Ftpup();
		String  msg=null;
		try {
			sql = "select photo from USERS";
			seatlist = tools.find(sql);
			if(seatlist.size()==0||seatlist==null){
				msg="查找图片结果:未查找到任何图片";
				System.out.println("查找图片结果:未查找到任何图片");
			}
			for (int i = 0; i < seatlist.size(); i++) {
				// 头拼接文件
				String ftp = "http://localhost:8080/Server/pic";
				// ftp地址
				String rometoPath = seatlist.get(i).get("pic");
				// 将这些地址的图片下载到本地
				ftpup.download(seatlist.get(i).get("photo"));
				String retopath = rometoPath.substring(rometoPath.indexOf("3323"));
				// 这是替换后的本地文件的尾部
				String path = retopath.replaceAll("/", "");
				// 拼接地址
				String updateSql = "update USERS set pic2='" + ftp
						+ "+" + path + "' where PHOTO=" + rometoPath + "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新图片结果:更新图片失败");
			msg="更新图片结果:更新图片失败";
		}
              return msg;
	}


}
