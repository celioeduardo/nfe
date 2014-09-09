package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Quantidade;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ExportacaoIndiretaConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return ExportacaoIndireta.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		ExportacaoIndireta exp = (ExportacaoIndireta) source;
		novoNo("nRE", exp.numeroRegistro(),writer);
		convert("chNFe", exp.chaveAcesso(),writer,context);
		convert("qExport", exp.quantidadeExportada(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Long numeroRegistro = null;
		ChaveAcesso chaveAcesso = null;
		Quantidade quantidadeExportada = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("nRE".equals(reader.getNodeName())) {
				numeroRegistro = Long.parseLong(reader.getValue());
			} else if ("chNFe".equals(reader.getNodeName())) {
				chaveAcesso = (ChaveAcesso) context.convertAnother(reader.getValue(), ChaveAcesso.class);
			} else if ("qExport".equals(reader.getNodeName())) {
				quantidadeExportada = (Quantidade) context.convertAnother(reader.getValue(), Quantidade.class);
			}
			reader.moveUp();
		}
		return new ExportacaoIndireta(numeroRegistro, chaveAcesso, quantidadeExportada);
	}

}
