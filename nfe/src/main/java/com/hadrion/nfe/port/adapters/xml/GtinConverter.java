package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class GtinConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Gtin.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new Gtin(str);
		/*if (str != null && !str.isEmpty())
			return new Gtin(str);
		return null;*/
	}

}
