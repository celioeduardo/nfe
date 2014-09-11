package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class ModeloConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Modelo.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new Modelo(str);
	}

}
