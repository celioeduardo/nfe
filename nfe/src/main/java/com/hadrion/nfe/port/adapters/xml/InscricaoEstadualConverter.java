package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class InscricaoEstadualConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return InscricaoEstadual.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		InscricaoEstadual ie = (InscricaoEstadual) source;
		if (ie.isento())
			writer.setValue("ISENTO");
		else
			writer.setValue(ie.obterSomenteDigitos());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new InscricaoEstadual(reader.getValue());
	}

}
