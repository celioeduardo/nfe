package com.hadrion.nfe.port.adapters.xml;

import org.apache.commons.lang.StringUtils;

import com.hadrion.nfe.tipos.Cnpj;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CnpjConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Cnpj.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Cnpj cnpj = (Cnpj) source;
		String valor = StringUtils.leftPad(
				String.valueOf(cnpj.numero()), 14, "0");
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new Cnpj(Long.parseLong(reader.getValue()));
	}

}
