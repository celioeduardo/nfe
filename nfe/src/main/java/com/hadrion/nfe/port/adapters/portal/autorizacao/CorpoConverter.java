package com.hadrion.nfe.port.adapters.portal.autorizacao;

import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class CorpoConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Corpo.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		Corpo corpo = (Corpo) source;
		
		writer.addAttribute("xmlns", corpo.uriWebService());
		
		writer.startNode("enviNFe");
		writer.addAttribute("versao", corpo.versaoDados());
		writer.addAttribute("xmlns", corpo.uriPortal());
		
		writer.startNode("idLote");
		writer.setValue(corpo.idLote());
		writer.endNode();
		
		writer.startNode("indSinc");
		writer.setValue(corpo.chamadaSincrona());
		writer.endNode();
		
		writer.endNode(); //enviNFe
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}

}
