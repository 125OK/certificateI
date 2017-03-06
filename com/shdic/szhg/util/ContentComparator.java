package com.shdic.szhg.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ContentComparator implements Comparator{	
	
	/**
	 * 
	 * 比较字段类
	 * @param conStr
	 * @param orderStr
	 */
	public ContentComparator(String conStr,String orderStr)
	{
		this.conString = conStr;
		this.orderString = orderStr;
	}
	public String conString = "";//排序字段
	public String orderString="";//排序方式
	public int compare(Object o1, Object o2)
	{
		Map c1=(HashMap)o1;
		Map c2=(HashMap)o2;
		int comStr1 = 0;//o1比较的字段
		int comStr2 = 0;//02比较字段
		Object obj1=c1.get(conString);
		if(obj1!=null)
		{
			comStr1=Integer.parseInt(c1.get(conString).toString());
			comStr2=Integer.parseInt(c2.get(conString).toString());	
		}
				
		if (comStr1 > comStr2) 
		{ 
			if(orderString.equalsIgnoreCase("desc"))
				return -1;
			else
				return 1;     
			
		}
		else 
			 {     
				  if (comStr1== comStr2) 
				  {
					  return 0; 
				  } 
				  else 
			      {   
					  if(orderString.equalsIgnoreCase("desc"))
						  return 1;
					  else
					      return -1;
				   }   
		     }    		
	}
}
