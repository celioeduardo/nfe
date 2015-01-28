package com.hadrion.nfe.port.adapters.portal.evento;

import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class EventoConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Evento.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Evento evento = (Evento) source;
		
		writer.addAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe");
		writer.addAttribute("versao", evento.versao());
		
		writer.startNode("infEvento");
		writer.addAttribute("Id", evento.id());
		convert("cOrgao", evento.codigoOrgaoRecepcao(), writer, context);
		convert("tpAmb", evento.ambiente(), writer, context);
		convert("CNPJ", evento.cnpjAutor(), writer, context);
		convert("chNFe", evento.chaveAcesso(), writer, context);
		convert("dhEvento", evento.dataHora(), writer, context);
		convert("tpEvento", evento.tipo(), writer, context);
		convert("nSeqEvento", evento.sequencia(), writer, context);
		convert("verEvento", evento.versao(), writer, context);
		writer.startNode("detEvento");
		writer.addAttribute("versao", evento.versao());
		convert("descEvento", evento.descricao(), writer, context);
		convert("nProt", evento.numeroProtocolo(), writer, context);
		convert("xJust", evento.justificativa(), writer, context);
		writer.endNode();
		writer.endNode();
	}

	@Override
	public Evento unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}

}
