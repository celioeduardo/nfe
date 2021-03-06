package com.hadrion.nfe.port.adapters.xml;

import static com.hadrion.util.DataUtil.formatarComTimezone;
import static com.hadrion.util.DataUtil.parseComTimezone;

import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter{
	//private SimpleDateFormat formatData;
	
//	public DateConverter() {
//		 formatData = new SimpleDateFormat("yyyy-MM-dd");
//	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Date.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Date data = (Date) source;
		writer.setValue(formatarComTimezone(data));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return parseComTimezone(reader.getValue());
	}

}
