package com.shdic.szhg.time;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import com.gxcrj.www.services.WeixinService.CrjWeixinGX;
import com.gxcrj.www.services.WeixinService.CrjWeixinGXServiceLocator;
import com.shdic.szhg.init.DBAdapter;
import com.shdic.szhg.util.Tools;
import com.shdic.szhg.util.XMLToMap;

public class QtSjcq {


	
	public void ycjSjtb(){
		Connection conn = null;
		Statement sta=null;
		//读取数据库中最新更新的数据交换时间
		String[] zds=new String[]{"xxlsh","ywlx","bzlx","zjhm","zjyxq","zwx","zwm","ywx","ywm","zwxm","xb","sfzhm","zzmm","whcd","gzdw","sjhm","yzbm","dwdh","brsf","zjzc","xzz","hkszdz","spjg","jhbj","sqrq","ywbh","hyzk","qwgj","cjsy","qzdz","zjqfd","yxzt","spbz","mz","csrq","csd","jtdh","gzdwdz","jhbjgxsj","hkpcs","hkxzqh","sjly","qzfs","emslxdh","emsxzz","emscode","emssjr","brjl","dbdw","dbr","rydylb","yjjzd","badwmc","tdbh","cjka","jjlxrxm","jjlxrdz","jjlxrdh","cldw","openid"};
		String sql = "select max(Jhbjgxsj) jhsj from prepare_leave_country_apply where (sjly = '1' or sjly = '3') and CLDW='450300' ";
		try {
			//将上一次同步的数据查询出来
			String jhsj = (String)Tools.execJdbc("CRJWGR",Tools.TYPE_STRING, sql,new String[]{});
			jhsj=jhsj==null?"":jhsj;
			System.out.println("jhsj------ycjSjtb:"+jhsj);
			CrjWeixinGXServiceLocator ss= new CrjWeixinGXServiceLocator();
			CrjWeixinGX wx=ss.getWeixinService();
			
			String res = wx.getDownloadData(jhsj, "");//调用接口返回的数据
//			System.out.println("res------ycjSjtb:"+res);
			List<Map<String,String>> list1 = XMLToMap.xmlStrToList(res);
			StringBuilder zdstr=new StringBuilder();
			StringBuilder valuestr=new StringBuilder();
			conn = DBAdapter.getConnect("CRJWGR");
			conn.setAutoCommit(false);
			sta=conn.createStatement();
			int index=0;
			String value="";
			for (Map<String, String> map : list1) {
				String xxlsh = map.get("xxlsh");
				String querySql = "select * from prepare_leave_country_apply where xxlsh =?";
				Map querymap = (Map)Tools.execJdbc("CRJWGR", Tools.TYPE_MAP, querySql, new String[]{xxlsh});
				//如果查询出来有数据则执行更新操作
				if(querymap!=null){
					StringBuilder upStr=new StringBuilder();
					for(int i=0;i<zds.length;i++){
						upStr.append(zds[i]+"='");
						upStr.append(querymap.get(zds[i])==null?"":querymap.get(zds[i]));
						upStr.append("',");
					}
					String upSql = "update prepare_leave_country_apply set "+upStr.substring(0,upStr.length()-1)+" where ywbh =?";
					Tools.execJdbc("CRJWGR", Tools.TYPE_UPDATE, upSql, new String[]{xxlsh});
					continue;
				}
				index++;
				for (int i=0; i<zds.length ;i++) {
					
					zdstr.append(zds[i]+",");
					value=map.get(zds[i]);
					value=value==null||"null".equals(value)?"":value;
					valuestr.append("'"+value+"',");
				}
				String insSql = "insert into prepare_leave_country_apply("+zdstr.substring(0, zdstr.length()-1)+")values("+valuestr.substring(0, valuestr.length()-1)+")";
				sta.addBatch(insSql);
				zdstr.setLength(0);
				valuestr.setLength(0);
				if(index%100==0){
					sta.executeBatch();
					conn.commit();
			
				}
			}
			sta.executeBatch();
			conn.commit();
			System.out.println("数据同步成功");
		} catch (Exception e) {
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			
			DBAdapter.close(sta);
			DBAdapter.close(conn);
			
		}
		
	}
	
	public void zcqSjtb(){
		
		Connection conn = null;
		Statement sta=null;
		//读取数据库中最新更新的数据交换时间
		String[] zds=new String[]{"ywbh","ywlx","sfzhm","lcbj","spjg","bpzyy","sfzdhc","zdhcjg","spbz","nqzrq","lczt","sqrq","yjsf","sjly","spcl","jhbj","jhbjgxsj","sldw","bzlx","openid","cldw"};
		String sql = "select max(Jhbjgxsj) jhsj from flow_info_public where sjly = 'W' and CLDW='450300'";
		try {
			String jhsj = (String)Tools.execJdbc("CRJWGR",Tools.TYPE_STRING, sql,new String[]{});
			jhsj=jhsj==null?"":jhsj;
			System.out.println("jhsj------zcqSjtb:"+jhsj);
			CrjWeixinGXServiceLocator ss= new CrjWeixinGXServiceLocator();
			CrjWeixinGX wx=ss.getWeixinService();
			String res = wx.getDownloadData(jhsj, "zcq");//调用接口返回的数据
//			System.out.println("res------zcqSjtb:"+res);
			List<Map<String,String>> list1 = XMLToMap.xmlStrToList(res);
			StringBuilder zdstr=new StringBuilder();
			StringBuilder valuestr=new StringBuilder();
			conn = DBAdapter.getConnect("CRJWGR");
			conn.setAutoCommit(false);
			sta=conn.createStatement();
			int index=0;
			String value="";
			System.out.println(list1.size());
			for (Map<String, String> map : list1) {
				String ywbh = map.get("ywbh");
				String querySql = "select * from flow_info_public where ywbh =?";
				Map querymap = (Map)Tools.execJdbc("CRJWGR", Tools.TYPE_MAP, querySql, new String[]{ywbh});
				if(querymap!=null){
					StringBuilder upStr=new StringBuilder();
					for(int i=0;i<zds.length;i++){
						upStr.append(zds[i]+"='");
						upStr.append(querymap.get(zds[i])==null?"":querymap.get(zds[i]));
						upStr.append("',");
					}
					String upSql = "update flow_info_public set "+upStr.substring(0,upStr.length()-1)+" where ywbh =?";
					Tools.execJdbc("CRJWGR", Tools.TYPE_UPDATE, upSql, new String[]{ywbh});
					continue;
				}
				index++;
				for (int i=0; i<zds.length ;i++) {
					zdstr.append(zds[i]+",");
					value=map.get(zds[i]);
					value=value==null||"null".equals(value)?"":value;
					valuestr.append("'"+value+"',");
					
				}
				String insSql = "insert into flow_info_public("+zdstr.substring(0, zdstr.length()-1)+")values("+valuestr.substring(0, valuestr.length()-1)+")";
				sta.addBatch(insSql);
				zdstr.setLength(0);
				valuestr.setLength(0);
				if(index%100==0){
					sta.executeBatch();
					conn.commit();
			
				}
			}
			sta.executeBatch();
			conn.commit();
			System.out.println("数据同步成功");
		} catch (Exception e) {
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			DBAdapter.close(sta);
			DBAdapter.close(conn);
		}
	}
	
}
