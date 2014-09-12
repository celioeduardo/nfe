package com.hadrion.nfe.port.adapters.xml.nf;

import static com.hadrion.util.DataUtil.formatarComTimezone;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.nf.Contingencia;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ContingenciaConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Contingencia.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Contingencia cont = (Contingencia) source;
		convert("dhCont",formatarComTimezone(cont.dataHora()),writer,context);
		convert("xJust",cont.justificativa(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Date dataHora = null;
		String justificativa = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("dhCont".equals(reader.getNodeName())) {
				dataHora = (Date) context.convertAnother(reader.getValue(), Date.class);
			} else if ("xJust".equals(reader.getNodeName())) {
				justificativa = (String) context.convertAnother(reader.getValue(), String.class);
			}
			reader.moveUp();
		}
		return new Contingencia(dataHora, justificativa);
	}

}
