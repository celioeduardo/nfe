package com.hadrion.nfe.port.adapters.xml;

import org.apache.commons.lang.StringUtils;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CepConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Cep.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Cep cep = (Cep) source;
		String valor = StringUtils.leftPad(
				String.valueOf(cep.numero()), 8, "0");
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new Cep(Long.parseLong(reader.getValue()));
	}

}
