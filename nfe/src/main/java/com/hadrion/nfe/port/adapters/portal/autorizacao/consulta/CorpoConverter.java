package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

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
		
		writer.startNode("consReciNFe");
		writer.addAttribute("versao", corpo.versaoDados());
		writer.addAttribute("xmlns", corpo.uriPortal());
		
		writer.startNode("tpAmb");
		writer.setValue(String.valueOf(corpo.ambiente().codigo()));
		writer.endNode();
		
		writer.startNode("nRec");
		writer.setValue(corpo.numeroReciboLote());
		writer.endNode();
		
		writer.endNode(); //enviNFe
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}

}
