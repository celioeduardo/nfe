package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculoSt;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DeterminacaoBaseCalculoStConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return DeterminacaoBaseCalculoSt.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		DeterminacaoBaseCalculoSt obj = (DeterminacaoBaseCalculoSt) source;
		String valor = String.valueOf(obj.codigo());
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return DeterminacaoBaseCalculoSt.obterPeloCodigo(Integer.parseInt(reader.getValue()));
	}

}
