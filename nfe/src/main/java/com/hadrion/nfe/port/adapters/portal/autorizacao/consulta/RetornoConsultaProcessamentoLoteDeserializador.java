package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.port.adapters.xml.ProtocoloNotaProcessadaConverter;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.util.xml.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class RetornoConsultaProcessamentoLoteDeserializador extends AbstractConverter{
	
	private String xml;
	private XStream xstream;
	
	RetornoConsultaProcessamentoLoteDeserializador(String xml){
		this.xml = xml; 
	}

	RetornoConsultaProcessamentoLote deserializar() {
		return (RetornoConsultaProcessamentoLote) xstream().fromXML(xml);
	}
	
	private XStream xstream(){
		if (xstream == null){
			xstream = XStreamFabrica.criar();
		}
		
		xstream.registerConverter(this);
		xstream.registerConverter(new ProtocoloNotaProcessadaConverter());
		xstream.alias("nfeRetAutorizacaoLoteResult", RetornoConsultaProcessamentoLote.class);
		xstream.alias("protNFe", ProtocoloNotaProcessada.class);
		return xstream;
	}
	

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Ambiente ambiente = null;
		int cStat = -1;
		String xMotivo = null;
		int cMsg = -1;
		String xMsg = null;
		
		List<ProtocoloNotaProcessada> protocolos = new LinkedList<ProtocoloNotaProcessada>();
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();

				if (reader.getNodeName().equals("tpAmb"))
					ambiente = (Ambiente) context.convertAnother(reader.getValue(), Ambiente.class);
				if (reader.getNodeName().equals("cStat"))
					cStat = Integer.parseInt(reader.getValue());
				if (reader.getNodeName().equals("xMotivo"))
					xMotivo = reader.getValue();
				if (reader.getNodeName().equals("cMsg"))
					cMsg = Integer.parseInt(reader.getValue());
				if (reader.getNodeName().equals("xMsg"))
					xMsg = reader.getValue();
				reader.moveUp();
			}
			reader.moveUp();
		}
		
		Document doc = XmlUtil.parseXml(xml);
		doc.normalizeDocument();
		NodeList nodes = doc.getElementsByTagName("protNFe");
		
		for (int i = 0; i < nodes.getLength() ; i++) {
			String xmlProtNfe = XmlUtil.xmlParaString(nodes.item(i));
			ProtocoloNotaProcessada p = (ProtocoloNotaProcessada) 
					xstream().fromXML(xmlProtNfe);
			protocolos.add(p.definirXml(xmlProtNfe));
		}
		
		return new RetornoConsultaProcessamentoLote(
				ambiente, 
				mensagem(cStat, xMotivo), 
				mensagemSefaz(cMsg, xMsg), 
				protocolos);
	}
	
	private Mensagem mensagem(int codigo, String descricao){
		if (codigo != -1 || descricao != null)
			return new Mensagem(codigo, descricao);
		return null;
	}
	private MensagemSefaz mensagemSefaz(int codigo, String descricao){
		if (codigo != -1 || descricao != null)
			return new MensagemSefaz(codigo, descricao);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return RetornoConsultaProcessamentoLote.class.isAssignableFrom(type);
	}
	
}
