package com.hadrion.nfe.port.adapters.xml.transporte;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class VolumeConverter extends AbstractConverter{
	
	private NumberFormat fmt;
	
	public VolumeConverter(){
		fmt = new DecimalFormat("#0.000",DecimalFormatSymbols.getInstance(Locale.US));
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Volume.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Volume volume = (Volume) source;
		convertIf("qVol", volume.quantidade(), writer, context);
		convertIf("esp", volume.especie(), writer, context);
		convertIf("marca", volume.marca(), writer, context);
		convertIf("nVol", volume.numeracao(), writer, context);
		convertIf("pesoL", fmt.format(volume.pesoLiquido()), writer, context);
		convertIf("pesoB", fmt.format(volume.pesoBruto()), writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Integer quantidade = null;
		String especie = null, marca = null, numeracao = null;
		Double pesoLiquido = null, pesoBruto = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("qVol".equals(reader.getNodeName())) {
				quantidade = (Integer) context.convertAnother(reader.getValue(), Integer.class);
			} else if ("esp".equals(reader.getNodeName())) {
				especie = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("marca".equals(reader.getNodeName())) {
				marca = reader.getValue();
			} else if ("nVol".equals(reader.getNodeName())) {
				numeracao = reader.getValue();
			} else if ("pesoL".equals(reader.getNodeName())) {
				pesoLiquido = (Double) context.convertAnother(reader.getValue(), Double.class);
			} else if ("pesoB".equals(reader.getNodeName())) {
				pesoBruto = (Double) context.convertAnother(reader.getValue(), Double.class);
			}
			reader.moveUp();
		}
		
		return new Volume(quantidade, especie, marca, numeracao, pesoLiquido, pesoBruto, null);
	}

}
