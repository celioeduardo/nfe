package com.hadrion.nfe.port.adapters.xml.nf;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Node;

import com.hadrion.util.xsd.Validador;

public class ValidadorLote extends Validador{

	public ValidadorLote(Source xml) {
		super(arquivoXsd(), xml);
	}
	public ValidadorLote(Node node){
		super(arquivoXsd(),new DOMSource(node));
	}
	
	private static File arquivoXsd(){
		return FileUtils.getFile("src","main","resources","xsd","PL_008e","enviNFe_v3.10.xsd");
	}

}
