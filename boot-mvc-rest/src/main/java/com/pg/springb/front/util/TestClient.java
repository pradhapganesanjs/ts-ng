package com.pg.springb.front.util;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class TestClient {

	public static void main(String[] args) {
		/*
		// TODO Auto-generated method stub
		System.out.println(convertNanoTimeStampToLocalDateTime(1514902229101000000L));
		System.out.println(convertNanoTimeStampToLocalDateTime(1514677331132000000L));
		System.out.println(convertNanoTimeStampToLocalDateTime(1514313779451000000L));
		
		System.out.println(0-0);
		System.out.println(5-2);
		System.out.println(2-5);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			Date date = new Date();
		    System.out.println("local format***: " + simpleDateFormat.format(date));
		    
		    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		    System.out.println("utc format***: " + simpleDateFormat.format(date));
		    
		    LocalDateTime now = LocalDateTime.now();
		    LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		    
		    
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	        System.out.println("Local:"+now.format(formatter));
	        System.out.println("utc:"+utcNow.format(formatter));
	        
	       
		
	     
		   
		} catch (Exception ex) {
		   // Logger.getLogger(JavaApplication203.class.getName()).log(Level.SEVERE, null, ex);
		}
	*/
		System.out.println("getYesterdayTime:"+getYesterdayTime());
	}
	
	
	private static LocalDateTime convertNanoTimeStampToLocalDateTime(Long timeInLong) {

		Long timeInMillis = timeInLong / 1000000;
		Integer timeInNanos = (int) (timeInLong % 1000000000);

		Instant inst = Instant.ofEpochMilli(timeInMillis);
		LocalDateTime ldt = LocalDateTime.ofInstant(inst, Clock.systemUTC().getZone());
		ldt = ldt.withNano(timeInNanos);

		return ldt;

	}
	
	private static Long convertLocalDateTimeToEpoch(LocalDateTime currentTs) {
		//long epochSec = currentTs.atZone(Clock.systemUTC().getZone()).toInstant().getEpochSecond();
        long epochSec = currentTs.atZone(ZoneOffset.UTC).toInstant().getEpochSecond(); 

		Integer nanos = currentTs.getNano();
		Long nanoTime = (epochSec * 1000000000) + nanos;
		return nanoTime;
	}
	
	 public static Date fromLocalDateInUTC(LocalDate date) {
		    return Date.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));
	}
	 
	 public static LocalDate asLocalDateInUTC(Date date) {
		    ZoneId UTC = ZoneId.of("Z");
		    return date.toInstant().atZone(UTC).toLocalDate();
	 }
	 
	 private static Long getYesterdayTime(){		
			//return convertLocalDateTimeToEpoch(LocalDateTime.now().minusDays(1));			
		 return convertLocalDateTimeToEpoch(LocalDateTime.now(Clock.systemUTC()).minusDays(1));
		}
	 
	 public static LocalDateTime getLocalDatewithFixedTime(int hour, int minute) {
		 LocalDateTime ldtSchdl = LocalDateTime.now().withHour(17).withMinute(15);
		 Long startTimeLong = convertLocalDateTimeToEpoch(ldtSchdl);
		 
		 System.out.println("startTimeLong:"+startTimeLong);
		 //LocalDateTime ldtSchdl.now()
		 
		 return ldtSchdl;
	 }
}
