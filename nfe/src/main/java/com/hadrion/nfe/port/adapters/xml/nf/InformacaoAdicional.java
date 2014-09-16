package com.hadrion.nfe.port.adapters.xml.nf;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

class InformacaoAdicional {
	private String fisco;
	private String contribuinte;
	
	public InformacaoAdicional(HierarchicalStreamReader reader,
			UnmarshallingContext context){
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("infAdFisco".equals(reader.getNodeName())) 
				fisco = reader.getValue();
			if ("infCpl".equals(reader.getNodeName())) 
				contribuinte = reader.getValue();
			reader.moveUp();
		}
	}

	public String getFisco() {
		return fisco;
	}

	public String getContribuinte() {
		return contribuinte;
	}
}
