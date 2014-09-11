package com.hadrion.nfe.port.adapters.xml.transporte;

import com.hadrion.nfe.dominio.modelo.nf.transporte.ModalidadeFrete;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ModalidadeFreteConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return ModalidadeFrete.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ModalidadeFrete obj = (ModalidadeFrete) source;
		String valor = String.valueOf(obj.codigo());
		writer.setValue(valor);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return ModalidadeFrete.obterPeloCodigo(Integer.parseInt(reader.getValue()));
	}

}
