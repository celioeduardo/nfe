package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class NfeConverter implements Converter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Nfe.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Nfe nfe = (Nfe) source;
		
		writer.addAttribute("xmlns", nfe.uriPortal());
		
		writer.startNode("infNFe");
		writer.addAttribute("Id", nfe.id());
		writer.addAttribute("versao", nfe.versaoLayout());
		context.convertAnother(nfe.notaFiscal());
		writer.endNode();
	}

	@Override
	public Nfe unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		NotaFiscal nf = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("infNFe"))
				nf = (NotaFiscal) context.convertAnother(reader.getValue(),NotaFiscal.class);
			reader.moveUp();
		}
		return new Nfe(nf);
	}

}
