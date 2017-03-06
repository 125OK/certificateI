
package com.shdic.szhg.util;

import java.text.ChoiceFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author : zanaishan
 * Description: 
 * Company: 上海天缘迪柯信息技术有限公司
 * @since Apr 20, 2011
 * 历次修改人 修改时间 修改原因和修改部分功能描述
 *
 */
public class DateUtil
{
	private static ChoiceFormat cf = null;

	/**
	 * 将数字转成中文
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthGBK(int month)
	{

		cf = new ChoiceFormat("0#○|1#一|2#二|3#三|4#四|5#五|6#六|7#七|8#八|9#九|10#十|11#十一|12#十二");
		return cf.format(month);

	}

	/**
	 * 比较两个日期相差天数
	 * 
	 * @param source
	 * @param aim
	 * @return
	 */
	public static long diffDaysOfDate(Date source, Date aim)
	{
		long diff = source.getTime() - aim.getTime();
		return diff / (1000 * 60 * 60 * 24);
	}

	/**
	 * 计算年龄
	 * 
	 * @param source
	 * @param aim
	 * @return
	 */
	public static String getAgeOf2Date(Date source, Date aim)
	{
		long diff = source.getTime() - aim.getTime();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(diff);
		return (c.get(Calendar.YEAR) - 1970) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DATE) + "日" ;
	}

	/**
	 * 将日期转化成String 使用默认的格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateToString(Date date)
	{
		return getDateToString(date, null);
	}

	public static Date string14ToDate(String date){
		if(date == null || date.trim().equals(""))
		{
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d1 = null;
		try
		{
			d1 = df.parse(date);
			return d1;
		}
		catch (ParseException e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将字符串转换成日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStringToDate(String date)
	{
		if(date == null || date.trim().equals(""))
		{
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try
		{
			d1 = df.parse(date);
		}
		catch (ParseException e)
		{
			DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			try
			{
				d1 = df2.parse(date);
			}
			catch(ParseException ee)
			{
				ee.printStackTrace();
				return null;
			}
		}
		return d1;
	}
	
	
	public static boolean getString2Date(String date)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			df.parse(date);
		}
		catch (ParseException e)
		{
			DateFormat df2 = new SimpleDateFormat("yyyy/MM/dd");
			try
			{
				df2.parse(date);
			}
			catch(ParseException ee)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * 将日期转化成String 使用传入的format
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateToString(Date date, String format)
	{
		if (format == null)
		{
			format = "yyyy-MM-dd HH:mm:ss";
		}
		if(date == null)
		{
			return null;
		}
		String str = null;
		SimpleDateFormat myFormat = new SimpleDateFormat(format);
		str = myFormat.format(date);
		return str;
	}

	/**日期格式化
	 * @return  返回yyyy年MM月dd日样式的日期字符串
	 */
	static public String dateFormatCN(java.util.Date date)
	{
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy年MM月dd日");
		return formatter.format(date);
	}

	public DateUtil()
	{
		super();

	}
	
	/**
	 * 返回当前的日期如：2007-7-22
	 * @return
	 */
	public static Date getCurrentDate()
	{
		Calendar cal = Calendar.getInstance();
		
		String year = String.valueOf(cal.get(Calendar.YEAR));
		String month = (String.valueOf(cal.get(Calendar.MONTH) + 1)).length() == 1?"0"+String.valueOf(cal.get(Calendar.MONTH) + 1):String.valueOf(cal.get(Calendar.MONTH) + 1);
		String day = (String.valueOf(cal.get(Calendar.DAY_OF_MONTH))).length() == 1?"0"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)):String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		String date = year + "/" + month +"/" + day;				
		return getStringToDate(date);
	}
	
	/**
	 * 根据传入的日期和变化天数,返回变化后的日期
	 * @param date 传入的初始日期
	 * @param inc 要变化的天数
	 * @return 变化后的日期
	 */
	public static Date getIncDate(Date date,int inc)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, inc);
		return c.getTime();
	}
	
	/**
	 * 由一个10位的yyyy-mm-dd字符串（其中yyyy、mm、dd是整数），
	 * 获得年份  yyyy字符串
	 * @param  date 字符串对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String getYear(String date)
	{		
		if (date == null)
			return null;
		
		if (!isDate(date))
			return null;
		
		String[] temp = date.split("-");

		return temp[0];
	}
	

	/**
	 * 由一个10位的yyyy-mm-dd字符串（其中yyyy、mm、dd是整数），
	 * 获得月份  mm字符串
	 * @param  date 字符串对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String getMonth(String date)
	{	
		if (date == null)
			return null;
		
		if (!isDate(date))
			return null;
		
		String[] temp = date.split("-");

		return temp[1];		
	}
	

	/**
	 * 由一个10位的yyyy-mm-dd字符串（其中yyyy、mm、dd是整数），
	 * 获得天的  mm字符串
	 * @param  date 字符串对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String getDay(String date)
	{	
		if (date == null)
			return null;
		
		if (!isDate(date))
			return null;
		
		String[] temp = date.split("-");

		return temp[2];
	
	}
	/**
	 * 从一个时间类型的对象返回表示此时间的14位的字符串
	 * @param date 时间对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String DateToString14(Date date)
	{
		if(date ==null)
		{
           return null;   
        }
		
		String str=null;
		SimpleDateFormat myFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		str=myFormat.format(date);		
		return str;
	}
	
	/**
	 * 从一个时间类型的对象返回表示此时间的8位的字符串
	 * @param date 时间对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String DateToString10(Date date)
	{   if(date ==null) {
        return null;   
      }
		String str=null;
		SimpleDateFormat myFormat=new SimpleDateFormat("yyyy-MM-dd");
		str=myFormat.format(date);		
		return str;
	}
	
	/**
	 * 从一个时间类型的对象返回表示此时间的8位的字符串
	 * @param date 时间对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String DateToString8(Date date)
	{   if(date ==null) {
        return null;   
      }
		String str=null;
		SimpleDateFormat myFormat=new SimpleDateFormat("yyyyMMdd");
		str=myFormat.format(date);		
		return str;
	}
	
	/**
	 * 从一个时间类型的对象返回表示年份的字符串
	 * @param date 时间对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String getYear(Date date)
	{
		if(date ==null)
		{
           return null;   
        }
		String str=null;
		SimpleDateFormat myFormat=new SimpleDateFormat("yyyy");
		str=myFormat.format(date);		
		return str;
	}
	
	/**
	 * 从一个时间类型的对象返回表示月份的字符串
	 * @param date 时间对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String getMonth(Date date)
	{
		if(date ==null)
		{
           return null;   
        }
		String str=null;
		SimpleDateFormat myFormat=new SimpleDateFormat("MM");
		str=myFormat.format(date);		
		return str;
	}
	
	/**
	 * 从一个时间类型的对象返回表示号数的字符串
	 * @param date 时间对象
	 * @return String:对应的字符串 null:出现异常
	 */
	public static String getDay(Date date)
	{
		if(date ==null)
		{
           return null;   
        }
		String str=null;
		SimpleDateFormat myFormat=new SimpleDateFormat("dd");
		str=myFormat.format(date);		
		return str;
	}
	
	/**
	 * 从一个时间类型的对象返回表示年、月、日的数组
	 * @param date 时间对象
	 * @return String[]:对应的字符串数组 null:出现异常
	 */
	public static String[] getYearMonthDay(Date date)
	{
		if(date ==null)
		{
           return null;   
        }
		String[] str=new String[3];		
		str[0]=getYear(date);	
		str[1]=getMonth(date);
		str[2]=getDay(date);
		return str;
	}
	/**
	 * 将Date类型的时间转换为 java.sql.Date 类型的时间
	 *
	 * @param date Date 类型的时间
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date)
	{	
		if(date ==null)
		{
           return null;   
        }
		java.sql.Date datesql=new java.sql.Date(date.getTime());
		return datesql;
	}
	/**
	 * 将java.sql.Date 类型的时间转换为 Date类型的时间
	 *
	 * @param  date java.sql.Date类型的时间
	 * @return 
	 */
	public static Date getDate(java.sql.Date date)
	{
		if(date ==null)
		{
           return null;   
        }
		Date dateutil=new Date(date.getTime());
		return dateutil;
	}

	/**
	 * 将java.sql.Date 类型的时间转换为 Date类型的时间
	 *
	 * @param  date java.sql.Date类型的时间
	 * @return 
	 */
	public static Date getDate(String date)
	{
		if(date ==null)
		{
           return null;   
        }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 *判断一个10位的yyyy-mm-dd字符串（其中yyyy、mm、dd是整数），
	 * 是否可以转化为时间类型
	 * @param  date 字符串对象
	 * @return true:正确 false:出现异常
	 */
	public static boolean isDate(String date)
	{
		if (date == null)
			return false;
		
		String[] temp = date.split("-");
		
		if (temp.length != 3)
			return false;
		
		for (int i = 0; i < temp.length; i++)
		{
			if (!StringUtil.isInt(temp[i]))
			{
				return false;
			}
		}
		
		return true;
		
	}	
	/**
	 * 比较两个时间的大小（早或者迟）
	 *
	 * @param dateBegin 
	 * @param dateEnd
	 * @return true:两个比较的时间相同或者前者时间比后者迟  false：前者时间比后者早
	 */
	public static boolean compareDate(String dateBegin,String dateEnd){
		if(DateUtil.isDate(dateBegin) && DateUtil.isDate(dateEnd)){			
			int compYear = DateUtil.getYear(dateBegin).compareTo(DateUtil.getYear(dateEnd));
			int compMonth = DateUtil.getMonth(dateBegin).compareTo(DateUtil.getMonth(dateEnd));
			int compDay = DateUtil.getDay(dateBegin).compareTo(DateUtil.getDay(dateEnd));
			
			if(compYear>0||((compYear==0)&&(compMonth>0))||((compYear==0)&&(compMonth==0)&&(compDay>0||compDay==0))) 
				return true;					 
		}		
		return false;
	}
	
	/**
	 * 生成yyyyMMdd格式的当前日期
	 * 
	 * @return String
	 */
	public static String currentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 生成yyyyMMddHHmmss格式的当前时间
	 * 
	 * @return String
	 */
	public static String current() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 生成HHmmss格式的当前时间 不包括年月日
	 * 
	 * @return String
	 */
	public static String currentTime() {
		SimpleDateFormat df = new SimpleDateFormat("HHmmss");

		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 增加若干小时后的当前时间
	 * 
	 * @param hours
	 * @return
	 */
	public static String currentAfter(int hours) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return df.format(calendar.getTime());
	}
	
	/**
	 * 
	 * @author: tangwei
	 * Description:增加若干天后的当前日期
	 * @param day
	 * @return
	 *
	 */
	public static String currentAfterDay(int day){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return df.format(calendar.getTime());
	}
	
	/**
     * 生成yyyy-MM-dd格式的当前日期
     * @return String
     */
    public static String currentNewDate()
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
	 * 
	 * @author: 
	 * Description:生成yyMMdd格式的当前日期
	 * @return String 
	 *
	 */
	public static String currentDateofSix(){
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}
}
