package com.shdic.szhg.newServicesImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.ResultSetMetaData;

import oracle.sql.BLOB;

import org.apache.axiom.util.blob.Blob;
import org.apache.taglibs.standard.lang.jpath.adapter.Convert;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.hibernate.Hibernate;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;




import com.shdic.szhg.init.Constants;
import com.shdic.szhg.init.DBAdapter;
import com.shdic.szhg.newServices.AndroidService;
import com.shdic.szhg.util.DateUtil;
import com.shdic.szhg.util.JsonUtil;
import com.shdic.szhg.util.MD5;
import com.shdic.szhg.util.StringUtil;
import com.shdic.szhg.util.Tools;

@SuppressWarnings("unchecked")
public class AndroidServiceImpl implements AndroidService {

	/**
	 * @author lidh
	 * 新用户注册
	 * @param conJson
	 * @return
	 */
	public String addYhxx(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		List reList =  null; //查询结果转换的LIST集合
		Connection conn = null;
		PreparedStatement ps = null;
			
			try{
				String sjhm  = getMap.get("sjhm")==null?"":(String)getMap.get("sjhm"); //手机号码，即用户名
				String mm = getMap.get("mm")==null?"":(String)getMap.get("mm");	//密码
				String code = getMap.get("code")==null?"":(String)getMap.get("code");	//手机验证码
				
				MD5 md = new MD5();
				mm = md.getMD5ofStr(mm);

				/*首先查询验证码是否匹配*/
				conn = DBAdapter.getConnect("CRJWGR");
				conn.setAutoCommit(false);
				StringBuffer codesql = new StringBuffer("select dxyzm from (select * from dxyzm_info t  where sjhm = ? ")
												.append("order by lsh desc)  where rownum = 1");
				ps = conn.prepareStatement(codesql.toString());
				
				ps.setString(1, sjhm);
				
				reList = getPreInfo(ps.executeQuery());
				Map ma = (Map)reList.get(0);
				if(code.equals(ma.get("DXYZM"))){
					/*如果验证码匹配，查询数据库里面是否存在此手机号码*/
					StringBuffer checksql = new StringBuffer("select * from register_user_info where sjhm = ?");
					ps = conn.prepareStatement(checksql.toString());
					
					ps.setString(1, sjhm);
					
					reList = getPreInfo(ps.executeQuery());

					/*如果存在，则返回已被注册信息*/
					if(reList.size()>0){
						retMap.put("data", "此手机号码已被注册！");
						retMap.put("type", "false");	
					/*否则进行添加*/					
					}else{
						StringBuffer sql = new StringBuffer("insert into register_user_info(sjhm,mm,zcsj)")
													.append("values(?,?,?)");
						ps = conn.prepareStatement(sql.toString());
						
						ps.setString(1, sjhm);
						ps.setString(2, mm);
						ps.setString(3, DateUtil.DateToString14(new Date()));

						ps.executeUpdate();
						conn.commit();
						retMap.put("data", "注册成功！");
						retMap.put("type", "true");
					}				
				}else{
					retMap.put("data", "验证码错误！");
					retMap.put("type", "false");				
				}
				

			}catch(JSONException e1){
				
				retMap.put("data", "传送数据有误，请联系管理员！");
				retMap.put("type", "false");
				e1.printStackTrace();
						
			}catch(Exception e){
				try{
					conn.rollback();
				}catch(SQLException ex){
					ex.printStackTrace();
					System.out.println("数据库回滚执行失败！");
				}
				retMap.put("data", "系统错误，请联系管理员！");
				retMap.put("type", "error");
				e.printStackTrace();
	
			}finally{
				DBAdapter.close(conn);
				DBAdapter.close(ps);
			}

		
		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}

	/**
	 * @author lidh
	 * 住宿信息登记
	 * @param conJson
	 * @return
	 */
	public String addZsxx(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		BLOB blob = null;
		OutputStream out = null;
		ByteArrayInputStream bi = null;
		
		
			
			try{
				String gjdq  = getMap.get("gjdq")==null?"":(String)getMap.get("gjdq"); //国家地区
				String zjhm = getMap.get("zjhm")==null?"":(String)getMap.get("zjhm");	//证件号码
				String rzsj = getMap.get("rzsj")==null?"":(String)getMap.get("rzsj");	//入住时间
				String nlkrq = getMap.get("nlkrq")==null?"":(String)getMap.get("nlkrq");	//拟离开日期
				String jdrsfzhm = getMap.get("jdrsfzhm")==null?"":(String)getMap.get("jdrsfzhm");	//接待人身份证号码
				String jdrsjhm = getMap.get("jdrsjhm")==null?"":(String)getMap.get("jdrsjhm");	//接待人手机号码
				String zsxzqh = getMap.get("zsxzqh")==null?"":(String)getMap.get("zsxzqh");	//住宿所在区县
				String pcs = getMap.get("pcs")==null?"":(String)getMap.get("pcs");	//派出所
				String zsxxdz = getMap.get("zsxxdz")==null?"":(String)getMap.get("zsxxdz");	//住宿详细地址
				String hzzlyzp = getMap.get("hzzlyzp")==null?"":(String)getMap.get("hzzlyzp");	//护照资料页照片

				conn = DBAdapter.getConnect("CRJWGR");
				conn.setAutoCommit(false);
				BASE64Decoder ba = new BASE64Decoder();
				byte[] byteimage = ba.decodeBuffer(hzzlyzp);
				bi = new ByteArrayInputStream(byteimage);


				String lsh  = "03"+DateUtil.DateToString8(new Date())+Tools.getSequence("SEQ_SJWGR");
				StringBuffer sql = new StringBuffer("insert into sj_wgr_zzxx(xxlsh,gjdq,zjhm,rzsj,nlkrq,jdrsfzhm,jdrsjhm,zsxzqh,zsxxdz,")
											.append("hzzlyzp,djsj,jhbj,jhbjgxsj,sjly,pcs)values(?,?,?,?,?,?,?,?,?,empty_blob(),?,'1',?,'4',?)");
				ps = conn.prepareStatement(sql.toString());
				ps.setString(1, lsh);
				ps.setString(2, gjdq);
				ps.setString(3, zjhm);
				ps.setString(4, rzsj);
				ps.setString(5, nlkrq);
				ps.setString(6, jdrsfzhm);
				ps.setString(7, jdrsjhm);
				ps.setString(8, zsxzqh);
				ps.setString(9, zsxxdz);
				ps.setString(10, DateUtil.current());
				ps.setString(11, DateUtil.current());
				ps.setString(12, pcs);
				
				ps.executeUpdate();

				StringBuffer sqlb = new StringBuffer("select hzzlyzp from sj_wgr_zzxx where xxlsh = ? for update");
				ps = conn.prepareStatement(sqlb.toString());
				ps.setString(1, lsh);
				rs = ps.executeQuery();
				if(rs.next())blob = (BLOB)rs.getBlob(1);

				int count = -1,total = 0;
				out = blob.getBinaryOutputStream();
				byte[] data = new byte[blob.getBufferSize()];
				while((count = bi.read(data))!=-1){
					total += count;
					out.write(data,0,count);
				}
				
				StringBuffer sqlC = new StringBuffer("update sj_wgr_zzxx set hzzlyzp = ? where xxlsh = ?");
				ps = conn.prepareStatement(sqlC.toString());
				ps.setBlob(1, blob);
				ps.setString(2, lsh);

				bi.close();
				out.close();				

				ps.executeUpdate();
				conn.commit();

				if(generateXML(lsh, hzzlyzp)){
					retMap.put("data", "登记成功！");
					retMap.put("type", "true");
				}else{
					retMap.put("data", "后台XML回执生成失败！");
					retMap.put("type", "error");					
				}
			}catch(JSONException e1){
				
				retMap.put("data", "传送数据有误，请联系管理员！");
				retMap.put("type", "false");
				e1.printStackTrace();
						
			}catch(Exception e){
				try{
					conn.rollback();
				}catch(SQLException ex){
					ex.printStackTrace();
					System.out.println("数据库回滚执行失败！");
				}
				retMap.put("data", "系统错误，请联系管理员！");
				retMap.put("type", "error");
				e.printStackTrace();
	
			}finally{
				DBAdapter.close(conn);
				DBAdapter.close(ps);
			}
		
		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}

	/**
	 * @author lidh
	 * 修改密码
	 * @param conJson
	 * @return
	 */
	public String updatePassword(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		List reList =  null; //查询结果转换的LIST集合
		Connection conn = null;
		PreparedStatement ps = null;

		
		
			String sjhm  = getMap.get("sjhm")==null?"":(String)getMap.get("sjhm"); //手机号码，即用户名
			String mm = getMap.get("newPwd")==null?"":(String)getMap.get("newPwd");	//新密码
			String code = getMap.get("code")==null?"":(String)getMap.get("code");	//手机验证码

			MD5 md = new MD5();
			mm = md.getMD5ofStr(mm);
			
			try{
				/*首先查询验证码是否匹配*/
				conn = DBAdapter.getConnect("CRJWGR");
				conn.setAutoCommit(false);
				StringBuffer codesql = new StringBuffer("select dxyzm from (select * from dxyzm_info t  where sjhm = ? ")
												.append("order by lsh desc)  where rownum = 1");
				ps = conn.prepareStatement(codesql.toString());
				
				ps.setString(1, sjhm);
				
				reList = getPreInfo(ps.executeQuery());
				Map ma = (Map)reList.get(0);
				
				if(code.equals(ma.get("DXYZM"))){
					/*如果验证码匹配，进行修改密码操作*/
					StringBuffer checksql = new StringBuffer("update register_user_info set mm = ? where sjhm = ?");
					ps = conn.prepareStatement(checksql.toString());
					
					ps.setString(1, mm);
					ps.setString(2, sjhm);
					ps.executeUpdate();
					conn.commit();

					retMap.put("data", "密码修改成功！");
					retMap.put("type", "true");
				
				}else{
					retMap.put("data", "验证码错误！");
					retMap.put("type", "false");				
				}
				

			}catch(JSONException e1){
				
				retMap.put("data", "传送数据有误，请联系管理员！");
				retMap.put("type", "false");
				e1.printStackTrace();
						
			}catch(Exception e){
				try{
					conn.rollback();
				}catch(SQLException ex){
					ex.printStackTrace();
					System.out.println("数据库回滚执行失败！");
				}
				retMap.put("data", "系统错误，请联系管理员！");
				retMap.put("type", "error");
				e.printStackTrace();
	
			}finally{
				DBAdapter.close(conn);
				DBAdapter.close(ps);
			}
		
		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}

	/**
	 * @author lidh
	 * 用户登录
	 * @param conJson
	 * @return
	 */
	public String login(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		List reList =  null; //查询结果转换的LIST集合
		Connection conn = null;
		PreparedStatement ps = null;

		
			try{
				
				String sjhm  = getMap.get("sjhm")==null?"":(String)getMap.get("sjhm"); //手机号码，即用户名
				String mm = getMap.get("mm")==null?"":(String)getMap.get("mm");	//密码
				
				MD5 md = new MD5();
				mm = md.getMD5ofStr(mm);

				conn = DBAdapter.getConnect("CRJWGR");
				StringBuffer checksql = new StringBuffer("select mm from register_user_info where sjhm = ?");
				ps = conn.prepareStatement(checksql.toString());
				
				ps.setString(1, sjhm);
				
				reList = getPreInfo(ps.executeQuery());
				/*如果List集合为空，表示没有这个人*/
				if(reList.size()==0){
					retMap.put("data", "该用户不存在！");
					retMap.put("type", "false");					
				}else{
					Map ma = (Map)reList.get(0);
					if(mm.equals(ma.get("MM"))){
						retMap.put("data", "验证成功，欢迎登陆！");
						retMap.put("type", "true");											
					}else{
						retMap.put("data", "密码错误！请重试！");
						retMap.put("type", "false");																	
					}
				}


			}catch(JSONException e1){
						
				retMap.put("data", "传送数据有误，请联系管理员！");
				retMap.put("type", "false");
				e1.printStackTrace();
						
			}catch(Exception e){
				
				retMap.put("data", "系统错误，请联系管理员！");
				retMap.put("type", "error");
				e.printStackTrace();
			}finally{
				DBAdapter.close(conn);
				DBAdapter.close(ps);
			}
		
		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}
	/**
	 * @author lidh
	 * 获取下拉框信息
	 * @param conJson
	 * @return
	 */
	public String getGjdq(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		Connection conn = null;
		PreparedStatement ps = null;
		
			
			try{
				
				String type  = getMap.get("type")==null?"":(String)getMap.get("type"); //类型，1表示请求国家信息；2表示请求区县信息

				conn = DBAdapter.getConnect("CRJWGR");
				
				if("1".equals(type)){
					type = "国家地区";
				}else if("2".equals(type)){
					type = "4503行政区划";
				}else{
					type += "警署";					
				}
				StringBuffer sql = new StringBuffer("select dmz,dmmc from system_code where dmlb = '"+type+"' ")
											.append("and dmz !='CHN' order by dmz asc");
				
				ps = conn.prepareStatement(sql.toString());
							
				List reList = getPreInfo(ps.executeQuery());	
				if(reList.size()>0){
					Map reMap = new HashMap();
					
					for(int i = 0;i<reList.size();i++){
						getMap = (Map)reList.get(i);
						reMap.put(getMap.get("DMZ"), getMap.get("DMMC"));
					}
					retMap.put("data", reMap);
					retMap.put("type", "true");
				}else{
					retMap.put("data", "数据获取失败！");
					retMap.put("type", "error");					
				}
						
			}catch(JSONException e1){
						
				retMap.put("data", "传送数据有误，请联系管理员！");
				retMap.put("type", "false");
				e1.printStackTrace();
						
			}catch(Exception e){
						
				retMap.put("data", "系统错误，请联系管理员！");
				retMap.put("type", "error");
				e.printStackTrace();
						
			}finally{
				DBAdapter.close(conn);
				DBAdapter.close(ps);
			}

		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}

	/**
	 * @author lidh
	 * 获取短信验证码
	 * @param conJson
	 * @return
	 */
	public String getYzm(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		String flag = "1";      //表示是否进行验证码获取操作
		Connection conn = null;
		PreparedStatement ps = null;
		String tszl = "";
		String fslx = "";
				try{
					
					String sjhm  = getMap.get("sjhm")==null?"":(String)getMap.get("sjhm"); //手机号码，即用户名
					String type  = getMap.get("type")==null?"":(String)getMap.get("type"); //类型，1表示注册验证码，2表示修改验证码,3表示售票动态密码

					
					Integer yzm = StringUtil.getRandom(); //获取随机六位数字
					conn = DBAdapter.getConnect("CRJWGR");
					conn.setAutoCommit(false);
					
					/*如果是修改密码请求的验证码，则查询库中是否有此手机号码*/
					if("2".equals(type)){
						StringBuffer checksql = new StringBuffer("select * from register_user_info where sjhm = ?");
						ps = conn.prepareStatement(checksql.toString());
						
						ps.setString(1, sjhm);
						
						List reList = getPreInfo(ps.executeQuery());	
						/*如果LIST集合长度为0，则表示没有这个人*/
						if(reList.size()==0){
							retMap.put("data", "该手机号码未注册！");
							retMap.put("type", "false");
							flag = "0";
						}
					}
					if(!"3".equals(type)){
						tszl = "验证码";
						fslx = "0";						
					}else{
						tszl = "动态密码";
						fslx = "1";						
					}
					if("1".equals(flag)){
						//首先删除验证码表中请求用户的验证码
						StringBuffer delsql = new StringBuffer("delete from dxyzm_info where sjhm = ? and fssj < ?");
						ps = conn.prepareStatement(delsql.toString());
						
						ps.setString(1,sjhm);
						ps.setString(2, DateUtil.DateToString14(new Date()));
					
						ps.executeUpdate();

						//之后删除短信猫表中的验证码
						StringBuffer delsql2 = new StringBuffer("delete from message_mobile where xxid = ? and sjhm = ? and cssj<?");
						ps = conn.prepareStatement(delsql2.toString());
						
						ps.setString(1,"yzm");
						ps.setString(2, sjhm);
						ps.setString(3, DateUtil.DateToString14(new Date()));
						
						ps.executeUpdate();

						//请求最新验证码
						StringBuffer sql = new StringBuffer("insert into dxyzm_info(lsh,sjhm,xh,dxyzm,fssj,fslx)values(?,?,?,?,?,?)");
						ps = conn.prepareStatement(sql.toString());
						
						ps.setString(1, DateUtil.DateToString8(new Date()).substring(2,8)+Tools.getSequence("SEQ_SJDXYZM"));
						ps.setString(2, sjhm);
						ps.setString(3, Tools.getSequence("SEQ_SJDXYZMXH"));
						ps.setInt(4, yzm);
						ps.setString(5, DateUtil.DateToString14(new Date()));
						ps.setString(6, fslx);
						
						ps.executeUpdate();

						

						StringBuffer sql2 = new StringBuffer("insert into message_mobile(id,xxid,xxnr,cssj,sjhm,jb,fszt,fslx)values(?,?,?,?,?,?,?,?)");
						ps = conn.prepareStatement(sql2.toString());
							
						ps.setString(1, Tools.getSequence("SEQ_MSG_MOBILE"));
						ps.setString(2, "yzm");
						ps.setString(3, "您的"+tszl+"为:"+yzm+",请勿告诉他人！");
						ps.setString(4, DateUtil.DateToString14(new Date()));
						ps.setString(5, sjhm);
						ps.setString(6, "1");
						ps.setString(7, "0");
						ps.setString(8, fslx);
							
						ps.executeUpdate();



						conn.commit();
						
						retMap.put("data", "获取验证码成功！");
						retMap.put("type", "true");
					}
				}catch(JSONException e1){				
					retMap.put("data", "传送数据有误，请联系管理员！");
					retMap.put("type", "false");
					e1.printStackTrace();				
				}catch(Exception e){
					
					retMap.put("data", "系统错误，请联系管理员！");
					retMap.put("type", "error");
					e.printStackTrace();
					
				}finally{
					DBAdapter.close(conn);
					DBAdapter.close(ps);
				}
		
		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}

	/**
	 * @author lidh
	 * 生成XML文件
	 * @param rs
	 * @return
	 */

	public Boolean generateXML(String lsh,String hzzlyzp){

		Connection conn = null;
		PreparedStatement ps = null;
		String XMLStr= "<?xml version=\"1.0\" encoding=\"utf-8\"?><ROOT><DATAS><DATA>";
		PrintStream pstream = null;
		try{
			conn = DBAdapter.getConnect("CRJWGR");
			conn.setAutoCommit(false);
			
			//首先更新交换时间
			StringBuffer updsql = new StringBuffer("update sj_wgr_zzxx set jhbj = '0',jhbjgxsj= ? where xxlsh = ?");
			
			ps = conn.prepareStatement(updsql.toString());
			
			ps.setString(1, DateUtil.current());
			ps.setString(2, lsh);

			ps.executeUpdate();
			conn.commit();

			
			//查找要赋值到XML的信息
			StringBuffer sql = new StringBuffer("select xxlsh,ywxm,zwxm,xb,csrq,gjdq,zjhm,zjyxq,rzsj,nlkrq,jdrsfzhm,")
											 .append("jdrsjhm,zsxzqh,zsxxdz,djsj,jhbj,jhbjgxsj,sjly,pcs from sj_wgr_zzxx where xxlsh = ?");
			ps = conn.prepareStatement(sql.toString());
			
			ps.setString(1, lsh);
			
			List reList = getPreInfo(ps.executeQuery());	
			Map map = (Map)reList.get(0); //获取流水号的MAP集合
			Iterator it = map.entrySet().iterator();
			while(it.hasNext()){
				java.util.Map.Entry en = (java.util.Map.Entry)it.next();
				XMLStr += "<"+en.getKey()+">"+en.getValue()+"</"+en.getKey()+">";
			}
			XMLStr += "<HZZLYZP>"+hzzlyzp+"</HZZLYZP></DATA></DATAS></ROOT>";
			String filename = "SJZSXX_"+lsh+"_"+DateUtil.current();
		   	File file = new File("C:\\xml\\");
	    	file.mkdir();
	    	pstream = new PrintStream(new FileOutputStream("C:\\xml\\"+filename+".xml"));
	    	pstream.write(XMLStr.toString().getBytes("UTF-8"));
	    	pstream.flush();

	    	return true;
		}catch(Exception e){
			try{
				conn.rollback();
			}catch(SQLException ex){
				ex.printStackTrace();
				System.out.println("数据库回滚执行失败！");
			}
			return false;
		}finally{
			DBAdapter.close(conn);
			DBAdapter.close(ps);
			pstream.close();
		}
	}

	/**
	 * @author lidh
	 * 结果集转换成LIST集合
	 * @param rs
	 * @return
	 */

	public List getPreInfo(ResultSet rs) throws java.sql.SQLException{
		if(rs == null)return Collections.EMPTY_LIST;
		ResultSetMetaData md =rs.getMetaData();
		int colunmnCount = md.getColumnCount();
		List list = new ArrayList();
		Map rowData = new HashMap();
		while(rs.next()){
			rowData = new HashMap(colunmnCount);
			for(int i = 1;i<=colunmnCount;i++){
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		return list;
	}

	/**
	 * @author lidh
	 * 用户登录
	 * @param conJson
	 * @return
	 */
	public String spLogin(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		List reList =  null; //查询结果转换的LIST集合
		Connection conn = null;
		PreparedStatement ps = null;

		
			try{
				
				String yhm  = getMap.get("yhm")==null?"":(String)getMap.get("yhm"); //即手机号码
				String mm = getMap.get("mm")==null?"":(String)getMap.get("mm");	//密码
				

				conn = DBAdapter.getConnect("CRJWGR");
				StringBuffer checksql = new StringBuffer("select sfsy,dxyzm from dxyzm_info where sjhm = ? and fslx = '1' ")
												 .append(" and exists(select xxid from message_mobile where  sjhm = ? and fslx = '1' and fszt = '1')");
				ps = conn.prepareStatement(checksql.toString());
				
				ps.setString(1, yhm);
				ps.setString(2, yhm);
				
				reList = getPreInfo(ps.executeQuery());
				/*如果List集合为空，表示没有这个人*/
				if(reList.size()==0){
					retMap.put("data", "该手机号码没有对应的信息，请输入手机号后点击获取动态密码！");
					retMap.put("type", "false");					
				}else{
					Map ma = (Map)reList.get(0);
					if(mm.equals(ma.get("DXYZM"))){
						if("1".equals(ma.get("SFSY"))){
							retMap.put("data", "动态密码已过期！请重新获取");
							retMap.put("type", "false");																													
						}else{
							retMap.put("data", "验证成功，欢迎登陆！");
							retMap.put("type", "true");			
							//成功后置动态密码使用状态为1
							checksql = new StringBuffer("update dxyzm_info set sfsy = '1' where sjhm = ? and fslx = '1'");
							ps = conn.prepareStatement(checksql.toString());
							ps.setString(1, yhm);
							ps.executeUpdate();
							conn.commit();
						}
					}else{
						retMap.put("data", "密码错误！请重试！");
						retMap.put("type", "false");																							
					}
				}


			}catch(JSONException e1){
						
				retMap.put("data", "传送数据有误，请联系管理员！");
				retMap.put("type", "false");
				e1.printStackTrace();
						
			}catch(Exception e){
				
				retMap.put("data", "系统错误，请联系管理员！");
				retMap.put("type", "error");
				e.printStackTrace();
			}finally{
				DBAdapter.close(conn);
				DBAdapter.close(ps);
			}
		
		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}

	@Override
	public String checkZjhm(String conJson) {
		
		Map getMap = JSONObject.fromObject(conJson); //将接收的JSON字符串转成MAP集合格式
		Map retMap = new HashMap(); //返回值的MAP集合
		String reJson = "";  		//最终返回的JSON字符串
		Connection conn = null;
		PreparedStatement ps = null;

		
			try{
				
				String zjhm = getMap.get("zjhm").toString().toUpperCase();
				conn = DBAdapter.getConnect("CRJWGR");

				StringBuffer sql = new StringBuffer("select rylx from MQRY_INFO where zjhm = ? ");
				ps = conn.prepareStatement(sql.toString());
				ps.setString(1, zjhm);
				
				List list = getPreInfo(ps.executeQuery());
				//
				if(list.size()>0){
					String rylx = ((Map)(list.get(0))).get("RYLX").toString();
					if("1".equals(rylx)){
						retMap.put("data", "此人属于72小时免签人员，建议不对其进行售票！");
						retMap.put("type", "false");											
					}else{
						retMap.put("data", "此人属于144免签团成员，建议不对其进行售票！");
						retMap.put("type", "false");											
					}
					
				}else{
					retMap.put("data", "此人不属于在控免签人员，可以对其进行售票！");
					retMap.put("type", "true");					
				}

				

			}catch(JSONException e1){
						
				retMap.put("data", "传送数据有误，请联系管理员！");
				retMap.put("type", "false");
				e1.printStackTrace();
						
			}catch(Exception e){
				
				retMap.put("data", "系统错误，请联系管理员！");
				retMap.put("type", "error");
				e.printStackTrace();
			}finally{
				DBAdapter.close(conn);
				DBAdapter.close(ps);
			}
		
		reJson = JsonUtil.map2json(retMap);	

		return reJson;
	}
	
}
