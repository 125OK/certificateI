package com.shdic.szhg.util;



import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {

	public static void main(String[] args) {
		try {
			System.out.println(getCurrentTime(-115));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取时区
	 * @param y 经度
	 * @return
	 */
	public static int getTimeArea(double y){
		int timeArea=0;
		//System.out.println(Math.abs(y));
		double t_y = y;
		y = Math.abs(y);
		if(0<y&&y<=7.5){
			timeArea=0;
		}else if(7.5<y&&y<=22.5){
			timeArea = 1;
		}else if(22.5<y&&y<=37.5){
			timeArea = 2;
		}else if(37.5<y&&y<=52.5){
			timeArea = 3;
		}else if(52.5<y&&y<=67.5){
			timeArea = 4;
		}else if(67.5<y&&y<=82.5){
			timeArea = 5;
		}else if(82.5<y&&y<=97.5){
			timeArea = 6;
		}else if(97.5<y&&y<=112.5){
			timeArea = 7;
		}else if(112.5<y&&y<=127.5){
			timeArea = 8;
		}else if(127.5<y&&y<=142.5){
			timeArea = 9;
		}else if(142.5<y&&y<=157.5){
			timeArea = 10;
		}else if(157.5<y&&y<=172.5){
			timeArea = 11;
		}else if(172.5<y&&y<=180){
			timeArea = 12;
		}
		if(t_y<0){
			timeArea=Integer.parseInt("-"+timeArea);
		}
		
		return timeArea;
	}
	
	/**
	 * 根据坐标获取时间
	 * @param y
	 * @return
	 */
	public static String getCurrentTime(double y){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.HOUR_OF_DAY, -8+getTimeArea(y));//先转成0时区
		String current = sdf.format(cd.getTime());
		System.out.println(current);
		return current;
	}
	
	/**
	 * 根据时间间隔获取时间
	 * @param timeInterver
	 * @return
	 */
	public static String getCurrentTime(int timeInterver){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.HOUR_OF_DAY, timeInterver);
		String current = sdf.format(cd.getTime());
		System.out.println(current);
		return current;
	}
	/**
	 * 根据传过来的（时间 天数）数字加减时间
	 * @param timeInterver
	 * @return
	 */
	
}
