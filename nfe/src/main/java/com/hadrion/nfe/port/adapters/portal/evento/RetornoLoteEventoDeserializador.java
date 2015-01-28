package com.hadrion.nfe.port.adapters.portal.evento;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
	
	private String xml;
	private XStream xstream;

	RetornoLoteEventoDeserializador(String xml){
		this.xml = xml;
	}

	RetornoLoteEvento deserializar() {
		return (RetornoLoteEvento) xstream().fromXML(xml);
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
		
		Document doc = XmlUtil.parseXml(xml);
		doc.normalizeDocument();
		NodeList nodes = doc.getElementsByTagName("infEvento");
		
		List<RetornoEvento> eventos = new LinkedList<RetornoEvento>();
		for (int i = 0; i < nodes.getLength() ; i++) {
			String xmlInfEvento = XmlUtil.xmlParaString(nodes.item(i));
			RetornoEvento e = (RetornoEvento) xstream().fromXML(xmlInfEvento);
			eventos.add(e);
		}
		
		return new RetornoLoteEvento(
				new Mensagem(cStat, xMotivo), 
				eventos);
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
