package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DeterminacaoBaseCalculoConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return DeterminacaoBaseCalculo.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		DeterminacaoBaseCalculo obj = (DeterminacaoBaseCalculo) source;
		String valor = String.valueOf(obj.codigo());
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return DeterminacaoBaseCalculo.obterPeloCodigo(Integer.parseInt(reader.getValue()));
	}

}
