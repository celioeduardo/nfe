package com.hadrion.nfe.port.adapters.portal.inutilizacao;

import java.io.StringWriter;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.port.adapters.xml.assinatura.Assinador;
import com.hadrion.nfe.tipos.Cnpj;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class InutilizacaoSerializador {
	private XStream xstream;
	private Certificado certificado;
	
	public InutilizacaoSerializador() {
		this(null);
	}
	
	public InutilizacaoSerializador(Certificado certificado) {
		this.certificado = certificado;
	}

	public String serializar(Inutilizacao inutilizacao, Uf uf, Cnpj cnpj){
		String xml = xmlCompacto(xstream(inutilizacao, uf, cnpj), inutilizacao);
		if (certificado != null)
			xml = Assinador.assinarInutilizacao(xml, certificado);
		return xml;
	}
	
	private String xmlCompacto(XStream x, Object o) {
		StringWriter sw = new StringWriter();
		x.marshal(o,  new CompactWriter(sw));
		return sw.toString();
	}
	
	private XStream xstream(Inutilizacao inutilizacao, Uf uf, Cnpj cnpj){
		if (xstream == null)
			xstream = XStreamFabrica.criar();
		xstream.registerConverter(new InutilizacaoConverter(uf,cnpj));
		xstream.alias("inutNFe", Inutilizacao.class);
		return xstream;
	}
	
}
