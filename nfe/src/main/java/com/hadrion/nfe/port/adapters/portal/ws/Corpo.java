package com.hadrion.nfe.port.adapters.portal.ws;

import static com.hadrion.util.xml.XmlUtil.parseXml;
import static com.hadrion.util.xml.XmlUtil.xmlParaString;

import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalConverter;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;
import com.thoughtworks.xstream.XStream;

public class Corpo {
	private Lote lote;
	private Set<NotaFiscal> notas;
	private Certificado certificado;
	private XStream xstream;
	NotaFiscalSerializador serializador;
	
	public Corpo(Lote lote, Set<NotaFiscal> notas, Certificado certificado) {
		super();
		this.lote = lote;
		this.notas = notas;
		this.certificado = certificado;
		serializador =  new NotaFiscalSerializador(certificado());
	}

	public String autorizacao() {
		Document doc = parseXml(xstream().toXML(this));
		Node enviNfe = doc.getElementsByTagName("enviNFe").item(0);
		
		for (NotaFiscal nf: notas) {
			Document notaXml = parseXml(serializador.serializar(nf));
			enviNfe.appendChild(doc.importNode(notaXml.getFirstChild(), true));
		}
		return xmlParaString(doc);
	}
	
	
	protected Set<NotaFiscal> notas(){
		return notas;
	}
	
	protected Certificado certificado(){
		return certificado;
	}
	
	protected Ambiente ambiente(){
		return lote.ambiente();
	}
	
	private XStream xstream(){
		if (xstream == null){
			xstream = XStreamFabrica.criar();
		}
		
		//TODO Obter versão do aplicativo do contribuinte
		String versaoAplicativo = "1.0";
		xstream.registerConverter(new NotaFiscalConverter(versaoAplicativo));
		xstream.registerConverter(new CorpoConverter());
		xstream.alias("nfeDadosMsg", Corpo.class);
		return xstream;
	}
	
	protected String versaoDados(){
		return "3.10";
	}
	
	protected String uriWebService(){
		return "http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao";
	}

	protected String idLote() {
		return lote.numero();
	}

	protected String chamadaSincrona() {
		return "0";
	}

	protected String uriPortal() {
		return "http://www.portalfiscal.inf.br/nfe";
	}

	protected String versaoLayout() {
		return "2.00";
	}
}
