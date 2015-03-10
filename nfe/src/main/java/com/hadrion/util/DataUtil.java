package com.hadrion.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DataUtil {
	
	public static Date janeiro(int dia, int ano){
		return data(dia,Calendar.JANUARY,ano); 
	}
	public static Date fevereiro(int dia, int ano){
		return data(dia,Calendar.FEBRUARY,ano); 
	}
	public static Date marco(int dia, int ano){
		return data(dia,Calendar.MARCH,ano); 
	}
	public static Date abril(int dia, int ano){
		return data(dia,Calendar.APRIL,ano); 
	}
	public static Date maio(int dia, int ano){
		return data(dia,Calendar.MAY,ano); 
	}
	public static Date junho(int dia, int ano){
		return data(dia,Calendar.JUNE,ano); 
	}
	public static Date julho(int dia, int ano){
		return data(dia,Calendar.JULY,ano); 
	}
	public static Date agosto(int dia, int ano){
		return data(dia,Calendar.AUGUST,ano); 
	}
	public static Date setembro(int dia, int ano){
		return data(dia,Calendar.SEPTEMBER,ano); 
	}
	public static Date outubro(int dia, int ano){
		return data(dia,Calendar.OCTOBER,ano); 
	}
	public static Date novembro(int dia, int ano){
		return data(dia,Calendar.NOVEMBER,ano); 
	}
	public static Date dezembro(int dia, int ano){
		return data(dia,Calendar.DECEMBER,ano); 
	}
	public static Date agora(){
		return Calendar.getInstance().getTime(); 
	}
	
	public static Date data(String data){
		return data(data,"dd/MM/yy");
	}
	
	public static Date data(String data, String formato){
		DateFormat formatter = new SimpleDateFormat(formato);
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}
	
	public static Date dataHora(String dataHora){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		try {
			return formatter.parse(dataHora);
		} catch (ParseException e) {
			return null;
		}	
		
	}
	/**
	 * Cria uma data com timezone
	 * @param dataHora Data no formato dd/MM/yy HH:mm:ss
	 * @param timezone Exemplo: GMT-03:00
	 * @return Data com Timezone
	 */
	public static Date dataHora(String dataHora, String timezone){
		TimeZone tz = TimeZone.getTimeZone(timezone);
		return dataHora(dataHora,tz);	
	}
	public static Date dataHora(String dataHora, TimeZone timezone){
		return dataHora(dataHora,DateTimeZone.forTimeZone(timezone));
	}
	public static Date dataHora(String dataHora, DateTimeZone timezone){
		DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yy HH:mm:ss");
		DateTime dateTime = dtf.withZone(timezone).parseDateTime(dataHora);
		return dateTime.toDate();
	}
	
	public static String formatarData(Date date){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	public static String formatarData(Date date, String formato){
		DateFormat formatter = new SimpleDateFormat(formato);
		return formatter.format(date);
	}
	
	public static Date parseData(String data){
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String formatarComTimezone(Date date){
		DateTime d = new DateTime(date);
		DateTimeFormatter dtf = ISODateTimeFormat
				.dateTimeNoMillis()
				.withZone(d.getZone());
		return d.toString(dtf);
	}
	
	public static String formatarComTimezone(Date date,TimeZone timezone){
		return formatarComTimezone(date, DateTimeZone.forTimeZone(timezone));
	}
	public static String formatarComTimezone(Date date,DateTimeZone timezone){
		DateTimeFormatter dtf = ISODateTimeFormat.dateTimeNoMillis();	
		DateTime d = new DateTime(date);
		return d.toString(dtf.withZone(timezone));
	}
	
	public static Date parseComTimezone(String data){
		DateTimeFormatter dtf = ISODateTimeFormat.dateTimeNoMillis();	
		DateTime dateTime = dtf.parseDateTime(data);
		return dateTime.toDate();
	}
	private static Date data(int dia, int mes, int ano){
		Calendar calendar = new GregorianCalendar(ano,mes,dia,0,0,0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}	
}
