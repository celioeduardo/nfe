package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NFe")
class Nfe {
	
	protected NotaFiscal notaFiscal;
	
	Nfe(NotaFiscal nf){
		notaFiscal = nf;
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
