package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MunicipioConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Municipio.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Municipio mun = (Municipio) source;
		writer.startNode("cMun");
		writer.setValue(String.valueOf(mun.codigo()));
		writer.endNode();
		writer.startNode("xMun");
		writer.setValue(mun.nome());
		writer.endNode();
		writer.startNode("UF");
		writer.setValue(mun.uf().toString());
		writer.endNode();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		reader.moveDown();
		int codigo = Integer.parseInt(reader.getValue());
		reader.moveUp();
		reader.moveDown();
		String nome = reader.getValue();
		reader.moveUp();
		reader.moveDown();
		Uf uf = Uf.valueOf(reader.getValue());
		
		return new Municipio(codigo,nome,uf);
	}

}
