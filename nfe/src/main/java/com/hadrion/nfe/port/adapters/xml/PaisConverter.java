package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PaisConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Pais.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Pais pais = (Pais) source;
		writer.startNode("cPais");
		writer.setValue(String.valueOf(pais.codigo()));
		writer.endNode();
		writer.startNode("xPais");
		writer.setValue(pais.nome());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		reader.moveDown();
		Long codigo = Long.parseLong(reader.getValue());
		reader.moveUp();
		reader.moveDown();
		String nome = reader.getValue();
		reader.moveUp();
		return new Pais(codigo,nome);
	}

}
