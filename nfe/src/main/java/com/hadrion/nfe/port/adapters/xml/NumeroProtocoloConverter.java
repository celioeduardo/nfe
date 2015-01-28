package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class NumeroProtocoloConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return NumeroProtocolo.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new NumeroProtocolo(str);
	}

}
