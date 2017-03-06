import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;
public class test {

	public void connectServer() throws IOException {
		try {
			// server：FTP服务器的IP地址；user:登录FTP服务器的用户名 测试
			// password：登录FTP服务器的用户名的口令；path：FTP服务器上的路径

			FTPClient ftpClient = new FTPClient();
			// 服务器IP地址
			ftpClient.connect("27.115.58.110");

			ftpClient.login("anonymous", "331");
			int reply = ftpClient.getReplyCode(); 
			            if(!FTPReply.isPositiveCompletion(reply)) { 
			  
			                ftpClient.disconnect();  
		                System.err.println("FTP server refused connection."); 
			            }
			// path是ftp服务下主目录的子目录
			// if (path.length() != 0) ftpClient.cd(path);
			// //用2进制上传、下载
//			ftpClient.binary();
		
		} catch (FtpLoginException e) {
			String a = "登陆主机失败!请检查用户名或密码是否正确：" + e;
			System.out.println(a);
		} catch (IOException e) {
			String a = "连接主机失败!请检查端口是否正确：" + e; // 字串3
			System.out.println(a);
			// return false;
		} catch (SecurityException e) {
			String a = "无权限与主机连接!请检查是否有访问权限：" + e;
			System.out.println(a);
			// return false;
		}
	}

	/**
	 * upload 上传文件
	 * 
	 * @throws java.lang.Exception
	 * @return -1 文件不存在 -2 文件内容为空 >0 成功上传，返回文件的大小
	 * @param newname
	 *            上传后的新文件名
	 * @param filename
	 *            上传的文件 //
	 */
	// public long upload(String filename,String newname) throws Exception
	// {
	// long result = 0;
	// TelnetOutputStream os = null;
	// FileInputStream is = null;
	// try {
	// FtpClient ftpClient = new FtpClient();
	// java.io.File file_in = new java.io.File(filename);
	// if (!file_in.exists()) return -1;
	// if (file_in.length()==0) return -2;
	// os = ftpClient.put(newname);
	// result = file_in.length();
	// is = new FileInputStream(file_in);
	// byte[] bytes = new byte[1024];
	// int c;
	// while ((c = is.read(bytes)) != -1) {
	// os.write(bytes, 0, c);
	// }
	// } finally {
	// if (is != null) {
	// is.close();
	// }
	// if (os != null) {
	// os.close();
	// }
	// }
	// return result;
	// }
	// /**
	// * upload
	// * @throws java.lang.Exception
	// * @return
	// * @param filename
	// */
	// public long upload(String filename)
	// throws Exception
	// {
	// String newname = "";
	// if (filename.indexOf("/")>-1)
	// {
	// newname = filename.substring(filename.lastIndexOf("/")+1);
	// }else
	// {
	// newname = filename;
	// }
	// return upload(filename,newname);
	// }

	/**
	 * download 从ftp下载文件到本地
	 * 
	 * @throws java.lang.Exception
	 * @return
	 * @param newfilename
	 *            本地生成的文件名
	 * @param filename
	 *            服务器上的文件名
	 */
	public long download(String rometoPath) throws Exception {
		long result = 0;
		InputStream is = null;
		FileOutputStream os = null;
		try { 
			// File file=new File("");
			FTPClient ftpClient = new FTPClient();
			// 服务器IP地址
			ftpClient.connect("27.115.58.110");
			ftpClient.login("anonymous", "331");
			String retopath=rometoPath.substring(rometoPath.indexOf("3323"));
			String path=retopath.replaceAll("/", "");
			//获取远程文件夹的文件名  retopath=3323_tigercafe/_3323_001.png
			is = ftpClient.retrieveFileStream(retopath);
			File outfile=null;
			File a=new File("");
			// TODO  这个地址只是暂时的，以后必须的换
				outfile = new File(a.getAbsolutePath()+File.separator+ "WebRoot"+File.separator+"pic"+File.separator+path );
		         System.out.println(a.getAbsolutePath()+File.separator+"WebRoot"+ File.separator+"pic"+File.separator+path );
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
	 * 取得某个目录下的所有文件列表
	 * 
//	 */
//	 public List getFileList()
//	 {
//	 List list = new ArrayList();
// try
//	 {
//
//	
//	 String filename = "";
//	 while((filename=dis.readLine())!=null)
//	 {
//	 list.add(filename);
//	 }
//			      
//	 } catch (Exception e)
//	 {
// e.printStackTrace();
//	 }
//	 return list;
//	 }

	/**
	 * closeServer 断开与ftp服务器的链接
	 * 
	 * @throws java.io.IOException
	 */
//	public void closeServer() throws IOException {
//		try {
//			FTPClient ftpClient = new FTPClient();
//			if (ftpClient != null) {
//				ftpClient.cdup();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) throws Exception {
	
	
		String s=":'[123123";
//		t.download(s);
//		File a=new File("");
//		System.out.println(a.getAbsolutePath()+ File.separator+"WebRoot"+ File.separator+"pic"+File.separator+"A.jpg");
	
		String picA= s.replaceAll("\'[","[" );
		System.out.println(picA);
//	System.out.println(s.substring(s.indexOf("3323")));
		
	}

}
