package com.shdic.szhg.init;

import java.util.HashMap;

public class GlobalData {
    /**
     * 获得所有的代码表中信息
     * @return HashMap
     */
    public static HashMap GlobalCode() 
    {
        HashMap hmglobalcode = new HashMap();
        OrganizeCode organizecode = new OrganizeCode();
        hmglobalcode = organizecode.OrganizeCode();

        return hmglobalcode;
    }
    
    /**
     * 获得机构配置信息中信息
     * @return HashMap
     */
    public static HashMap GlobalStructurepara() {
    	OrganizeCode organizecode = new OrganizeCode();
    	HashMap hmstructure =  organizecode.getStructurePara();
        
        return hmstructure;
    }
    /**
     * 
     *从消息表中查询出来消息<br>
     * @param @return  
     * @return HashMap 查询的结果
     * @throws
     */
    public static HashMap GlobalMessage()
    {
    	HashMap messageCode = new HashMap();
    	OrganizeCode organizecode = new OrganizeCode();
    			messageCode =  organizecode.getMessageCode();
    	return messageCode;
    }
    
}
