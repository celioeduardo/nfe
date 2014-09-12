package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AmbienteConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Ambiente.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Ambiente obj = (Ambiente) source;
		String valor = String.valueOf(obj.codigo());
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return Ambiente.obterPeloCodigo(Integer.parseInt(reader.getValue()));
	}

}
