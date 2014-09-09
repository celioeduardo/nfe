package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.nf.publico.Crt;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IcmsConverter extends AbstractConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Icms.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		
		Icms icms = (Icms) source;
		
		switch (icms.cst()) {
		case CST_51:
			writer.startNode("ICMS51");
			break;
		default:
			break;
		}
		
		convert("orig", icms.origem(), writer, context);
		convert("CST", icms.cst(), writer, context);
		convert("modBC", icms.determinacaoBaseCalculo(), writer, context);
		convert("pRedBC", icms.percentualReducaoBaseCalculo(), writer, context);
		convert("vBC", icms.baseCalculo(), writer, context);
		convert("pICMS", icms.aliquota(), writer, context);
		convert("vICMSOp", icms.valorSemDiferimento(), writer, context);
		convert("pDif", icms.percentualDiferimento(), writer, context);
		convert("vICMSDif", icms.valorDiferido(), writer, context);
		convert("vICMS", icms.valor(), writer, context);
		
		writer.endNode();
	}
	
	private Dinheiro valorIcmsDaOperacao(Icms icms){
		return icms.baseCalculo().multiplicar(icms.aliquota().valor());
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
