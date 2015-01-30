package com.hadrion.nfe.port.adapters.portal.evento;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoEvento;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoLoteEvento;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.util.xml.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class RetornoLoteEventoDeserializador extends AbstractConverter{
	
	private String xmlRetorno;
	private Document xmlEnvio;
	private XStream xstream;

	RetornoLoteEventoDeserializador(Document xmlEnvio,
			String xmlRetorno){
		this.xmlEnvio = xmlEnvio;
		this.xmlRetorno = xmlRetorno;
	}

	RetornoLoteEvento deserializar() {
		return (RetornoLoteEvento) xstream().fromXML(xmlRetorno);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		int cStat = -1;
		String xMotivo = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if (reader.getNodeName().equals("cStat"))
					cStat = Integer.parseInt(reader.getValue());
				if (reader.getNodeName().equals("xMotivo"))
					xMotivo = reader.getValue();
				reader.moveUp();
			}
			reader.moveUp();
		}
		
		Document doc = XmlUtil.parseXml(xmlRetorno);
		doc.normalizeDocument();
		NodeList nodes = doc.getElementsByTagName("infEvento");
		
		List<RetornoEvento> eventos = new LinkedList<RetornoEvento>();
		for (int i = 0; i < nodes.getLength() ; i++) {
			String xmlInfEvento = XmlUtil.xmlParaString(nodes.item(i));
			RetornoEvento e = (RetornoEvento) xstream().fromXML(xmlInfEvento);
			e.definirXmlRetorno(xmlInfEvento);
			e.definirXmlEnvio(obterXmlEnvio(e.chaveAcesso()));
			eventos.add(e);
		}
		
		return new RetornoLoteEvento(
				new Mensagem(cStat, xMotivo), 
				eventos);
	}
	
	private String obterXmlEnvio(ChaveAcesso chaveAcesso){
		if (xmlEnvio == null || chaveAcesso == null) return null;
		NodeList lista = xmlEnvio.getElementsByTagName("infEvento");
		for (int i=0; i < lista.getLength(); i++){
			Element el = (Element)lista.item(i);
			NodeList chaves = el.getElementsByTagName("chNFe");
			if (chaves.getLength() == 1 && 
					chaves.item(0).getTextContent().equals(chaveAcesso.chave())){
				return XmlUtil.xmlParaString(el);
			}
		}
		return null;
	}
	
	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return RetornoLoteEvento.class.isAssignableFrom(type);
	}
	
	private XStream xstream(){
		if (xstream == null){
			xstream = XStreamFabrica.criar();
		}
		
		xstream.registerConverter(this);
		xstream.registerConverter(new RetornoEventoConverter());
		xstream.alias("nfeRecepcaoEventoResult", RetornoLoteEvento.class);
		xstream.alias("infEvento", RetornoEvento.class);
		return xstream;
	}
	
	
	
}
