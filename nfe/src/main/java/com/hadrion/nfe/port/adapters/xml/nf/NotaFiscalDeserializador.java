package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.xml.DestinatarioConverter;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.thoughtworks.xstream.XStream;

public class NotaFiscalDeserializador {
	
	private XStream xstream;
	private Nfe nfe;
	private Ambiente ambiente;
	
	public NotaFiscalDeserializador(Ambiente ambiente){
		this.ambiente = ambiente;
	}
	
	public NotaFiscal deserializar(String xml){
		nfe = (Nfe) xstream(ambiente).fromXML(xml);
		return nfe.notaFiscal();
	} 
	
	private XStream xstream(Ambiente ambiente){
		if (xstream == null)
			xstream = XStreamFabrica.criar();
		xstream.processAnnotations(Nfe.class);
		xstream.registerConverter(new NotaFiscalConverter(null));
		xstream.registerConverter(new NfeConverter());
		xstream.registerConverter(new DestinatarioConverter(ambiente));
		xstream.alias("infNFe", NotaFiscal.class);
		xstream.alias("NFe", Nfe.class);
		return xstream;
	}
}
