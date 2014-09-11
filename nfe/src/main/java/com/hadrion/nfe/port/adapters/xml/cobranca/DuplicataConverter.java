package com.hadrion.nfe.port.adapters.xml.cobranca;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DuplicataConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Duplicata.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Duplicata dup = (Duplicata) source;
		convert("nDup", dup.numero(), writer, context);
		convert("dVenc", dup.vencimento(), writer, context);
		convert("vDup", dup.valor(), writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String numero = null;
		Dinheiro valor = null;
		Date vencimento = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("nDup".equals(reader.getNodeName())) {
				numero = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("dVenc".equals(reader.getNodeName())) {
				vencimento = (Date) context.convertAnother(reader.getValue(), Date.class);
			} else if ("vDup".equals(reader.getNodeName())) {
				valor = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
			}
			reader.moveUp();
		}
		return new Duplicata(numero, vencimento, valor);
	}

}
