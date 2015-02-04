package com.hadrion.nfe.port.adapters.portal.ws;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Cabecalho {
	private Uf uf;
	private XStream xstream; 

	public Cabecalho(Uf uf) {
		super();
		this.uf = uf;
	}
	
	public String autorizacao(){
		return xstream().toXML(
				new NfeCabecMsg(uf, "3.10","http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao"));
	}

	public String retornoAutorizacao() {
		return xstream().toXML(
				new NfeCabecMsg(uf, "3.10","http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao"));	
	}
	
	public String evento() {
		return xstream().toXML(
				new NfeCabecMsg(uf, "1.00","http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento"));	
	}
	public String inutilizacao() {
		return xstream().toXML(
				new NfeCabecMsg(uf, "2.00","http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2"));	
	}

	private XStream xstream(){
		if (xstream == null){
			xstream = XStreamFabrica.criar();
			xstream.processAnnotations(NfeCabecMsg.class);
		}
		return xstream;
	}
	
	@XStreamAlias("nfeCabecMsg")
    static class NfeCabecMsg{
		
		@XStreamAlias("xmlns")
		@XStreamAsAttribute
    	protected String uriWebService;
    	
    	@XStreamAlias("cUF")
    	protected Uf uf;
    	
    	protected String versaoDados;
		
    	protected NfeCabecMsg(Uf uf, String versaoDados, String uriWebService) {
			super();
			this.uf = uf;
			this.versaoDados = versaoDados;
			this.uriWebService = uriWebService;
		}
    }
	
}
