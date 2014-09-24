package com.hadrion.nfe.port.adapters.portal.autorizacao;

import static com.hadrion.util.xml.XmlUtil.parseXml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.ReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;

class RetornoAutorizacaoDeserializador {
	
	private Document doc;
	
	RetornoAutorizacaoDeserializador(String xml){
		doc = parseXml(xml);
	}

	RetornoAutorizacao deserializar() {
		if (existeNo("nRec"))
			return new RetornoAutorizacao(
					new ReciboLote(new NumeroReciboLote(texto("nRec"))));
		else 
			return new RetornoAutorizacao(
					new Mensagem(inteiro("cStat"), texto("xMotivo")));
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
