package com.hadrion.nfe.port.adapters.xml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.dominio.modelo.pis.Cst;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PisConverter extends AbstractConverter implements Converter {

	private NumberFormat fmt;
	
	public PisConverter(){
		fmt = new DecimalFormat("#0.0000",DecimalFormatSymbols.getInstance(Locale.US));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Pis.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		Pis pis = (Pis) source;
		
		switch (pis.cst()) {
		case CST_01:
		case CST_02:
			writer.startNode("PISAliq");
			convert("CST", pis.cst(), writer, context);
			convert("vBC", pis.baseCalculo(), writer, context);
			convert("pPIS", pis.aliquota(), writer, context);
			convert("vPIS", pis.valor(), writer, context);
			break;
		case CST_03:
			writer.startNode("PISQtde");
			convert("CST", pis.cst(), writer, context);
			convert("qBCProd", fmt.format(pis.quantidade()), writer, context);
			convert("vAliqProd", fmt.format(pis.aliquotaEmReais()), writer, context);
			convert("vPIS", pis.valor(), writer, context);
			break;
		case CST_04:
		case CST_05:
		case CST_06:
		case CST_07:
		case CST_08:
		case CST_09:
			writer.startNode("PISNT");
			convert("CST", pis.cst(), writer, context);
			break;
		default:
			writer.startNode("PISOutr");
			convert("CST", pis.cst(), writer, context);
			convert("vBC", pis.baseCalculo(), writer, context);
			convert("pPIS", pis.aliquota(), writer, context);
			convert("vPIS", pis.valor(), writer, context);
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
				} else if ("pPIS".equals(reader.getNodeName())) {
					aliquota = (Aliquota) context.convertAnother(reader.getValue(), Aliquota.class);
				} else if ("vPIS".equals(reader.getNodeName())) {
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

		return new PisConvertido(cst, baseCalculo, aliquota, quantidade, aliquotaEmReais,valor);
				
	}
	
	

}
