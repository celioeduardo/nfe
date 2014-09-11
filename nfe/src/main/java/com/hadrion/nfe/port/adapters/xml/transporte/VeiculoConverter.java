package com.hadrion.nfe.port.adapters.xml.transporte;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Placa;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class VeiculoConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Veiculo.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Veiculo veic = (Veiculo) source;
		if (veic.placa() != null){
			convert("placa", veic.placa().numero(), writer, context);
			convert("UF", veic.placa().uf(), writer, context);
		}
		convertIf("RNTC", veic.registroAntt(), writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		String numeroPlaca = null, registroAntt = null;
		Uf ufPlaca = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("placa".equals(reader.getNodeName())) {
				numeroPlaca = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("UF".equals(reader.getNodeName())) {
				ufPlaca = (Uf) context.convertAnother(reader.getValue(), Uf.class);
			} else if ("RNTC".equals(reader.getNodeName())) {
				registroAntt = reader.getValue();
			}
			reader.moveUp();
		}
		
		Placa placa = null;
		if (numeroPlaca != null && ufPlaca != null)
			placa = new Placa(ufPlaca, numeroPlaca);

		return new Veiculo(null, placa, registroAntt, null);
	}

}
