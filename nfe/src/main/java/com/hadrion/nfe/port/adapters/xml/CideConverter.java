package com.hadrion.nfe.port.adapters.xml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.dominio.modelo.nf.item.Cide;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CideConverter extends AbstractConverter{
	private NumberFormat fmt4decimais;
	
	public CideConverter(){
		fmt4decimais = new DecimalFormat("#0.0000",DecimalFormatSymbols.getInstance(Locale.US));
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Cide.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Cide cide = (Cide) source;
		convert("qBCProd",cide.baseCalculo(),writer,context);
		convert("vAliqProd",formatarAliquota(cide.aliquota()),writer,context);
		convert("vCIDE",cide.valor(),writer,context);
	}
	
	private String formatarAliquota(Aliquota aliquota){
		return fmt4decimais.format(aliquota.valor());
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Dinheiro baseCalculo = null, valor = null;
		Aliquota aliquota = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("qBCProd".equals(reader.getNodeName())) {
				baseCalculo = new Dinheiro(Double.parseDouble(reader.getValue()));
			} else if ("vAliqProd".equals(reader.getNodeName())) {
				aliquota = new Aliquota(Double.parseDouble(reader.getValue()));
			} else if ("vCIDE".equals(reader.getNodeName())) {
				valor = new Dinheiro(Double.parseDouble(reader.getValue()));
			}
			reader.moveUp();
		}
		return new Cide(baseCalculo, aliquota, valor);
	}

}
