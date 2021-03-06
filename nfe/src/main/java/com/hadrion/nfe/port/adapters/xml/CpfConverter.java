package com.hadrion.nfe.port.adapters.xml;

import org.apache.commons.lang.StringUtils;

import com.hadrion.nfe.tipos.Cpf;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CpfConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Cpf.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Cpf cpf = (Cpf) source;
		String valor = StringUtils.leftPad(
				String.valueOf(cpf.numero()), 11, "0");
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new Cpf(Long.parseLong(reader.getValue()));
	}

}
