package com.hadrion.nfe.port.adapters.xml.transporte;

import org.apache.commons.lang.StringUtils;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class TransportadorConverter extends AbstractConverter{
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Transportador.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Transportador transp = (Transportador) source;
		convertIf("CNPJ", transp.cnpj(), writer, context);
		convertIf("CPF", transp.cpf(), writer, context);
		convertIf("xNome", transp.razaoSocial(), writer, context);
		convertIf("IE", transp.ie(), writer, context);
		convertIf("xEnder", enderecoCompleto(transp.endereco()), writer, context);
		if (transp.endereco() != null && transp.endereco().municipio() != null){
			convertIf("xMun", transp.endereco().municipio().nome(), writer, context);
			convertIf("UF", transp.endereco().municipio().uf(), writer, context);
		}
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Cnpj cnpj = null;
		Cpf cpf = null;
		String nome = null, enderecoCompleto = null, nomeMunicipio = null; 
		Uf ufMunicipio = null;
		InscricaoEstadual ie = null;
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("CNPJ".equals(reader.getNodeName())) {
				cnpj = (Cnpj) context.convertAnother(reader.getValue(), Cnpj.class);
			} else if ("CPF".equals(reader.getNodeName())) {
				cpf = (Cpf) context.convertAnother(reader.getValue(), Cpf.class);
			} else if ("xNome".equals(reader.getNodeName())) {
				nome = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("IE".equals(reader.getNodeName())) {
				ie = (InscricaoEstadual) context.convertAnother(reader.getValue(), InscricaoEstadual.class);
			} else if ("xEnder".equals(reader.getNodeName())) {
				enderecoCompleto = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("xMun".equals(reader.getNodeName())) {
				nomeMunicipio = (String) context.convertAnother(reader.getValue(), String.class);
			} else if ("UF".equals(reader.getNodeName())) {
				ufMunicipio = (Uf) context.convertAnother(reader.getValue(), Uf.class);
			}
			reader.moveUp();
		}
		Endereco endereco = new Endereco(enderecoCompleto, null, null, null, 
				new Municipio(0, nomeMunicipio, ufMunicipio), null, null, null);
		return new Transportador(cnpj, cpf, nome, ie, endereco);
	}

	public String enderecoCompleto(Endereco end){
		if (end == null) return null;
		String result = "";
		
		if (end.logradouro() != null)
			result += end.logradouro() + " ";
		if (end.numero() != null)
			result += end.numero() + " ";
		if (end.complemento() != null)
			result += end.complemento() + " ";
		if (end.bairro() != null)
			result += end.bairro() + " ";
		
		result = result.trim();
		return StringUtils.substring(result,0,60);
	}

}
