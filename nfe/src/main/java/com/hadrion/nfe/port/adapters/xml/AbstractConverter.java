package com.hadrion.nfe.port.adapters.xml;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public abstract class AbstractConverter implements Converter{

	public void novoNo(String nome, String valor, HierarchicalStreamWriter writer) {
		writer.startNode(nome);
		writer.setValue(valor);
		writer.endNode();
	}
	public void novoNoIf(String nome, String valor, HierarchicalStreamWriter writer) {
		if (StringUtils.isNotEmpty(valor)){
			writer.startNode(nome);
			writer.setValue(valor);
			writer.endNode();
		}
	}

	public void convert(String nome, Object valor, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode(nome);
		context.convertAnother(valor);
		writer.endNode();
	}

	public void convertIf(String nome, Object valor, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (valor != null){
			writer.startNode(nome);
			context.convertAnother(valor);
			writer.endNode();
		}
	}
}
