package com.shdic.szhg.newServicesImpl;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shdic.szhg.init.Constants;
import com.shdic.szhg.init.DBAdapter;
import com.shdic.szhg.newServices.WeChatService;
import com.shdic.szhg.util.DateUtil;
import com.shdic.szhg.util.Tools;



public class WeChatServiceImpl implements WeChatService {
	static Logger logger = Logger.getLogger(WeChatServiceImpl.class);


	@Override
	public String getContentById(String ID, String openid, String ip) {
		logger.info("参数====syresource_id："+ID+";openid:"+openid+";ip:"+ip);
		String reXmlString = ""; // 最终返回的JSON字符串
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; // 数据集
		try {
			conn = DBAdapter.getConnect(Constants.CRJWGR);
			String sql="select content_title title,content_fbuser fbuser,to_char(to_date(content_fbtime,'yyyymmddHH24miss'),'yyyy-mm-dd HH24:mi:ss') fbtime,content_body clob_content from content t where content_id =?";
			logger.info("获取发布内容 Sql:"+sql.toString());
			ps=conn.prepareStatement(sql);

			ps.setString(1, ID);
			rs = ps.executeQuery();
			// 将数据集合查询到的数据转成XML字符串返回sssssss
			reXmlString = Tools.getResultSetToJsonString(rs);

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();

		} finally {
			DBAdapter.close(rs);
			DBAdapter.close(ps);
			DBAdapter.close(conn);
		}
		logger.info("返回结果reXmlString："+reXmlString);
		return reXmlString;
	}

	// 接口3：获取外国人服务类别

	@Override
	public String getWgrfwlx(String syresource_id, String openid, String ip) {

		logger.info("参数====syresource_id：" + syresource_id + ";openid:"
				+ openid + ";ip:" + ip);

		String reXmlString = ""; // 最终返回的JSON字符串
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null; // 数据集
		try {
			conn = DBAdapter.getConnect(Constants.CRJWGR);
			StringBuffer codesql = new StringBuffer(
					"select id,name  from SYRESOURCE t where SYRESOURCE_ID=? order by seq");
			logger.info("获取外国人服务类别 Sql:" + codesql.toString());

			ps = conn.prepareStatement(codesql.toString());
			ps.setString(1, syresource_id);
			rs = ps.executeQuery();

			// 将数据集合查询到的数据转成XML字符串返回
			reXmlString = Tools.getResultSetToJsonString(rs);

		} catch (Exception e) {
			logger.error(e.getMessage());
			Map returnJson = new HashMap();
			returnJson.put("flag", 0);
			returnJson.put("errorMsg", "查询失败");
			reXmlString = JSON.toJSONString(returnJson);
			e.printStackTrace();
		} finally {
			DBAdapter.close(rs);
			DBAdapter.close(ps);
			DBAdapter.close(conn);
		}

		logger.info("返回结果reXmlString：" + reXmlString);

		return reXmlString;
	}

	// 接口4：获取XXXX公告目录
	@Override
	public String getGglist(String CONTENT_ID, String openid, String ip) {

		logger.info("参数====CONTENT_ID：" + CONTENT_ID + ";openid:" + openid
				+ ";ip:" + ip);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String reXmlString = ""; // 最终返回的JSON字符串

		try {
			conn = DBAdapter.getConnect(Constants.CRJWGR);
			String sql = "select content_id id,content_title title,to_char(to_date(substr(content_fbtime,0,8),'yyyy-mm-dd'),'yyyy-mm-dd') fbtime from content t where content_yxzt='1' and content_type=? order by content_fbtime desc";

			logger.info("获取XXXX公告目录 Sql:" + sql);

			ps = conn.prepareStatement(sql);
			ps.setString(1, CONTENT_ID);
			rs = ps.executeQuery();
			reXmlString = Tools.getResultSetToJsonString(rs);
		} catch (Exception e) {
			logger.error(e.getMessage());
			Map returnJson = new HashMap();
			returnJson.put("flag", 0);
			returnJson.put("errorMsg", "查询失败");
			reXmlString = JSON.toJSONString(returnJson);
			e.printStackTrace();
		} finally {
			DBAdapter.close(rs);
			DBAdapter.close(ps);
			DBAdapter.close(conn);
		}

		logger.info("返回结果reXmlString：" + reXmlString);

		return reXmlString;
	}

	@Override
	public String getSelfDictionCfg(String dj, String subnode_id, String openid, String ip) {
		logger.info("接收到的参数是：" + "dj:" + dj + "subnode: " + subnode_id
				 + "openid:" + openid + "ip:" + ip);
		System.out.println("接收到的参数是：" + "dj:" + dj + "subnode: " + subnode_id
				+ "openid:" + openid + "ip:" + ip);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String reXmlString = "";
		
		String sql="";
		
		
		
		try {
			conn = DBAdapter.getConnect(Constants.CRJWGR);
			if("up".equals(subnode_id)){
				sql="select cxtj,dj,param1,(select dj from(select dj from self_consult_log t where t.openid = '"+openid+"' order by cxsj desc) where rownum = 1) maxdj  from self_consult_log where openid='"+openid+"' and dj='"+dj+"' order by cxsj desc";
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				String cxtj="";
				String param1="";
				String maxdj="";
				String dj_log="";
				if(Integer.valueOf(dj)<=0){
					Map map = new HashMap();
					map.put("flag", "0");
					map.put("message", "参数等级有误！");
					return JSON.toJSONString(map);
				}
				if(rs.next()){
					cxtj=rs.getString("CXTJ");
					param1=rs.getString("PARAM1");
					maxdj=rs.getString("MAXDJ");
					dj_log=rs.getString("DJ");
					
					//返回上一级  判断等级数字是否正确
					if(!maxdj.equals((Integer.valueOf(dj)+1)+"")){
						Map map = new HashMap();
						map.put("flag", "0");
						map.put("message", "参数等级有误！");
						
						return JSON.toJSONString(map);
					}
				}else{
					Map map = new HashMap();
					map.put("flag", "0");
					map.put("message", "没有上一级目录！");
					
					return JSON.toJSONString(map);
					
				}
				sql=cxtj;
				System.out.println("----------查询sql----------："+sql);
				ps = conn.prepareStatement(sql);
				if("1".equals(dj_log)){
					ps.setString(1, dj_log);
				}
				if (!"1".equals(dj_log)) { // 表示的是如果输入的参数不等于零，在设置参数的时候按的是子节点查询
					ps.setString(1, dj_log);
					ps.setString(2, param1);
				}
			}else{
				sql = "Select node_id, dysz,node_mc,subnode_id,dj from self_consult_cfg t where dj=?+1 and sfky=1 ";
				logger.info("子节点查询sql:" + sql); 
				if (!"0".equals(dj)) { // 如果不为零，执行的是子节点查询语句
					sql +=" and instr(?,node_id)>0  ";
				}
				sql +=" order by dysz";
				ps = conn.prepareStatement(sql);
				if("0".equals(dj)){
					ps.setString(1, dj);
				}
				if (!"0".equals(dj)) { // 表示的是如果输入的参数不等于零，在设置参数的时候按的是子节点查询
					ps.setString(1, dj);
					ps.setString(2, subnode_id);
				}
				
			}
			rs = ps.executeQuery();
			if (rs.isAfterLast() == rs.isBeforeFirst()) { // 表示的是如果结果集为空，执行的sql语句是字典信息表
				// if (!rs.next()) {
				String atomsql = "select yzmc node_mc from atom_dictionary_info t where sfky = '1' and  instr(?,yzid)>0 order by xh";
				logger.info("atom_dictionary_info sql:" + atomsql); 
				
				ps = conn.prepareStatement(atomsql);
				ps.setString(1, subnode_id);
				rs = ps.executeQuery();
				reXmlString = Tools.getResultSetToJsonString(rs); // 将结果集转换为字符串的形式
			} else {

				reXmlString = Tools.getResultSetToJsonString(rs);
			}
			
			
			ps = conn.prepareStatement("delete self_consult_log where openid ='"+openid+"' and dj>='"+dj+"' ");
			ps.execute();
			
			
			//插入日志
			ps = conn.prepareStatement("insert into self_consult_log (openid, dj, cxtj, cxjg, cxsj,param1) values ('"+openid+"', '"+dj+"',? , ?, to_char(sysdate,'yyyyMMddHHmiss'),?)");
			ps.setString(1, sql);
			
			if (reXmlString.toString().getBytes().length > 666)
		        ps.setCharacterStream(2, new StringReader(reXmlString.toString()), reXmlString.toString().getBytes().length);
		      else {
		        ps.setString(2, reXmlString.toString());
		      }
			//ps.setString(2, reXmlString);
			ps.setString(3, subnode_id);
			
			
			ps.execute();
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			System.out.println(e.getMessage());
			Map map = new HashMap();
			map.put("flag", "0");
			map.put("errorMaessage", "查询中出错");
			reXmlString = JSON.toJSONString(map); // 将集合转换成json字符串
		} finally {
			DBAdapter.close(rs);
			DBAdapter.close(ps);
			DBAdapter.close(conn);
		}
		logger.info("返回的reXmlString：" + reXmlString);
		return reXmlString;
	}
	
	
	@Override
	public String getPdCount(String sld, String openid, String ip) {
		logger.info("接收到的参数分别是：" + "sld:" + sld + "openid:" + openid + "ip:"+ ip);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String reXmlString = "";
		String sid_temp="";
		if(!"".equals(sld)){
			if(sld.length()>4){
				sid_temp=sld;
				sld=sld.substring(0,4);
				
			}else if(sld.length()==4){
				sid_temp=sld+"00";
			}else{
				Map map = new HashMap();
				map.put("flag", "0");
				map.put("message", "传参不合法！");
				return  JSON.toJSONString(map);
				 
			}
		}else{
			Map map = new HashMap();
			map.put("flag", "0");
			map.put("message", "传参不合法！");
			return  JSON.toJSONString(map);
		}
		StringBuffer sql = new StringBuffer("select sfgzr from workday_info t where rq=to_char(sysdate,'yyyymmdd')"); // sql的作用查看是否是工作日
		logger.info("查看是否为工作日的sql：" + sql);
		try {
			conn = DBAdapter.getConnect(Constants.CRJWGR);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			if (rs.next()) {
				int num = rs.getInt(1);
				sql.delete(0, sql.length());
				sql.append("select (select dmmc from system_code where dmlb = '行政区划' and dmz = ?) fsldmc, ")
			       .append("s.dmz sld, s.dmmc sldmc, ")
			       .append("(select dmmc from system_code where dmlb = '受理点地址' and dmz = s.dmz) addr, ")
			       .append("to_char(nvl(to_date(GXSJ, 'yyyymmddHH24miss'),sysdate), 'yyyy-mm-dd HH24:mi:ss') updatetime, ")
			       .append("(select dmmc from system_code where dmlb = '受理点电话' and dmz = s.dmz) tel, ");
				if (num == 0) { // 为零表示的不是工作日
					sql.append("(select dmz from system_code where dmlb = '大厅排队状态9') now, (select dmmc from system_code where dmlb = '大厅排队状态9') status ");
					logger.info("非工作日查询");
				}else { // 表示的为工作日要执行的操作
					sql.append("decode(d.sll,null, (select dmz from system_code where dmlb = '大厅排队状态0'),'目前排队【'||d.sll||'】人' ) now,")
				       .append("(case when d.sll is null then (select dmmc from system_code where dmlb = '大厅排队状态0') ")
				       .append("when to_number(d.sll) <= (select dmz from system_code where dmlb = '大厅排队状态1') then ")
				       .append("(select dmmc from system_code where dmlb = '大厅排队状态1') ")
				       .append("when to_number(d.sll) <= (select substr(dmz,4,2) from system_code where dmlb = '大厅排队状态2') and to_number(d.sll) >(select substr(dmz,1,2) from system_code where dmlb = '大厅排队状态2') then ")
				       .append("(select dmmc from system_code where dmlb = '大厅排队状态2') ")
				       .append("when to_number(d.sll) <= (select substr(dmz,4,2) from system_code where dmlb = '大厅排队状态3') and to_number(d.sll) >(select substr(dmz,1,2) from system_code where dmlb = '大厅排队状态3') then ")
				       .append("(select dmmc from system_code where dmlb = '大厅排队状态3') ")
				       .append("when to_number(d.sll) > (select dmz from system_code where dmlb = '大厅排队状态4') then ")
				       .append("(select dmmc from system_code where dmlb = '大厅排队状态4') end) status ");
					
				     
					logger.info("工作日查询");
				}
				 sql.append("from system_code s, ")
			       .append("(select x.id, x.sld, x.slrq, x.sll, x.gxsj ")
			       .append("from DT_SLL_INFO x, (select sld, slrq, max(id) id from DT_SLL_INFO t group by sld, slrq order by sld) f ")
			       .append("where x.id = f.id and substr(x.sld, 0, 4) = ?  and x.slrq = to_char(sysdate, 'yyyymmdd') ) d ")
			       .append("where s.dmz = d.sld(+) and s.dmlb = '受理点' order by s.dmz");
				 logger.info("查询sql：" + sql);
				 logger.info("?[1:"+sid_temp+",2:"+sld+"]");
				ps = conn.prepareStatement(sql.toString());
				ps.setString(1, sid_temp);
				ps.setString(2, sld);
				rs = ps.executeQuery();
				List list=new ArrayList();
				Map map =null;
				int index=-1;
				String status_color="";
				String color="";
				String status="";
				 while (rs.next()){
					 
					 Map obj =new HashMap();
					 obj.put("sld", rs.getString("sld")==null?"":rs.getString("sld"));
					 obj.put("sldmc", rs.getString("sldmc")==null?"":rs.getString("sldmc"));
					 obj.put("now", rs.getString("now")==null?"":rs.getString("now"));
					 status_color=rs.getString("status")==null?"":rs.getString("status");
					 index=status_color.indexOf("@@");
					 status=status_color.substring(0, index);
					 color=status_color.substring(index+2);
					 if(!"".equals(status)&&!"".equals(color)){
						 obj.put("color", color);
						 obj.put("status", status);
					 }else{
						 obj.put("color", "");
						 obj.put("status", "");
					 }
					 
					 obj.put("addr", rs.getString("addr")==null?"":rs.getString("addr"));
					 obj.put("tel", rs.getString("tel")==null?"":rs.getString("tel"));
					
					 if(map==null){
						 map=new HashMap();
						 map.put("sld", sld);
						 map.put("updatetime", rs.getString("updatetime")==null?"":rs.getString("updatetime"));
						 map.put("mc", rs.getString("fsldmc")==null?"":rs.getString("fsldmc"));
						 
					 }
					 list.add(obj);
				};
				map.put("data", list);
				reXmlString=JSON.toJSONString(map);
			}
		}  catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			Map map = new HashMap();
			map.put("flag", "0");
			map.put("message", "查询中出错");
			reXmlString = JSON.toJSONString(map);
		} finally {
			DBAdapter.close(rs);
			DBAdapter.close(ps);
			DBAdapter.close(conn);
		}

		return reXmlString;
	}
	

	
	
	@Override
	public String getIsWorkDay(){
		
		//返回对象
		String reJSONString="";
		Map temp=null;
		Map map = new HashMap();
		try {
			Date nowDate=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmm");
			String year=sdf.format(nowDate).substring(0,8);
			
			Object data = Tools.execJdbc(Constants.CRJWGR, "obj", "select sfgzr from workday_info where rq='" + year + "'", null);
			temp=data!=null?(Map)data:null;
			if(temp!=null){
				logger.info("今天是工作日 rq:"+year);
				String sfgzr=(String)temp.get("sfgzr");
				if("1".equals(sfgzr)){
					
					String rq_code_sql="select dmz from system_code t where dmlb='受理点人工工作时间' and dmmc='冬季工作时间'";
					int month=Integer.valueOf(year.substring(4, 6));
					if(month>=5 && month<=10){
						rq_code_sql="select dmz from system_code t where dmlb='受理点人工工作时间' and dmmc='夏季工作时间'";
					}
					String rq_code = (String)Tools.execJdbc(Constants.CRJWGR,Tools.TYPE_STRING,rq_code_sql,null);
					String[] rqs=rq_code.split("@")[0].split("-");
					Date rqssd= sdf.parse(year+rqs[0]);
					Date rqsed= sdf.parse(year+rqs[1]);
					
					logger.info("受理点客服工作时间段 :"+rqs);
					if(rqssd.before(nowDate)&&rqsed.after(nowDate)){
						logger.info("受理点客服正在工作");
						map.put("state", "yes");
					}else {
						String[] rqx=rq_code.split("@")[1].split("-");
						Date rqxsd= sdf.parse(year+rqx[0]);
						Date rqxed= sdf.parse(year+rqx[1]);
						if(rqxsd.before(nowDate)&&rqxed.after(nowDate)){
							logger.info("受理点客服正在工作");
							map.put("state", "yes");
						}else{
							logger.info("受理点客服休息中");
							map.put("state", "no");
						}
					}
					map.put("flag", "1");
					map.put("work_time",rq_code);
					map.put("message", "");
				}else{
					logger.info("今天是工作日 rq:"+year);
					map.put("flag", "1");
					map.put("state", "no");
					map.put("message", "");
				}
			}else{
				map.put("flag", "0");
				map.put("message", "日期有误！");
			}
			
		} catch (Exception e) {
			map.put("flag", "0");
			map.put("state", "error");
			map.put("message", "查询中出错");
			reJSONString = JSON.toJSONString(map);
			e.printStackTrace();
			
		}
		this.logger.info("返回值："+JSON.toJSONString(map));
		reJSONString = JSON.toJSONString(map);
		return reJSONString;
		
	}
	

	/**
	 * 
	 * @Description: 再次签和新办证数据下载
	 * @param @return
	 * @return String JSON字符串
	 * @throws
	 *
	 * @author lisw
	 * @date 2016-12-22
	 */
	@Override
	public  String getDownloadData(String username, String pwd, String type) {
		String date=DateUtil.current();//获取系统的当前时间格式为yyyyMMddHHmmss
		Connection conn = null;
		Statement sta=null;
		String querySql="";//查询需要下载的数据的sql语句
		String gxsj="";//查询广西公安上次下载的时间
		List flowinfoList=new ArrayList();//本次需要同步的数据
		Map returnMap=new HashMap();
		Map message=new HashMap();
		returnMap.put("info", flowinfoList);
		returnMap.put("message", message);
		String rzSql=""; //记录日志的sql语句
		String dmz = "";
		if(!"glsj-certi".equals(username)||!"glsj-certi".equals(pwd)){
			message.put("code", "0");
			message.put("description", "用户名或密码错误");
			returnMap.put("info", new ArrayList());
			
		} else {
			try {
				String id = date+Tools.getSequence("SEQ_PU_DOW_LOG");
				try{
					rzSql = "insert into public_download_log(id,xzsj,sjlx,xzzt,error,xznr,xzts) values('"+id+"',?,?,?,?,empty_clob(),?)";
					if("xbz".equals(type)||"zcq".equals(type)){
						if("zcq".equals(type)){
							dmz = "zcq";
							//查询再次签上一次的同步时间
							String gxgasql = "select dmmc from system_code where dmlb='广西公安数据抽取时间' and dmz = 'zcq'";
							String gxgajhsj = (String)Tools.execJdbc("CRJWGR",Tools.TYPE_STRING, gxgasql,new String[]{});
							//将在此时间段的数据查询出来
							querySql = "select ywbh,ywlx,sfzhm,sqrq,zdhcjg spjg,lczt,spbz,decode(openid,null,'1','3') sjly,bzlx,jhbjgxsj from flow_info_public  where sjly = 'W' and CLDW='450300' ";
							if(!"".equals(gxgajhsj)){
								querySql += " and JHBJGXSJ > '"+gxgajhsj+"'";
							}
							
						} else if("xbz".equals(type)) {
								dmz = "xbzyy";
								//查询新办证上一次的同步时间
								String gxgasql = "select dmmc from system_code where dmlb='广西公安数据抽取时间' and dmz = 'xbzyy'";
								String gxgajhsj = (String)Tools.execJdbc("CRJWGR",Tools.TYPE_STRING, gxgasql,new String[]{});
								//将在此时间段的数据查询出来
								querySql = "select xxlsh,ywlx,sfzhm,sqrq,spjg,yxzt,spbz,decode(openid,null,'1','3') sjly,bzlx,jhbjgxsj from prepare_leave_country_apply where (sjly = '1' or sjly = '3') and CLDW='450300' ";
								if(!"".equals(gxgajhsj)){
									querySql += " and JHBJGXSJ > '"+gxgajhsj+"'";
								}
								
						}
						querySql += " order by jhbjgxsj asc";
						flowinfoList = (List)Tools.execJdbc("CRJWGR",Tools.TYPE_LIST, querySql,new String[]{});
						//将本次数据同步的时间查询出来放到数据库中
						if(flowinfoList!=null){
							gxsj = (String)((Map)flowinfoList.get(flowinfoList.size()-1)).get("jhbjgxsj");
							//将最后一条数据的时间放到数据库中作为下一次下载外网数据时的标示
							String upSql = "update system_code set dmmc='"+gxsj+"' where dmlb='广西公安数据抽取时间' and dmz = '"+dmz+"'";
							conn = DBAdapter.getConnect("CRJWGR");
							conn.setAutoCommit(false);
							sta=conn.createStatement();
							sta.executeUpdate(upSql);
							conn.commit();
						}
						message.put("code", "1");
						message.put("description", "成功下载！");
						flowinfoList=flowinfoList==null?new ArrayList():flowinfoList;
						String xznr = JSON.toJSONString(flowinfoList,SerializerFeature.WriteNullStringAsEmpty);
						Integer xzts = flowinfoList.size();
						Tools.execJdbc("CRJWGR",Tools.TYPE_UPDATE, rzSql,new String[]{date,type,"0","",xzts.toString()});
						String clobSql = "select xznr from public_download_log where id ='"+id+"' for update";
						Tools.insClob(DBAdapter.getConnect("CRJWGR"), xznr, clobSql);
						returnMap.put("info", flowinfoList);
					}
				}catch (Exception e) {
					message.put("code", "0");
					message.put("description", "下载失败："+e.getMessage());
					returnMap.put("info", new ArrayList());
					if(conn!=null){
						try {
							conn.rollback();
						} catch (SQLException e1) {
							message.put("description", "下载失败："+e.getMessage()+"；"+e1.getMessage());
							e1.printStackTrace();
						}
					}
					try {
						Tools.execJdbc("CRJWGR",Tools.TYPE_UPDATE, rzSql, new String[]{date,type,"1",e.getMessage()==null?"空指针异常":e.getMessage(),"0"});
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}finally{
					DBAdapter.close(sta);
					DBAdapter.close(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		String reStr= JSON.toJSONString(returnMap,SerializerFeature.WriteNullStringAsEmpty);
		return reStr;
	}
}

