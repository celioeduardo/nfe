package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AdicaoConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Adicao.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Adicao adicao = (Adicao) source;
		convert("nAdicao",adicao.numero(),writer,context);
		convert("nSeqAdic",adicao.sequencia(),writer,context);
		convert("cFabricante",adicao.fabricante(),writer,context);
		
		adicao.desconto().ifPresent(v->convert("vDescDI",v,writer,context));
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {		
		return null;
		
	}
}