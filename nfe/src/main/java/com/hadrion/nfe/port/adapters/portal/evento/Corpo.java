package com.hadrion.nfe.port.adapters.portal.evento;

import static com.hadrion.util.xml.XmlUtil.parseXml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.util.xml.XmlUtil;
import com.thoughtworks.xstream.XStream;

public class Corpo {
	private LoteEvento lote;
	private Certificado certificado;
	private XStream xstream;
	private EventoSerializador serializador;
	
	public Corpo(LoteEvento lote, Certificado certificado) {
		super();
		this.lote = lote;
		this.certificado = certificado;
		serializador =  new EventoSerializador(certificado());
	}

	public Document gerar() {
		Document doc = parseXml(xstream().toXML(this));
		Node enviLote = doc.getElementsByTagName("envEvento").item(0);
		
		for (Evento evento: lote.eventos()) {
			Document eventoXml = parseXml(serializador.serializar(evento));
			enviLote.appendChild(doc.importNode(eventoXml.getFirstChild(), true));
		}
		return doc;
		
	}
	
	public String gerarComoString(){
		return XmlUtil.xmlParaString (gerar());
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
		
		xstream.registerConverter(new EventoConverter());
		xstream.registerConverter(new CorpoConverter());
		xstream.alias("nfeDadosMsg", Corpo.class);
		return xstream;
	}
	
	protected String versaoDados(){
		return "1.00";
	}
	
	protected String uriWebService(){
		return "http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento";
	}

	protected String idLote() {
		return String.valueOf(lote.id());
	}

	
	protected String uriPortal() {
		return "http://www.portalfiscal.inf.br/nfe";
	}

	protected String versaoLayout() {
		return "1.00";
	}
}
