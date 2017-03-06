package com.shdic.szhg.newServices;


public interface WeChatService {
	
	/**
	 * @author zyf
	 * 
	 * 接口实现逻辑
   	 *	根据前台传入的参数ID查询内容发布信息表(CONTENT)中符合条件的公告详细信息，并将查询封装结果；
	 *	参考sql语句：
	 *	select content_title title,content_fbuser fbuser,content_fbtime fbtime,content_body content from content t where content_id =? 
	 * @param ID
	 * @return
	 */
	public String getContentById (String id,String openid,String ip);
	
	/**
	 * @author zyf
	 * 
	 * 接口实现逻辑：
	 * 根据前台传入的参数SYRESOURCE_ID查询该模块下的所有子模块信息，
	 * 查到将子模块的公告类型代码ID和公告名称NAME封装到xml结果返回给前台；
	 * 参考sql:select * from SYRESOURCE t where SYRESOURCE_ID=？ order by seq
	 * @param SYRESOURCE_ID  模块ID代码
	 * @return
	 */
	public String getWgrfwlx (String  syresource_id,String openid,String ip);
	
	

	
	/**
	 * @author lk
	 * 
	 *   接口的实现逻辑：
	 *   根据公告类型的代码CONTENT_ID获取相应的公告信息目录列表
	 *   参考sql语句：
	 *   select content_id, content_title,content_fbtime from content t where content_yxzt='1'  
	 *   and content_type=?  order by content_fbtime desc
	 *   
	 */
	public String getGglist(String content_id,String openid,String ip);
	

	
	/**
	 * @author lk
	 * 
	 * 	接口的实现逻辑：
	 *   首先根据传入的参数查询自助咨询配置表(SELF_CONSULT_CFG)，找到如果自助咨询配置表未查找到相应的信息，
	 *   则需到自助咨询字典信息表（ATOM_DICTIONARY_INFO）中查找相关信息，并将查询封装结果；
	 *   参考的sql语句：
	 *   Select node_id, subnode_id,dysz,node_name from self_consult_cfg t where dj=?+1  And node_id in(?) and sfky='1' order by dysz
	 * 
	 */
	public String getSelfDictionCfg(String dj,String subnode_id,String openid,String ip);
	


	
	/**
	 * @author lk
	 * 
	 * 	接口的实现逻辑：

	 * 	 查询大厅受理量信息表(DT_SLL_INFO)，并将查询封装结果；
	 *  参考的sql语句：
	 *  SELECT sld, (select dmmc from system_code
     *    where dmlb = '受理点' and dmz = t.sld) sldmc, sll  FROM DT_SLL_INFO T
     *   where substr(sld,0,4)=? And slrq = to_char(sysdate, 'yyyymmdd')  order by sld
     *
	 */
	public String getPdCount(String sld,String openid,String ip);
	
	/**
	 * 
	 * @Description: 判断微信客服是否休息
	 * @param @return
	 * @return String
	 * @throws
	 *
	 * @author zyf
	 * @date 2016-7-18
	 */
	public String getIsWorkDay();
	
	/**
	 * 
	 * @Description: 再次签数据同步
	 * @param @return
	 * @return String xml字符串
	 * @throws
	 *
	 * @author lisw
	 * @date 2016-12-22
	 */
	public String getDownloadData(String username, String pwd, String type);
	

}

