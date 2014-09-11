package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CstPisConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return CstCofins.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		CstCofins obj = (CstCofins) source;
		String valor = obj.codigo() < 10 ? 
				"0"+obj.codigo() : String.valueOf(obj.codigo());
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return CstCofins.obterPeloCodigo(Integer.parseInt(reader.getValue()));
	}

}
