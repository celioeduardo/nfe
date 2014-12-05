package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.thoughtworks.xstream.XStream;

public class Corpo {
	private Lote lote;
	private XStream xstream;
	
	public Corpo(Lote lote) {
		super();
		this.lote = lote;
	}

	public String gerar() {
		return xstream().toXML(this);
	}
	
	protected Ambiente ambiente(){
		return lote.ambiente();
	}
	
	private XStream xstream(){
		if (xstream == null)
			xstream = XStreamFabrica.criar();
		
		xstream.registerConverter(new CorpoConverter());
		xstream.alias("nfeDadosMsg", Corpo.class);
		return xstream;
	}
	
	protected String versaoDados(){
		return "3.10";
	}
	
	protected String uriWebService(){
		return "http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao";
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

	public String numeroReciboLote() {
		return String.valueOf(lote.numeroRecibo());
	}
}
