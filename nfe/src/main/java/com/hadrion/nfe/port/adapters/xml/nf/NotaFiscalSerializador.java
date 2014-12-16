package com.hadrion.nfe.port.adapters.xml.nf;

import java.io.StringWriter;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.xml.DestinatarioConverter;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.port.adapters.xml.assinatura.Assinador;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class NotaFiscalSerializador {
	private XStream xstream;
	private Certificado certificado;
	
	public NotaFiscalSerializador() {
		this(null);
	}
	
	public NotaFiscalSerializador(Certificado certificado) {
		this.certificado = certificado;
	}

	public String serializar(NotaFiscal nf){
		//String xml = xstream().toXML(new Nfe(nf)); 
		String xml =  xmlCompacto(xstream(nf.ambiente()), new Nfe(nf));
		if (certificado != null)
			xml = Assinador.assinarNfe(xml, certificado);
		return xml;
	}
	
	private String xmlCompacto(XStream x, Object o) {
		StringWriter sw = new StringWriter();
		x.marshal(o,  new CompactWriter(sw));
		return sw.toString();
	}
	
	//TODO implementar versão do aplicativo do contribuinte
	private String versaoAplicativo(){
		return "1.0";
	}
	
	private XStream xstream(Ambiente ambiente){
		if (xstream == null)
			xstream = XStreamFabrica.criar();
		xstream.processAnnotations(Nfe.class);
		xstream.autodetectAnnotations(true);
		xstream.registerConverter(
				new NotaFiscalConverter(versaoAplicativo()));
		xstream.registerConverter(new NfeConverter());
		xstream.registerConverter(new DestinatarioConverter(ambiente));
		return xstream;
	}
	
}
