package com.hadrion.nfe.port.adapters.xml.cobranca;

import java.util.ArrayList;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Fatura;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CobrancaConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Cobranca.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Cobranca cob = (Cobranca) source;
		convertIf("fat", cob.fatura(), writer, context);
		for (Duplicata dup : cob.duplicatas()) 
			convertIf("dup", dup, writer, context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Fatura fatura = null;
		List<Duplicata> duplicatas = new ArrayList<Duplicata>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("fat".equals(reader.getNodeName())) {
				fatura = (Fatura) context.convertAnother(reader.getValue(), Fatura.class);
			} else if ("dup".equals(reader.getNodeName())) {
				duplicatas.add((Duplicata) context.convertAnother(reader.getValue(), Duplicata.class));
			}
			reader.moveUp();
		}
		return new Cobranca(fatura, duplicatas);
	}

}
