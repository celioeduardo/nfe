package com.hadrion.util.xsd;

import java.io.IOException;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;


public class Validador {
	
	private SchemaFactory factory;
	private Schema schema;
	private Validator validator;
	private ManipuladorErro manipuladorErro;
	
	protected Validador(Source source, Source xml) {
		super();
		
		manipuladorErro = new ManipuladorErro();
		factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		
		try {
			factory.setResourceResolver(new ResourceResolver("xsd/PL_008e/"));
			schema = factory.newSchema(source);
		} catch (SAXException e) {
			manipuladorErro.registrarErro("Erro ao ler XML Schema: "+e.getMessage());
			return;
		}
		
		validator = schema.newValidator();
		validator.setErrorHandler(manipuladorErro);
		
		try {
			validator.validate(xml);
		} catch (SAXException | IOException e) {
			manipuladorErro.registrarErro("Erro ao ler XML: "+e.getMessage());
		}
	}
	
	public String errosComoTexto(){
		return manipuladorErro.errosComoTexto();
	}
	
	public List<String> erros(){
		return manipuladorErro.erros();
	}
	
	public int quantidadeErros() {
		return erros().size();
	}

	public boolean temErros() {
		return quantidadeErros() > 0;
	}

}
