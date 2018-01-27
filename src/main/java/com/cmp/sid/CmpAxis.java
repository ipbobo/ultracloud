package com.cmp.sid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cmp.util.StringUtil;

//坐标轴
public class CmpAxis {
	private Date xaxis1;//x轴1
	private Date xaxis2;//x轴2
	private Date xaxis3;//x轴3
	private Date xaxis4;//x轴4
	private Date xaxis5;//x轴5
	private Date xaxis6;//x轴6
	private String yaxis1;//y轴1
	private String yaxis2;//y轴2
	private String yaxis3;//y轴3
	private String yaxis4;//y轴4
	private String yaxis5;//y轴5
	private String yaxis6;//y轴6
	private String timeType;//时间类型：hour-小时；day-天；week-周；month-月；halfyear-半年；year-年
	
	public CmpAxis(String timeType){
		this.timeType=timeType;
		if("hour".equals(timeType)){//小时
			Date currDate=StringUtil.getFmtDate(new Date(), "yyyyMMddHHmm");//当前时间：精确到分钟
			xaxis1=StringUtil.getRaiseDay(currDate, Calendar.MINUTE, -50);
			xaxis2=StringUtil.getRaiseDay(currDate, Calendar.MINUTE, -40);
			xaxis3=StringUtil.getRaiseDay(currDate, Calendar.MINUTE, -30);
			xaxis4=StringUtil.getRaiseDay(currDate, Calendar.MINUTE, -20);
			xaxis5=StringUtil.getRaiseDay(currDate, Calendar.MINUTE, -10);
			xaxis6=currDate;
		}else if("day".equals(timeType)){//天
			Date currDate=StringUtil.getFmtDate(new Date(), "yyyyMMddHH");//当前时间：精确到小时
			xaxis1=StringUtil.getRaiseDay(currDate, Calendar.HOUR_OF_DAY, -20);
			xaxis2=StringUtil.getRaiseDay(currDate, Calendar.HOUR_OF_DAY, -16);
			xaxis3=StringUtil.getRaiseDay(currDate, Calendar.HOUR_OF_DAY, -12);
			xaxis4=StringUtil.getRaiseDay(currDate, Calendar.HOUR_OF_DAY, -8);
			xaxis5=StringUtil.getRaiseDay(currDate, Calendar.HOUR_OF_DAY, -4);
			xaxis6=currDate;
		}else if("week".equals(timeType)){//周
			Date currDate=StringUtil.getFmtDate(new Date(), "yyyyMMdd");//当前时间：精确到天
			xaxis1=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -5);
			xaxis2=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -4);
			xaxis3=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -3);
			xaxis4=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -2);
			xaxis5=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -1);
			xaxis6=currDate;
		}else if("month".equals(timeType)){//月
			Date currDate=StringUtil.getFmtDate(new Date(), "yyyyMMdd");//当前时间：精确到天
			xaxis1=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -25);
			xaxis2=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -20);
			xaxis3=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -15);
			xaxis4=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -10);
			xaxis5=StringUtil.getRaiseDay(currDate, Calendar.DAY_OF_MONTH, -5);
			xaxis6=currDate;
		}else if("halfyear".equals(timeType)){//半年
			Date currDate=StringUtil.getFmtDate(new Date(), "yyyyMM");//当前时间：精确到月
			xaxis1=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -5);
			xaxis2=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -4);
			xaxis3=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -3);
			xaxis4=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -2);
			xaxis5=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -1);
			xaxis6=currDate;
		}else if("year".equals(timeType)){//年
			Date currDate=StringUtil.getFmtDate(new Date(), "yyyyMM");//当前时间：精确到月
			xaxis1=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -10);
			xaxis2=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -8);
			xaxis3=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -6);
			xaxis4=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -4);
			xaxis5=StringUtil.getRaiseDay(currDate, Calendar.MONTH, -2);
			xaxis6=currDate;
		}
	}

	public String getXaxis() {
		String pattern=null;
		String unit=null;
		if("hour".equals(timeType)){//小时
			pattern="mm";
			unit="分";
		}else if("day".equals(timeType)){//天
			pattern="HH";
			unit="时";
		}else if("week".equals(timeType)){//周
			pattern="dd";
			unit="日";
		}else if("month".equals(timeType)){//月
			pattern="dd";
			unit="日";
		}else if("halfyear".equals(timeType)){//半年
			pattern="MM";
			unit="月";
		}else if("year".equals(timeType)){//年
			pattern="MM";
			unit="月";
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return sdf.format(xaxis1)+unit+","+sdf.format(xaxis2)+unit+","+sdf.format(xaxis3)+unit+","+sdf.format(xaxis4)+unit+","+sdf.format(xaxis5)+unit+","+sdf.format(xaxis6)+unit;
	}
	
	public Date getXaxis1() {
		return xaxis1;
	}

	public void setXaxis1(Date xaxis1) {
		this.xaxis1 = xaxis1;
	}

	public Date getXaxis2() {
		return xaxis2;
	}

	public void setXaxis2(Date xaxis2) {
		this.xaxis2 = xaxis2;
	}

	public Date getXaxis3() {
		return xaxis3;
	}

	public void setXaxis3(Date xaxis3) {
		this.xaxis3 = xaxis3;
	}

	public Date getXaxis4() {
		return xaxis4;
	}

	public void setXaxis4(Date xaxis4) {
		this.xaxis4 = xaxis4;
	}

	public Date getXaxis5() {
		return xaxis5;
	}

	public void setXaxis5(Date xaxis5) {
		this.xaxis5 = xaxis5;
	}

	public Date getXaxis6() {
		return xaxis6;
	}

	public void setXaxis6(Date xaxis6) {
		this.xaxis6 = xaxis6;
	}

	public String getYaxis() {
		return yaxis1+","+yaxis2+","+yaxis3+","+yaxis4+","+yaxis5+","+yaxis6;
	}
	
	public String getYaxis1() {
		return yaxis1;
	}

	public void setYaxis1(String yaxis1) {
		this.yaxis1 = yaxis1;
	}

	public String getYaxis2() {
		return yaxis2;
	}

	public void setYaxis2(String yaxis2) {
		this.yaxis2 = yaxis2;
	}

	public String getYaxis3() {
		return yaxis3;
	}

	public void setYaxis3(String yaxis3) {
		this.yaxis3 = yaxis3;
	}

	public String getYaxis4() {
		return yaxis4;
	}

	public void setYaxis4(String yaxis4) {
		this.yaxis4 = yaxis4;
	}

	public String getYaxis5() {
		return yaxis5;
	}

	public void setYaxis5(String yaxis5) {
		this.yaxis5 = yaxis5;
	}

	public String getYaxis6() {
		return yaxis6;
	}

	public void setYaxis6(String yaxis6) {
		this.yaxis6 = yaxis6;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
}