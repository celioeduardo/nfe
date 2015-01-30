package com.hadrion.nfe.port.adapters.portal.evento;

import java.io.StringWriter;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.port.adapters.xml.assinatura.Assinador;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class EventoSerializador {
	private XStream xstream;
	private Certificado certificado;
	
	public EventoSerializador() {
		this(null);
	}
	
	public EventoSerializador(Certificado certificado) {
		this.certificado = certificado;
	}

	public String serializar(Evento evento){
		String xml = xmlCompacto(xstream(), evento);
		if (certificado != null)
			xml = Assinador.assinarEvento(xml, certificado);
		return xml;
	}
	
	private String xmlCompacto(XStream x, Object o) {
		StringWriter sw = new StringWriter();
		x.marshal(o,  new CompactWriter(sw));
		return sw.toString();
	}
	
	private XStream xstream(){
		if (xstream == null)
			xstream = XStreamFabrica.criar();
		xstream.registerConverter(new EventoCancelamentoConverter());
		xstream.registerConverter(new EventoCartaCorrecaoConverter());
		xstream.alias("evento", EventoCancelamento.class);
		xstream.alias("evento", EventoCartaCorrecao.class);
		return xstream;
	}
	
}
