package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class NotaFiscalIdConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return NotaFiscalId.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new NotaFiscalId(str);
	}

}
