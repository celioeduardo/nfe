package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class SerieConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Serie.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new Serie(Long.parseLong(str));
	}

}
