package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Telefone;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class EnderecoConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Endereco.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Endereco end = (Endereco) source;
		novoNo("xLgr", end.logradouro(), writer);
		novoNo("nro",end.numero(),writer);
		novoNoIf("xCpl", end.complemento(), writer);
		novoNo("xBairro",end.bairro(),writer);
		context.convertAnother(end.municipio());
		novoNoIf("CEP",String.valueOf(end.cep().numero()),writer);
		if (end.pais() != null)
			context.convertAnother(end.pais());
		novoNoIf("fone", fone(end), writer);
	}
	
	private Telefone fone(Endereco endereco){
		if (endereco.telefone() == null) 
			return null;
		
		if (endereco.telefone().obterDigitos().length() < 6)
			return null;
		
		return endereco.telefone(); 
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String logradouro = null, numero = null, complemento = null, bairro = null;
		int codMunicipio = 0;
		long codPais = 0;
		String nomeMunicipio = null, nomePais = null;
		Uf ufMunicipio = null;
		
		Cep cep = null;
		Telefone telefone = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("xLgr".equals(reader.getNodeName())) {
				logradouro = reader.getValue();
			} else if ("nro".equals(reader.getNodeName())) {
				numero = reader.getValue();
			} else if ("xCpl".equals(reader.getNodeName())) {
				complemento = reader.getValue();
			} else if ("xBairro".equals(reader.getNodeName())) {
				bairro = reader.getValue();
			} else if ("cMun".equals(reader.getNodeName())) {
				codMunicipio = Integer.parseInt(reader.getValue());
			} else if ("xMun".equals(reader.getNodeName())) {
				nomeMunicipio = reader.getValue();
			} else if ("UF".equals(reader.getNodeName())) {
				ufMunicipio = Uf.valueOf(reader.getValue());
			} else if ("cPais".equals(reader.getNodeName())) {
				codPais = Long.parseLong(reader.getValue());
			} else if ("xPais".equals(reader.getNodeName())) {
				nomePais = reader.getValue();
			} else if ("CEP".equals(reader.getNodeName())) {
				cep = (Cep) context.convertAnother(reader.getValue(), Cep.class);
			} else if ("fone".equals(reader.getNodeName())) {
				if (reader.getValue() != null && reader.getValue() != "")
					telefone = (Telefone) context.convertAnother(reader.getValue(), Telefone.class);
			}
			reader.moveUp();
		}
		Pais pais = new Pais(codPais, nomePais);
		Municipio mun = new Municipio(codMunicipio, nomeMunicipio, ufMunicipio);
		return new Endereco(logradouro, numero, complemento, bairro, mun, pais,cep,telefone);
	}

}
