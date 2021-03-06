package com.hadrion.nfe.port.adapters.xml.nf;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;

import com.hadrion.util.xsd.Validador;

public class ValidadorNotaFiscal extends Validador{

	public ValidadorNotaFiscal(Source xml) {
		super(xsd(), xml);
	}
	
	public ValidadorNotaFiscal(Node node){
		super(xsd(),new DOMSource(node));
	}
	
	private static Source xsd() {
		return new StreamSource(Validador.class.getClassLoader()
				.getResourceAsStream("xsd/PL_008e/nfe_v3.10.xsd"));
	}

}
