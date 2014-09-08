package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class LocalEntregaConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return LocalEntrega.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		LocalEntrega local = (LocalEntrega) source;
		convertIf("CNPJ",local.cnpj(),writer,context);
		convertIf("CPF",local.cpf(),writer,context);
		novoNo("xLgr", local.endereco().logradouro(), writer);
		novoNo("nro",local.endereco().numero(),writer);
		novoNoIf("xCpl", local.endereco().complemento(), writer);
		novoNo("xBairro",local.endereco().bairro(),writer);
		context.convertAnother(local.endereco().municipio());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Cnpj cnpj = null;
		Cpf cpf = null;
		String logradouro = null, numero = null, complemento = null, bairro = null;
		int codMunicipio = 0;
		String nomeMunicipio = null;
		Uf ufMunicipio = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("CNPJ".equals(reader.getNodeName())) {
				cnpj = (Cnpj) context.convertAnother(reader.getValue(), Cnpj.class);
			} else if ("CPF".equals(reader.getNodeName())) {
				cpf = (Cpf) context.convertAnother(reader.getValue(), Cpf.class);
			} else if ("xLgr".equals(reader.getNodeName())) {
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
			}
			reader.moveUp();
		}
		Municipio mun = new Municipio(codMunicipio, nomeMunicipio, ufMunicipio);
		Endereco endereco = new Endereco(logradouro, numero, complemento, bairro, mun, null,null,null);
		return new LocalEntrega(cnpj, cpf, endereco);
	}

}
