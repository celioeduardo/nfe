package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class UfConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Uf.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Uf obj = (Uf) source;
		writer.setValue(obj.codigo());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return Uf.obterPeloCodigo(reader.getValue());
	}

}
