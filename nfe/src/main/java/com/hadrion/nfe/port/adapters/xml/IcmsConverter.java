package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IcmsConverter extends AbstractConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Icms.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		Icms icms = (Icms) source;
		
		switch (icms.cst()) {
		case CST_51:
			writer.startNode("ICMS51");
			break;
		default:
			break;
		}
		
		convert("orig", icms.origem(), writer, context);
		convert("CST", icms.cst(), writer, context);
		convert("modBC", icms.determinacaoBaseCalculo(), writer, context);
		convert("pRedBC", icms.percentualReducaoBaseCalculo(), writer, context);
		convert("vBC", icms.baseCalculo(), writer, context);
		convert("pICMS", icms.aliquota(), writer, context);
		convert("vICMSOp", icms.valorSemDiferimento(), writer, context);
		convert("pDif", icms.percentualDiferimento(), writer, context);
		convert("vICMSDif", icms.valorDiferido(), writer, context);
		convert("vICMS", icms.valor(), writer, context);
		
		writer.endNode();
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Origem origem = null;
		Cst cst = null; 
		DeterminacaoBaseCalculo determinacaoBaseCalculo= null; 
		Percentual percentualReducaoBaseCalculo = null; 
		Dinheiro baseCalculo = null,valor = null; 
		Aliquota aliquota = null; 
		Percentual percentualDiferimento = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if ("orig".equals(reader.getNodeName())) {
					origem = (Origem) context.convertAnother(reader.getValue(), Origem.class);
				} else if ("CST".equals(reader.getNodeName())) {
					cst = (Cst) context.convertAnother(reader.getValue(), Cst.class);
				} else if ("modBC".equals(reader.getNodeName())) {
					 determinacaoBaseCalculo = (DeterminacaoBaseCalculo) context.convertAnother(reader.getValue(), DeterminacaoBaseCalculo.class);
				} else if ("pRedBC".equals(reader.getNodeName())) {
					percentualReducaoBaseCalculo = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
				} else if ("vBC".equals(reader.getNodeName())) {
					baseCalculo = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				} else if ("pICMS".equals(reader.getNodeName())) {
					aliquota = (Aliquota) context.convertAnother(reader.getValue(), Aliquota.class);
				} else if ("vICMSOp".equals(reader.getNodeName())) {
					valor = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				} else if ("pDif".equals(reader.getNodeName())) {
					percentualDiferimento = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
				}
				reader.moveUp();
			}
			reader.moveUp();
		}
		return new IcmsConvertido(origem, 
				cst, determinacaoBaseCalculo, 
				percentualReducaoBaseCalculo, 
				null, aliquota, valor, 
				null, 
				percentualDiferimento,
				baseCalculo);
	}

}
