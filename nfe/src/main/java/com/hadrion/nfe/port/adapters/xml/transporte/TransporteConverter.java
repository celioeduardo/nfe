package com.hadrion.nfe.port.adapters.xml.transporte;

import java.util.ArrayList;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.nf.transporte.ModalidadeFrete;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TransporteConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Transporte.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Transporte transporte = (Transporte) source;
		convert("modFrete", transporte.modalidadeFrete(), writer, context);
		convert("transporta", transporte.transportador(), writer, context);
		convertIf("veicTransp", transporte.veiculo(), writer, context);
		for (Volume v : transporte.volumes()) 
			convert("vol", v, writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		ModalidadeFrete modalidadeFrete = null;
		Transportador transportador = null;
		Veiculo veiculo = null;
		List<Volume> volumes = new ArrayList<Volume>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("modFrete".equals(reader.getNodeName())) {
				modalidadeFrete = (ModalidadeFrete) context.convertAnother(reader.getValue(), ModalidadeFrete.class);
			} else if ("transporta".equals(reader.getNodeName())) {
				transportador = (Transportador) context.convertAnother(reader.getValue(), Transportador.class);
			} else if ("veicTransp".equals(reader.getNodeName())) {
				veiculo = (Veiculo) context.convertAnother(reader.getValue(), Veiculo.class);
			} else if ("vol".equals(reader.getNodeName())) {
				volumes.add((Volume) context.convertAnother(reader.getValue(), Volume.class));
			}
			reader.moveUp();
		}
		return new Transporte(modalidadeFrete,transportador,null, veiculo,volumes);
	}

}
