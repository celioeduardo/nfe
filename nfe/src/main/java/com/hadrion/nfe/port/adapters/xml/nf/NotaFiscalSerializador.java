package com.hadrion.nfe.port.adapters.xml.nf;

import java.io.StringWriter;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.port.adapters.xml.assinatura.Assinador;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class NotaFiscalSerializador {
	private Ambiente ambiente;
	private XStream xstream;
	private Certificado certificado;
	
	public NotaFiscalSerializador(Ambiente ambiente) {
		this(ambiente,null);
	}
	
	public NotaFiscalSerializador(Ambiente ambiente, Certificado certificado) {
		this.ambiente = ambiente;
		this.certificado = certificado;
	}

	public String serializar(NotaFiscal nf){
		//String xml = xstream().toXML(new Nfe(nf)); 
		String xml =  xmlCompacto(xstream(), new Nfe(nf));
		if (certificado != null)
			xml = Assinador.assinarNfe(xml, certificado);
		return xml;
	}
	
	private String xmlCompacto(XStream x, Object o) {
		StringWriter sw = new StringWriter();
		x.marshal(o,  new CompactWriter(sw));
		return sw.toString();
	}
	
	private Ambiente ambiente(){
		return this.ambiente;
	}

	//TODO implementar versão do aplicativo do contribuinte
	private String versaoAplicativo(){
		return "1.0";
	}
	
	private XStream xstream(){
		if (xstream == null)
			xstream = XStreamFabrica.criar();
		xstream.processAnnotations(Nfe.class);
		xstream.registerConverter(
				new NotaFiscalConverter(ambiente(),versaoAplicativo()));
		xstream.registerConverter(new NfeConverter());
		return xstream;
	}
	
	@XStreamAlias("NFe")
	static class Nfe {
		
		protected NotaFiscal notaFiscal;
		
		Nfe(NotaFiscal nf){
			this.notaFiscal = nf;
		}

		String uriPortal() {
			return "http://www.portalfiscal.inf.br/nfe";
		}

		String id() {
			return "NFe" + notaFiscal.chaveAcesso();
		}

		String versaoLayout() {
			return "3.10";
		}

		NotaFiscal notaFiscal() {
			return notaFiscal;
		}
		
	}
	
}
