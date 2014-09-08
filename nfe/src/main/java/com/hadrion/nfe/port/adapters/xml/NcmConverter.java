package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class NcmConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Ncm.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new Ncm(str);
	}

}
