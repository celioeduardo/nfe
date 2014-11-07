package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.nf.publico.Crt;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class EmitenteConverter extends AbstractConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Emitente.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Emitente emitente = (Emitente) source;
		if (emitente.cnpj() != null)
			convert("CNPJ", emitente.cnpj(), writer, context);
		
		if (emitente.cpf() != null)
			convert("CPF", emitente.cnpj(), writer, context);
		
		novoNo("xNome", emitente.razaoSocial(), writer);
		novoNo("xFant", emitente.nomeFantasia(), writer);
		convert("enderEmit", emitente.endereco(), writer, context);
		convert("IE",emitente.ie(),writer, context);
		
		if (emitente.ieSubstituto() != null)
			convert("IEST",emitente.ieSubstituto(),writer,context);
		if (emitente.crt() != null)
			convert("CRT",emitente.crt(),writer,context);//TODO crt
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Cnpj cnpj = null;
		Cpf cpf = null;
		String razaoSocial = "", nomeFantasia = "";
		Endereco endereco = null;
		InscricaoEstadual ie = null;
		InscricaoEstadual ieSt = null;
		Crt crt = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("CNPJ".equals(reader.getNodeName())) {
				cnpj = (Cnpj) context.convertAnother(reader.getValue(), Cnpj.class);
			} else if ("CPF".equals(reader.getNodeName())) {
				cpf = (Cpf) context.convertAnother(reader.getValue(), Cpf.class);
			} else if ("xNome".equals(reader.getNodeName())) {
				razaoSocial = reader.getValue();
			} else if ("xFant".equals(reader.getNodeName())) {
				nomeFantasia = reader.getValue();
			} else if ("enderEmit".equals(reader.getNodeName())) {
				endereco = (Endereco) context.convertAnother(reader.getValue(), Endereco.class);
			} else if ("IE".equals(reader.getNodeName())) {
				ie = (InscricaoEstadual) context.convertAnother(reader.getValue(), InscricaoEstadual.class);
			} else if ("IEST".equals(reader.getNodeName())) {
				ieSt = (InscricaoEstadual) context.convertAnother(reader.getValue(), InscricaoEstadual.class);
			} else if ("CRT".equals(reader.getNodeName())) {
				crt = (Crt) context.convertAnother(reader.getValue(), Crt.class);
			}
			reader.moveUp();
		}
		return new Emitente(cnpj, cpf, razaoSocial, nomeFantasia, endereco, null, ie, ieSt, crt);
	}

}
