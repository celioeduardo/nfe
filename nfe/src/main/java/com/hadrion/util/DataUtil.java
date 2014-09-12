package com.hadrion.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
	
	private static Date data(int dia, int mes, int ano){
		Calendar calendar = new GregorianCalendar(ano,mes,dia,0,0,0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}
	
	public static Date dataHota(String dataHora){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		try {
			return formatter.parse(dataHora);
		} catch (ParseException e) {
			return null;
		}	
		
	}


	
	
	
}
