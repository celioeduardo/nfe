package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.IndicadorIe;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DestinatarioConverter extends AbstractConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Destinatario.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Destinatario dest = (Destinatario) source;
		if (dest.cnpj() != null)
			convert("CNPJ", dest.cnpj(), writer, context);
		
		if (dest.cpf() != null)
			convert("CPF", dest.cpf(), writer, context);
		
		novoNo("xNome", dest.razaoSocial(), writer);
		convert("enderDest", dest.endereco(), writer, context);
		convert("indIEDest",dest.indicadorIe(),writer, context);
		convertIf("IE",dest.ie(),writer, context);
		convert("ISUF",dest.inscricaoSuframa(),writer, context);
		convertIf("email",dest.email(),writer,context);
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Cnpj cnpj = null;
		Cpf cpf = null;
		String idEstrangeiro = null,razaoSocial = null;
		Endereco endereco = null;
		IndicadorIe indIe = null;
		InscricaoEstadual ie = null;
		Long inscricaoSuframa = null;
		Email email = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("CNPJ".equals(reader.getNodeName())) {
				cnpj = (Cnpj) context.convertAnother(reader.getValue(), Cnpj.class);
			} else if ("CPF".equals(reader.getNodeName())) {
				cpf = (Cpf) context.convertAnother(reader.getValue(), Cpf.class);
			} else if ("idEstrangeiro".equals(reader.getNodeName())) {
				idEstrangeiro = reader.getValue();
			} else if ("xNome".equals(reader.getNodeName())) {
				razaoSocial = reader.getValue();
			} else if ("enderDest".equals(reader.getNodeName())) {
				endereco = (Endereco) context.convertAnother(reader.getValue(), Endereco.class);
			} else if ("indIEDest".equals(reader.getNodeName())) {
				indIe = (IndicadorIe) context.convertAnother(reader.getValue(), IndicadorIe.class);
			} else if ("IE".equals(reader.getNodeName())) {
				ie = (InscricaoEstadual) context.convertAnother(reader.getValue(), InscricaoEstadual.class);
			} else if ("ISUF".equals(reader.getNodeName())) {
				inscricaoSuframa = Long.parseLong(reader.getValue());
			} else if ("email".equals(reader.getNodeName())) {
				email = (Email) context.convertAnother(reader.getValue(), Email.class);
			}
			reader.moveUp();
		}
		return new Destinatario(cnpj, cpf, idEstrangeiro, 
				razaoSocial, null, endereco, null, 
				indIe, ie, inscricaoSuframa, email);
	}

}
