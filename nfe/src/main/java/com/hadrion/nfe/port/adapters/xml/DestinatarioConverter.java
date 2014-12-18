package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.Ambiente;
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
	
	private Ambiente ambiente;
	
	public DestinatarioConverter(Ambiente ambiente){
		this.ambiente = ambiente;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Destinatario.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Destinatario dest = (Destinatario) source;
		
		if (ambiente == Ambiente.HOMOLOGACAO){
			convert("CNPJ", new Cnpj(99999999000191L), writer, context);
		} else {
			if (dest.cnpj() != null)
				convert("CNPJ", cnpj(dest), writer, context);
			else if (dest.cpf() != null)
				convert("CPF", cpf(dest), writer, context);
		}
		
		novoNo("xNome", razaoSocial(dest), writer);
		convert("enderDest", dest.endereco(), writer, context);
		convert("indIEDest",indicadorIe(dest),writer, context);
		convertIf("IE",ie(dest),writer, context);
		convertIf("ISUF",dest.inscricaoSuframa(),writer, context);
		convertIf("email",dest.email(),writer,context);
	}
	
	private String razaoSocial(Destinatario destinatario){
		if (ambiente == Ambiente.HOMOLOGACAO)
			return "NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL";
		return destinatario.razaoSocial();
	}
	
	private Cpf cpf(Destinatario dest){
		return dest.cpf();
	} 
	
	private Cnpj cnpj(Destinatario dest){
		return dest.cnpj();
	}
	
	private InscricaoEstadual ie(Destinatario dest){
		if (ambiente == Ambiente.HOMOLOGACAO)
			return null;
		if (indicadorIe(dest) != IndicadorIe.CONTRIBUINTE)
			return null;
		
		return dest.ie();
	} 
	
	private IndicadorIe indicadorIe(Destinatario dest){
		if (ambiente == Ambiente.HOMOLOGACAO)
			return IndicadorIe.NAO_CONTRIBUINTE;
		
		if (operacaoNoExterior(dest))
			return IndicadorIe.NAO_CONTRIBUINTE;
		
		return dest.indicadorIe();
	}
	
	private boolean operacaoNoExterior(Destinatario dest){
		return dest.estrangeiro();
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
