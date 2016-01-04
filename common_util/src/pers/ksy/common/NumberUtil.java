package pers.ksy.common;

import java.text.DecimalFormat;

public class NumberUtil {
	static DecimalFormat df;
	public static String formartNum(long num,String format){
		df = new DecimalFormat(format);
		return df.format(num);
	}
	
	public static String formartNum(long num, char c, int length) {
		String format = getFormat(c, length);
		df = new DecimalFormat(format);
		return df.format(num);
	}
	
	public static String formartNum(int num,String format){
		df = new DecimalFormat(format);
		return df.format(num);
	}
	
	public static String formartNum(int num, char c, int length) {
		String format = getFormat(c, length);
		df = new DecimalFormat(format);
		return df.format(num);
	}
	
	public static String formartNum(double num,String format){
		df = new DecimalFormat(format);
		return df.format(num);
	}
	
	public static String formartNum(float num,String format){
		df = new DecimalFormat(format);
		return df.format(num);
	}
	
	private static String getFormat(char c, int length){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<length;i++){
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(formartNum(new Long(6), "000000"));
	}
	
}
