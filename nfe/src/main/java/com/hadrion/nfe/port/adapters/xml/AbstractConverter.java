package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public abstract class AbstractConverter implements Converter{

	public void novoNo(String nome, Object valor, HierarchicalStreamWriter writer) {
		writer.startNode(nome);
		writer.setValue(String.valueOf(valor));
		writer.endNode();
	}
	public void novoNoIf(String nome, Object valor, HierarchicalStreamWriter writer) {
		if (valor != null){
			writer.startNode(nome);
			writer.setValue(String.valueOf(valor));
			writer.endNode();
		}
	}

	public void convert(String nome, Object valor, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode(nome);
		context.convertAnother(valor);
		writer.endNode();
	}
	
	public void convertMaiorQueZero(String nome, Dinheiro valor, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (valor.maiorQueZero()){
			writer.startNode(nome);
			context.convertAnother(valor);
			writer.endNode();
		}
	}

	public void convertIf(String nome, Object valor, HierarchicalStreamWriter writer, MarshallingContext context) {
		if (valor != null){
			writer.startNode(nome);
			context.convertAnother(valor);
			writer.endNode();
		}
	}
	public void convertVazio(String nome, Object valor, HierarchicalStreamWriter writer, MarshallingContext context) {
		writer.startNode(nome);
		if (valor != null)
			context.convertAnother(valor);
		writer.endNode();
	}
}
