package com.hadrion.nfe.dominio.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class CustomJacksonObjectMapper extends ObjectMapper {
	
	private static final long serialVersionUID = 1L;
	
	private final String mask = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	@PostConstruct 
	public void afterProps(){
		configurar();
	}
	
	public static ObjectMapper criar(){
		return new CustomJacksonObjectMapper().configurar();
	}
	
	private ObjectMapper configurar(){
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
		setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		//setSerializationInclusion(Include.NON_NULL);
		
		SimpleModule module = new SimpleModule("DateModule");
		module.addSerializer(Date.class, new JsonSerializer<Date>(){

			@Override
			public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
				SimpleDateFormat df = new SimpleDateFormat(mask);
				String data = df.format(value);
				String timezone = new SimpleDateFormat("Z").format(value);
				//Incluindo caracter ':' no timezone para manter compatibilidade com o Javascript 
				timezone = timezone.substring(0, 3) + ":" + timezone.substring(3,5); 
				jgen.writeString(data+timezone);
			}
		});
		
		this.registerModule(module);
		
		return this;
	}
}