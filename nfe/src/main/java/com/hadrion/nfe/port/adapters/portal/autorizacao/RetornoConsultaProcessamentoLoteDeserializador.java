package com.hadrion.nfe.port.adapters.portal.autorizacao;

import static com.hadrion.util.xml.XmlUtil.parseXml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;

class RetornoConsultaProcessamentoLoteDeserializador {
	
	private Document doc;
	
	RetornoConsultaProcessamentoLoteDeserializador(String xml){
		doc = parseXml(xml);
	}

	RetornoConsultaProcessamentoLote deserializar() {
		//TODO implementar
		return null;
	}
	
	private String texto(String nome){
		Node no = obterNo(nome);
		return no != null ? no.getTextContent() : null;
	}
	
	private Integer inteiro(String nome){
		Node no = obterNo(nome);
		return no != null ? Integer.parseInt(no.getTextContent()) : null;
	}
	private Node obterNo(String nome){
		NodeList list = doc.getDocumentElement().getElementsByTagName(nome);
		if (list.getLength() > 0)
			return list.item(0);
		return null;
	}
	
	private boolean existeNo(String nome){
		return obterNo(nome) != null;
	}
	
}
