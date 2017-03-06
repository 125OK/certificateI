package com.shdic.szhg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.sql.Date;
import java.sql.Timestamp;

/************************************************************
 *  ����ժҪ	��<p>
 *
 *  ����	��adminstrator
 *  ����ʱ��	��2013-3-27 ����10:06:31 
 *  ��ǰ�汾�ţ�v1.0
 *  ��ʷ��¼	:
 *  	����	: 2013-3-27 ����10:06:31 	�޸��ˣ�adminstrator
 *  	����	:
 ************************************************************/
public class DateTool {

	private static SimpleDateFormat dateFormat = null;
	
	static {
		// ָ�����ڸ�ʽΪ��λ��/��λ�·�/��λ���ڣ�ע��yyyy/MM/dd��ִ�Сд��
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ����lenientΪfalse.
		// ����SimpleDateFormat��ȽϿ��ɵ���֤���ڣ�����2007/02/29�ᱻ���ܣ���ת����2007/03/01
		dateFormat.setLenient(false);
	}
	
	/**
	 * 
	 *  ������� : isValidDate
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param s
	 *  	@return
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:07:18	�޸��ˣ�adminstrator
	 *  	����	���ж�һ���ַ��Ƿ���ʱ���ʽ
	 *
	 */
	public boolean isValidDate(String s) {
		try {
			dateFormat.parse(s);
			return true;
		} catch (Exception e) {
			// ���throw java.text.ParseException����NullPointerException����˵����ʽ����
			return false;
		}
	}
	
	/**
	 * 
	 *  ������� : formatDate
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param d 			ʱ��	
	 *  	@param dateFormat   ʱ���ʽ
	 *  	@return
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:09:48	�޸��ˣ�adminstrator
	 *  	����	�� ��һ�����ڰ�����ָ���ĸ�ʽ���
	 *
	 */
	public String formatDate(Date d,String dateFormat) {
		SimpleDateFormat Format = new SimpleDateFormat(dateFormat);
		return Format.format(d);
	}
	
	/**
	 * 
	 *  ������� : dateCompare
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param date1	 ʱ���ʽ���ַ�   eg:2013-03-27
	 *  	@param date2	 ʱ���ʽ���ַ�
	 *  	@return 0 ����� 
	 *  			1 ������
	 *  			2��С��
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:15:23	�޸��ˣ�adminstrator
	 *  	����	���Ƚ϶�����ʱ��
	 *
	 */
	public int dateCompare(String date1,String date2) {
		
		Date a = Date.valueOf(date1);
		Date b = Date.valueOf(date2);
		
		if (a.equals(b)) {
			return 0;
		}else if (a.after(b)) {
			return 1;
		}else {
			return 2;
		}
	}
	
	/**
	 * 
	 *  ������� : timeCompare
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param a ʱ���ַ� eg:2013-03-27 15:48:24
	 *  	@param b ʱ���ַ�
	 *  	@return	0 ����� 
	 *  			1 ������
	 *  			2��С��
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:24:08	�޸��ˣ�adminstrator
	 *  	����	�� �Ƚ�����ʱ���ʽ���ַ��ǰ��
	 *
	 */
	public int timeCompare(String a, String b) {
		Timestamp time1 = Timestamp.valueOf(a);
		Timestamp time2 = Timestamp.valueOf(b);

		if (time1.equals(time2)) {
			return 0;
		} else if (time1.after(time2)) {
			return 1;
		} else {
			return 2;
		}
	}
	
	/**
	 * 
	 *  ������� : twoDateInterval
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param aa ʱ���ʽ���ַ� eg��2013-03-02
	 *  	@param bb
	 *  	@return ����ʱ���ʽ���ַ�֮���ʱ����������
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:27:52	�޸��ˣ�adminstrator
	 *  	����	������ʱ���ʽ���ַ�֮���ʱ����������
	 *
	 */
	public int twoDateInterval(String aa,String bb) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		long a = 0;
		long b = 0 ;
		try {
			a = sdf.parse(aa).getTime();
			b = sdf.parse(bb).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		int julianDay = 0 ;
		if (a > b) {
			julianDay  = (int) ((a - b) / (1000*60*60*24));
		}else {
			julianDay  = (int) ((b - a) / (1000*60*60*24));
		}
		
		System.out.println(aa + " - " + bb + " = " + julianDay + " days");
		
		return julianDay;
	}
	
	
	/**
	 * 
	 *  ������� : twoTimeInterval
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param a eg:"2013-03-25 11:30:09"
	 *  	@param b
	 *		@return �죺ʱ���֣���
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:34:55	�޸��ˣ�adminstrator
	 *  	����	����������ʱ������ʱ����
	 *
	 */
	public String twoTimeInterval(String a,String b) {
		try {
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date begin = dfs.parse(a);
			java.util.Date end = dfs.parse(b);
			
			long between = (end.getTime() - begin.getTime()) / 1000;// ����1000��Ϊ��ת������

			System.out.println("����ʱ�䣺" + between);
			
			long day = between / (24 * 3600);
			long hour = between % (24 * 3600) / 3600;
			long minute = between % 3600 / 60;
			long second = (between % 60);
			System.out.println("" + day + "��" +  hour + "Сʱ" + minute + "��"+ second + "��");
			return day +":" + hour +":" + minute +":" + second ;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 
	 *  ������� : afterdate
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param str_date  ʱ��  eg:2013-03-27
	 *  	@param days      ������� 
	 *  	@return
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:40:41	�޸��ˣ�adminstrator
	 *  	����	��������days�������
	 *
	 */
	public String afterdate(String str_date ,int days) {
		try {
			
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = dfs.parse(str_date);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			//����֮�������
			String str_return = (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
 			return str_return;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 
	 *  ������� : afterTime
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param str_date  ����  2013-03-23 11:40:09
	 *  	@param minute    �������
	 *  	@return          ����֮�������
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:50:52	�޸��ˣ�adminstrator
	 *  	����	�����minute֮�������
	 *
	 */
	public String afterTime(String str_date ,int minute) {
		try {
			
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = dfs.parse(str_date);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, minute);
			//����֮���ʱ��
			String str_return = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
 			return str_return;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 
	 *  ������� : withinRange
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param str_date    ����
	 *  	@param uncheck     ����֤������
	 *  	@param befer       ֮ǰ�ļ�����
	 *  	@param after	        ֮��ļ�����
	 *  	@return 
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-3-27 ����10:56:24	�޸��ˣ�adminstrator
	 *  	����	���ж�uncheck�����������str_date֮ǰ��before���Ӻ�after����֮��Χ��
	 *
	 */
	public boolean withinRange(String str_date,String uncheck,int before,int after) {
		try{
			
			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = dfs.parse(str_date);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, (-before));
		
			//��ʼʱ��
			String str_before = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
			System.out.println("str_before" + str_before);
			Timestamp time_before = Timestamp.valueOf(str_before);
			
			//����ʱ��
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date);
			cal2.add(Calendar.MINUTE, after);
			String str_after = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal2.getTime());
			System.out.println("str_after" + str_after);
			Timestamp time_after = Timestamp.valueOf(str_after);
			
			
			//����֤��ʱ��
			Timestamp time_unckeck = Timestamp.valueOf(uncheck);
			
			//�ж��Ƿ���������ʱ���֮��
			if (time_unckeck.after(time_before) && time_unckeck.before(time_after) || time_unckeck.equals(time_before)|| time_unckeck.equals(time_after)) {
				return true;
			}else {
				System.out.print("!=");
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 
	 *  ������� : WhatDayIsToday
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param century 			����ǰ����-1������2013����21���ͣ�����centuryΪ20
	 *  	@param year     		 �꣺����2013����ôyear����13
	 *  	@param monthOfYear      �·ݣ�һ���е��·ݣ�
	 *  	@param dayOfMonth       ���ڣ�һ���е����ڣ�
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-4-8 ����10:32:33	�޸��ˣ�adminstrator
	 *  	����	������ĳ��ʱ����һ��֮�е����ڼ�
	 *  	�������w=y+[y/4]+[c/4]-2c+[26(m+1)/10]+d-1
	 *  		   ��ʽ�еķ�ź������£�w�����ڣ�c������-1��y���꣨��λ��m���£�m���ڵ���3��С�ڵ���14�����ڲ��չ�ʽ�У�ĳ���1��2��Ҫ������һ���13��14�������㣬
	 *			 ����2003��1��1��Ҫ����2002���13��1�������㣩��d���գ�[ ]���ȡ��ֻҪ����֡�(C���������һ��y����ݺ���λ��M���·ݣ�d������
	 *			1�º�2��Ҫ����һ���13�º� 14�����㣬��ʱC��y����һ��ȡֵ��)�������W����7�������Ǽ��������ڼ������������0����Ϊ�����ա�
	 */
	public void  WhatDayIsToday(int century, int year,int monthOfYear,int dayOfMonth) {
		int c = century;
		int y = year;
		int m = 0;
		
		if (monthOfYear < 3) {
			y -= 1;
			m = monthOfYear + 12;
		}else {
			m = monthOfYear;
		}
		
		int d = dayOfMonth;
		int w = (y + (y / 4) + (c / 4) - (2 * c) + (26 * (m + 1) / 10) + d - 1) % 7;
		System.out.println("w = " + w);
		
		if (w < 0) {
			w = w + 7;
		}
		
		String myWeek = null;
		switch (w) {
		case 1:
			myWeek = "һ";
			break;
		case 2:
			myWeek = "��";
			break;
		case 3:
			myWeek = "��";
			break;
		case 4:
			myWeek = "��";
			break;
		case 5:
			myWeek = "��";
			break;
		case 6:
			myWeek = "��";
			break;
		case 0:
			myWeek = "��";
			break;

		default:
			break;

		}
	
		System.out.println(year+"��"+(monthOfYear)+"��"+dayOfMonth+"��"+"��"+myWeek);
	}
	
	/**
	 * 
	 *  ������� : dateDiff
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param startTime 		��ʼʱ��
	 *  	@param endTime     		����ʱ��
	 *  	@param format           ʱ���ʽ
	 *  	@param str       	    
	 *
	 *  �޸ļ�¼����������ʱ�����졢Сʱ�����ӡ��룩
	 *  	
	 */
	public Long dateDiff(String startTime, String endTime,String format, String str) {   
        // ���մ���ĸ�ʽ���һ��simpledateformate����   
        SimpleDateFormat sd = new SimpleDateFormat(format);   
        long nd = 1000 * 24 * 60 * 60;// һ��ĺ�����   
        long nh = 1000 * 60 * 60;// һСʱ�ĺ�����   
        long nm = 1000 * 60;// һ���ӵĺ�����   
        long ns = 1000;// һ���ӵĺ�����   
        long diff;   
        long day = 0;   
        long hour = 0;   
        long min = 0;   
        long sec = 0;   
        // �������ʱ��ĺ���ʱ�����   
        try {   
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();   
            day = diff / nd;// ����������   
            hour = diff % nd / nh + day * 24;// ��������Сʱ   
            min = diff % nd % nh / nm + day * 24 * 60;// �������ٷ���   
            sec = diff % nd % nh % nm / ns;// ����������   
            // ������   
//            System.out.println("ʱ����" + day + "��" + (hour - day * 24) + "Сʱ"  + (min - day * 24 * 60) + "����" + sec + "�롣");   
//            System.out.println("hour=" + hour + ",min=" + min);   
            
            if (str.equalsIgnoreCase("h")) {   
                return hour;   
            } else {   
                return min;   
            }   
        } catch (ParseException e) {   
            e.printStackTrace();   
            return null;
        }   
    }

	/**
	 * 
	 *  ������� : weekTime
	 *  �������� :  
	 *  �����ֵ˵����
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-6-21 ����05:59:03	�޸��ˣ�����Դ
	 *  	����	�����㱾�ܵ�ʱ���
	 *
	 */
	public HashMap<String, Object>  weekTime() {
		Calendar cal = Calendar.getInstance();
		String today = String.format("%tF", cal.getTime());
		
		System.out.println("���������: " + today);

		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		String startDate = String.format("%tF", cal.getTime());
		System.out.println("���ܵ�һ��: " + startDate);

//		cal.add(Calendar.DATE, 7);
		cal.add(Calendar.DATE, 7);
		String endDate = String.format("%tF", cal.getTime());
		System.out.println("����ĩ: " + endDate);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("today", today);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		return map;
	}
	
	/**
	 * 
	 *  ������� : differSecond
	 *  �������� :  
	 *  �����ֵ˵����
	 *  	@param a
	 *  	@param b
	 *  	@return
	 *
	 *  �޸ļ�¼��
	 *  	���ڣ�2013-6-25 ����09:59:35	�޸��ˣ�adminstrator
	 *  	����	����������
	 *
	 */
	public long differSecond(String a,String b) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date begin;
		long between = 0;
		
		try {
			begin = dfs.parse(a);
			java.util.Date end = dfs.parse(b);
			between = (end.getTime() - begin.getTime()) / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return between;
	}
}
