package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador.Nfe;
import com.thoughtworks.xstream.XStream;

public class NotaFiscalDeserializador {
	private XStream xstream;
	
	public NotaFiscal deserializar(String xml){
		Nfe nfe = (Nfe) xstream().fromXML(xml);
		return nfe.notaFiscal();
	} 
	
	private XStream xstream(){
		if (xstream == null)
			xstream = XStreamFabrica.criar();
		xstream.processAnnotations(Nfe.class);
		xstream.registerConverter(new NotaFiscalConverter(null,null));
		xstream.registerConverter(new NfeConverter());
		xstream.alias("infNFe", NotaFiscal.class);
		xstream.alias("NFe", Nfe.class);
		return xstream;
	}
}
