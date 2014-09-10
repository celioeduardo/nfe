package com.hadrion.nfe.port.adapters.xml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.Cst;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CofinsConverter extends AbstractConverter implements Converter {

	private NumberFormat fmt;
	
	public CofinsConverter(){
		fmt = new DecimalFormat("#0.0000",DecimalFormatSymbols.getInstance(Locale.US));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Cofins.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		Cofins pis = (Cofins) source;
		
		switch (pis.cst()) {
		case CST_01:
		case CST_02:
			writer.startNode("COFINSAliq");
			convert("CST", pis.cst(), writer, context);
			convert("vBC", pis.baseCalculo(), writer, context);
			convert("pCOFINS", pis.aliquota(), writer, context);
			convert("vCOFINS", pis.valor(), writer, context);
			break;
		case CST_03:
			writer.startNode("COFINSQtde");
			convert("CST", pis.cst(), writer, context);
			convert("qBCProd", fmt.format(pis.quantidade()), writer, context);
			convert("vAliqProd", fmt.format(pis.aliquotaEmReais()), writer, context);
			convert("vCOFINS", pis.valor(), writer, context);
			break;
		case CST_04:
		case CST_05:
		case CST_06:
		case CST_07:
		case CST_08:
		case CST_09:
			writer.startNode("COFINSNT");
			convert("CST", pis.cst(), writer, context);
			break;
		default:
			writer.startNode("COFINSOutr");
			convert("CST", pis.cst(), writer, context);
			convert("vBC", pis.baseCalculo(), writer, context);
			convert("pCOFINS", pis.aliquota(), writer, context);
			convert("vCOFINS", pis.valor(), writer, context);
			break;
		}
		writer.endNode();
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {

		Cst cst = null; 
		Aliquota aliquota = null; 
		Double quantidade = null, aliquotaEmReais = null;
		Dinheiro baseCalculo = null, valor = null; 
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if ("CST".equals(reader.getNodeName())) {
					cst = (Cst) context.convertAnother(reader.getValue(), Cst.class);
				} else if ("vBC".equals(reader.getNodeName())) {
					baseCalculo = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				} else if ("pCOFINS".equals(reader.getNodeName())) {
					aliquota = (Aliquota) context.convertAnother(reader.getValue(), Aliquota.class);
				} else if ("vCOFINS".equals(reader.getNodeName())) {
					valor = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				} else if ("qBCProd".equals(reader.getNodeName())) {
					quantidade = (Double) context.convertAnother(reader.getValue(), Double.class);
				} else if ("vAliqProd".equals(reader.getNodeName())) {
					aliquotaEmReais = (Double) context.convertAnother(reader.getValue(), Double.class);
				}
				reader.moveUp();
			}
			reader.moveUp();
		}

		return new CofinsConvertido(cst, baseCalculo, aliquota, quantidade, aliquotaEmReais,valor);
				
	}
	
	

}
