package com.hadrion.nfe.port.adapters.xml.cobranca;

import com.hadrion.nfe.dominio.modelo.nf.cobranca.Fatura;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FaturaConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Fatura.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Fatura fatura = (Fatura) source;
		convert("nFat", fatura.numero(), writer, context);
		convert("vOrig", fatura.valor(), writer, context);
		convertIf("vDesc", fatura.desconto(), writer, context);
		convert("vLiq", fatura.liquido(), writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String numero = null;
		Dinheiro valor = null, desconto = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("nFat".equals(reader.getNodeName())) {
				numero = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("vOrig".equals(reader.getNodeName())) {
				valor = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
			} else if ("vDesc".equals(reader.getNodeName())) {
				desconto = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
			}
			reader.moveUp();
		}
		return new Fatura(numero, valor, desconto);
	}

}
