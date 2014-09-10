package com.hadrion.nfe.port.adapters.xml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.tipos.Percentual;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PercentualConverter extends AbstractConverter{
	
	private NumberFormat fmt;
	
	public PercentualConverter(){
		fmt = new DecimalFormat("#0.00",DecimalFormatSymbols.getInstance(Locale.US));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Percentual.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Percentual percentual = (Percentual) source;
		writer.setValue(fmt.format(percentual.valor()));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new Percentual(Double.parseDouble(reader.getValue()));
	}

}
