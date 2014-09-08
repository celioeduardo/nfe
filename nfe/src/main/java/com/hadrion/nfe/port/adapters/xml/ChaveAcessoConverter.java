package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class ChaveAcessoConverter extends AbstractSingleValueConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return ChaveAcesso.class.isAssignableFrom(type);
	}

	@Override
	public Object fromString(String str) {
		return new ChaveAcesso(str);
	}

}
