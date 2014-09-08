package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class CfopConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Cfop.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new Cfop(Long.parseLong(str));
	}

}
