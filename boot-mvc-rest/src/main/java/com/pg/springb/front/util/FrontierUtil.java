package com.pg.springb.front.util;

import java.io.InputStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontierUtil {
	
	private static final Logger log = LoggerFactory.getLogger(FrontierUtil.class);
	
	public static String getHostName(){
		try{
			return InetAddress.getLocalHost().getHostName();
		}catch(Exception e){
			log.error("getHostName error:"+e.getMessage());}
		return "";
	}
	
	public static String generateUploadFileName(String origFileName,String uploader){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
		LocalDateTime ldtNow=LocalDateTime.now();
		return ldtNow.format(formatter)+"-"+uploader+"-"+origFileName.replace(" ","_");
	}
	
	public static String generateIntervalReportFileName(String clientId, LocalDateTime currTs){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		return clientId + "_CITI_MIFID_II_recaps_" +currTs.format(formatter)+
				FrontierConstants.REPORT_FILE_EXTENSION;
	}
	
	public static String generateDailyReportFileName(String clientId, LocalDateTime fromTs, LocalDateTime toTs){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		return clientId + "_CITI_MIFID_II_recaps_" + fromTs.format(formatter)+"_To_"+ 
				toTs.format(formatter)+FrontierConstants.REPORT_FILE_EXTENSION;
	}
	
	public static String generateResendReportFileName(String clientId, LocalDateTime fromTs, LocalDateTime toTs, LocalDateTime currTs){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
		return clientId + "_CITI_MIFID_II_recaps_" + fromTs.format(formatter) +"_To_"+
				toTs.format(formatter)+"_"+convertLocalDateTimeToEpoch(currTs)+
				FrontierConstants.REPORT_FILE_EXTENSION;
	}
	
	public static String generateEmailSubject(String clientId, String reportType, LocalDateTime currentTs){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		
		if(FrontierConstants.CLIENT_REPORT_TYPE[0].equalsIgnoreCase(reportType)){
			return clientId + "/CITI MIFID II recaps (interval) – "+currentTs.format(formatter);
		}
		else if(FrontierConstants.CLIENT_REPORT_TYPE[1].equalsIgnoreCase(reportType)){
			formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			return clientId +"/CITI MIFID II recaps (Daily Summary) – "+currentTs.format(formatter);
		}else {
			return clientId +"/CITI MIFID II recaps ("+reportType+") – "+currentTs.format(formatter);
		}
	}
	
	public static String generateEmailMessage(String organization){
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream inptStrm = classLoader.getResourceAsStream("interval_disclaimer.txt");
			return IOUtils.toString(inptStrm, StandardCharsets.UTF_8).replace("XXXX", organization);
			}catch (Exception ex) {
				log.error(ex.getMessage());
				}
		return"";
	}
	
	public static Long convertLocalDateTimeToEpoch(LocalDateTime currentTs) {
		long epochSec = currentTs.atZone(Clock.systemUTC().getZone()).toInstant().getEpochSecond();
		Integer nanos = currentTs.getNano();
		Long nanoTime = (epochSec * 1000000000) + nanos;
		return nanoTime;
	}
	
	public static boolean isTimeForInterval(int intrvlMint, int currentMint){	
		
		if (intrvlMint == 0)
			return false;
		if(currentMint == 0 ) 
			currentMint = 60;
		if(currentMint%intrvlMint== 0)
			return true;
		else
			return false;
	}
	
	public static boolean isTimeForDaily(String dailyReportTime, LocalDateTime lastEmailSentTs, LocalDateTime currentTs) {
		
		if (dailyReportTime == null)
			return false;
		if(lastEmailSentTs != null && currentTs.getDayOfMonth() == lastEmailSentTs.getDayOfMonth()) {
			return false;
		}
		try{
			int rptTime = Integer.parseInt(dailyReportTime);
			int scheduledHour = rptTime / 100;
			int scheduledMinute = rptTime % 100;
			int currentHour = currentTs.getHour();
			int currentMinute = currentTs.getMinute();
			if(scheduledHour == currentHour && currentMinute - scheduledMinute >= 0 && currentMinute - scheduledMinute < 3 )
				return true;
			}catch (Exception e){
				log.error("error in isTimeForDaily:" + e.getMessage());
				}
		return false;
		}
}
