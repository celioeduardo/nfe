package com.hadrion.nfe.port.adapters.xml.exportacao;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ExportacaoNotaConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Exportacao.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Exportacao exp = (Exportacao) source;
		convert("UFSaidaPais", exp.uf(), writer, context);
		convert("xLocExporta", exp.localEmbarque(), writer, context);
		convert("xLocDespacho", exp.localDespacho(),writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Uf ufSaidaPais = null;
		String localExportacao = null, localDespacho = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("UFSaidaPais".equals(reader.getNodeName())) {
				ufSaidaPais = (Uf) context.convertAnother(reader.getValue(), Uf.class);
			} else if ("xLocExporta".equals(reader.getNodeName())) {
				localExportacao = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("xLocDespacho".equals(reader.getNodeName())) {
				localDespacho = (String) context.convertAnother(reader.getValue(), String.class);
			}
			reader.moveUp();
		}
		return new Exportacao(ufSaidaPais, localExportacao, localDespacho);
	}

}
