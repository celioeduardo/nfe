package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ExportacaoConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return ExportacaoItem.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ExportacaoItem exp = (ExportacaoItem) source;
		convert("nDraw",exp.numeroDrawBack(),writer,context);
		convert("exportInd",exp.exportacaoIndireta(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Long nDraw = null;
		ExportacaoIndireta exportacaoIndireta = null; 
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("nDraw".equals(reader.getNodeName())) {
				nDraw = Long.parseLong(reader.getValue());
			} else if ("exportInd".equals(reader.getNodeName())) {
				exportacaoIndireta = (ExportacaoIndireta) context.convertAnother(reader.getValue(), ExportacaoIndireta.class);
			}
			reader.moveUp();
		}
		return new ExportacaoItem(nDraw, exportacaoIndireta);
	}

}
