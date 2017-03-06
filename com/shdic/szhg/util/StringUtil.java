
package com.shdic.szhg.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : zanaishan
 * Description: 
 * Company: 上海天缘迪柯信息技术有限公司
 * @since Apr 20, 2011
 * 历次修改人 修改时间 修改原因和修改部分功能描述
 *
 */
public class StringUtil 
{
	public static boolean isEmpty(String string)
	{
		return string == null || string.trim().length() == 0;
	}
	/**
	 * 页面字符串长度的处理
	 * @param s
	 * @param i
	 * @return
	 */
	public static String substring(String s, int i) 
    {
        if(s == null || s.trim().equals(""))
        {
            return "";
        }
        else if(s.trim().length() <= i)
        {
            return s.trim();
        }
        else
        {
           return s.trim().substring(0,i) + "..."; 
        }
    }
	
	
	/**
	 * 分割字符串。
	 * @param string
	 * @return
	 */ 
	public static String[] spitString(String string)
	{
		if(string == null || string.trim().equals(""))
		{
			return null;
		}
		Pattern p = Pattern.compile("[,|.|;|'|，|。|；|、|！|：]");
		return p.split(string);
	}	
	
	/**
	 * 检查指定的字符串是否能转换成int类型
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true:此字符串能够换成int型 false:反之
	 */
	public static boolean isInt(String meta) {
		if (meta == null) {
			return false;
		}
		
	//检查字符串内字符是否在'0'到'9'（包括边界）之间。
	//在，返回true；不在，返回false。
		for (int i = 0; i < meta.length(); i++) {
			char c = meta.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
	//检查字符串是否为整数。
		try {
			Integer.parseInt(meta);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	/**
	 * 检查指定的字符串是否全部由数字组成
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true:此字符串全部由数字组成 false:反之
	 */
	public static boolean isDigitAll(String meta) {
		if (meta == null) {
			return false;
		}
		
	//检查字符串内字符是否在'0'到'9'（包括边界）之间。
	//在，返回true；不在，返回false。
		for (int i = 0; i < meta.length(); i++) {
			char c = meta.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}

		return true;	
	}
	
	/**
	 * 检查指定的字符串是否全部由字母组成
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true:此字符全部由字母组成 false:反之
	 */	
	public static boolean isLetterAll(String meta) {
		if (meta == null) {
			return false;
		}
	//检查字符串内字符是否在'a'到'z'（包括边界）之间或在'A'到'Z'（包括边界）之间。
	//在，返回true；不在，返回false。
		for (int i = 0; i < meta.length(); i++) {
			char c = meta.charAt(i);
			if (c <'A' 
				|| ((c<'a')&&(c>'Z')) 
				|| c >'z') {
				return false;
			}
		}
		return true;		
	}
	
	/**
	 * 检查指定的字符串是否全部由字母或数字组成
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true:此字符串全部由字母或数字组成 false:反之
	 */	
	public static boolean isDigitAndLetterAll(String meta) 
	{
		if (meta == null) {
			return false;
		}
	//检查字符串内字符是否在'a'到'z'（包括边界）之间或在'A'到'Z'（包括边界）之间或在'0'到'9'（包括边界）之间。
	//在，返回true；不在，返回false。
		for (int i = 0; i < meta.length(); i++) {
			char c = meta.charAt(i);
			
			if (c < '0' 
				|| ((c<'A')&&(c>'9')) 
				|| ((c<'a')&&(c>'Z')) 
				|| c>'z') {
				return false;
			}
		}

		return true;	
	
	}
	
	/**
	 * 检查指定的字符串是否由数字和逗号组成
	 * @param meta
	 * @return
	 */
	public static boolean isCommaAndDigit(String meta)
	{		
		if (meta == null) {
			return false;
		}

	//检查字符串内字符是否在'0'到'9'（包括边界）之间或为','，以','隔开，不能以','为开头和结尾，也不能有连续的','。
	//满足条件，返回true；不满足，返回false。
		char cpre=',';
		for (int i = 0; i < meta.length(); i++) {
			char c = meta.charAt(i);
			if ((c < '0' || c > '9')&& (c!=',') ){
				return false;
			}
			if(c==','&& cpre==',')
				return false;
			cpre=c;
		}
		
		if(cpre==',')
			return false;
		
		return true;
	}

	/**
	 * @author lidh
	 * 生成六位随机数
	 * @param rs
	 * @return
	 */

	public static int getRandom(){
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		int re = tmp%(999999-100000+1)+100000;
		return re;
	}

	public static boolean isEMail(String email)
	{
		if(email == null || email.trim().equals(""))
		{
			return false;
		}
		//String check = "^([a-z0-9A-Z]+[-|\._]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$";
		String regex = "[a-z0-9a-z][\\w_]+@\\w+(\\.\\w+)+";
		Pattern pattern = Pattern.compile(regex);   
        Matcher matcher = pattern.matcher(email);   
        return matcher.matches();
	}
}
