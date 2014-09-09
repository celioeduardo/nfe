package com.hadrion.nfe.port.adapters.xml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.tipos.Aliquota;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AliquotaConverter extends AbstractConverter{
	
	private NumberFormat fmt;
	
	public AliquotaConverter(){
		fmt = new DecimalFormat("#0.00",DecimalFormatSymbols.getInstance(Locale.US));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Aliquota.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Aliquota aliquota = (Aliquota) source;
		writer.setValue(fmt.format(aliquota.valor()));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return new Aliquota(Double.parseDouble(reader.getValue()));
	}

}
