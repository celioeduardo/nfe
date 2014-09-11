package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ItemConverter extends AbstractConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Item.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		Item item = (Item) source;
		convert("prod", item.produto(), writer, context);
		convert("imposto", item.imposto(), writer, context);
		convert("infAdProd", item.informacaoAdicional(), writer, context);
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {

		DescritorProduto produto = null;
		Imposto imposto = null;
		String informacaoAdicionalProduto = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("prod".equals(reader.getNodeName())) {
				produto = (DescritorProduto) context.convertAnother(reader.getValue(), DescritorProduto.class);
			} else if ("imposto".equals(reader.getNodeName())) {
				imposto = (Imposto) context.convertAnother(reader.getValue(), Imposto.class);
			} else if ("infAdProd".equals(reader.getNodeName())) {
				informacaoAdicionalProduto = (String) context.convertAnother(reader.getValue(), String.class);
			}
			reader.moveUp();
		}

		return new Item(produto, imposto, informacaoAdicionalProduto);
				
	}
	
	

}
