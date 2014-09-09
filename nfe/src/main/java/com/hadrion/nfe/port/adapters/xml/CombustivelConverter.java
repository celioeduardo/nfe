package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.item.Cide;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.tipos.Quantidade;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CombustivelConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Combustivel.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Combustivel comb = (Combustivel) source;
		convert("cProdANP",comb.codAnp(),writer,context);
		convert("qTemp",comb.quantidade(),writer,context);
		convert("UFCons",comb.ufConsumo(),writer,context);
		convert("CIDE",comb.cide(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Long codAnp = null;
		Quantidade quantidade = null;
		Uf ufConsumo = null;
		Cide cide = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("cProdANP".equals(reader.getNodeName())) {
				codAnp = Long.parseLong(reader.getValue());
			} else if ("qTemp".equals(reader.getNodeName())) {
				quantidade = (Quantidade) context.convertAnother(reader.getValue(), Quantidade.class);
			} else if ("UFCons".equals(reader.getNodeName())) {
				ufConsumo = (Uf) context.convertAnother(reader.getValue(), Uf.class);
			} else if ("CIDE".equals(reader.getNodeName())) {
				cide = (Cide) context.convertAnother(reader.getValue(), Cide.class);
			}
			reader.moveUp();
		}
		return new Combustivel(codAnp, quantidade, ufConsumo, cide);
	}

}
