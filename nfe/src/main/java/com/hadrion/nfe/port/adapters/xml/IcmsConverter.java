package com.hadrion.nfe.port.adapters.xml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculoSt;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.icms.SubstituicaoTributaria;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IcmsConverter extends AbstractConverter implements Converter {
	private NumberFormat fmt4decimais;
	
	public IcmsConverter(){
		fmt4decimais = new DecimalFormat("#0.0000",DecimalFormatSymbols.getInstance(Locale.US));
	}
	
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
		case CST_00:
			writer.startNode("ICMS00");
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			convert("modBC", icms.determinacaoBaseCalculo(), writer, context);
			convert("vBC", icms.baseCalculo(), writer, context);
			convert("pICMS", formatarAliquota(icms.aliquota()), writer, context);
			convert("vICMS", icms.valor(), writer, context);
			break;
		case CST_10:
			writer.startNode("ICMS10");
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			convert("modBC", icms.determinacaoBaseCalculo(), writer, context);
			convert("vBC", icms.baseCalculo(), writer, context);
			convert("pICMS", formatarAliquota(icms.aliquota()), writer, context);
			convert("vICMS", icms.valor(), writer, context);
			convert("modBCST", icms.st().determinacaoBaseCalculo(), writer, context);
			convert("pMVAST", icms.st().percentualMargemValorAdicionado(), writer, context);
			convert("pRedBCST", icms.st().percentualReducaoBaseCalculo(), writer, context);
			convert("vBCST", icms.st().baseCalculo(), writer, context);
			convert("pICMSST", icms.st().aliquota(), writer, context);
			convert("vICMSST", icms.st().valor(), writer, context);
			break;
		case CST_20:
			writer.startNode("ICMS20");
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			convert("modBC", icms.determinacaoBaseCalculo(), writer, context);
			convert("pRedBC", icms.percentualReducaoBaseCalculo(), writer, context);
			convert("vBC", icms.baseCalculo(), writer, context);
			convert("pICMS", formatarAliquota(icms.aliquota()), writer, context);
			convert("vICMS", icms.valor(), writer, context);
			break;
		case CST_30:
			writer.startNode("ICMS30");
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			convert("modBCST", icms.st().determinacaoBaseCalculo(), writer, context);
			convert("pMVAST", icms.st().percentualMargemValorAdicionado(), writer, context);
			convert("pRedBCST", icms.st().percentualReducaoBaseCalculo(), writer, context);
			convert("vBCST", icms.st().baseCalculo(), writer, context);
			convert("pICMSST", icms.st().aliquota(), writer, context);
			convert("vICMSST", icms.st().valor(), writer, context);
			break;
		case CST_40:
		case CST_41:
		case CST_50:
			writer.startNode("ICMS40");
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			break;
		case CST_60:
		case CST_90:
			writer.startNode("ICMS"+icms.cst().codigo());
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			break;
		case CST_51:
			writer.startNode("ICMS51");
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			convert("modBC", icms.determinacaoBaseCalculo(), writer, context);
			convert("pRedBC", icms.percentualReducaoBaseCalculo(), writer, context);
			convert("vBC", icms.baseCalculo(), writer, context);
			convert("pICMS", formatarAliquota(icms.aliquota()), writer, context);
			convert("vICMSOp", icms.valorSemDiferimento(), writer, context);
			if (icms.percentualDiferimento().maiorQueZero())
				convert("pDif", formatarPercentualDiferimento(icms.percentualDiferimento()), writer, context);
			convert("vICMSDif", icms.valorDiferido(), writer, context);
			convert("vICMS", icms.valor(), writer, context);
			break;
		case CST_70:
			writer.startNode("ICMS70");
			convert("orig", icms.origem(), writer, context);
			convert("CST", icms.cst(), writer, context);
			convert("modBC", icms.determinacaoBaseCalculo(), writer, context);
			convert("pRedBC", icms.percentualReducaoBaseCalculo(), writer, context);
			convert("vBC", icms.baseCalculo(), writer, context);
			convert("pICMS", formatarAliquota(icms.aliquota()), writer, context);
			convert("vICMS", icms.valor(), writer, context);
			convert("modBCST", icms.st().determinacaoBaseCalculo(), writer, context);
			convert("pMVAST", icms.st().percentualMargemValorAdicionado(), writer, context);
			convert("pRedBCST", icms.st().percentualReducaoBaseCalculo(), writer, context);
			convert("vBCST", icms.st().baseCalculo(), writer, context);
			convert("pICMSST", icms.st().aliquota(), writer, context);
			convert("vICMSST", icms.st().valor(), writer, context);		
			break;
		default:
			break;
		}
		writer.endNode();
	}
	private String formatarAliquota(Aliquota aliquota){
		return fmt4decimais.format(aliquota.valor());
	}
	private String formatarPercentualDiferimento(Percentual percentual){
		if (percentual.valor() == 100.0)
			return "100";
		return fmt4decimais.format(percentual.valor());
	}
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Origem origem = null;
		Cst cst = null; 
		DeterminacaoBaseCalculo determinacaoBaseCalculo= null; 
		DeterminacaoBaseCalculoSt determinacaoBaseCalculoSt = null; 
		
		Percentual percentualReducaoBaseCalculo = null,percentualReducaoBaseCalculoSt = null, 
				percentualMargemValorAdicionado = null; 
		Dinheiro baseCalculo = null, valor = null,baseCalculoSt = null, valorSt = null; 
		Aliquota aliquota = null,aliquotaSt = null; 
		Percentual percentualDiferimento = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if ("orig".equals(reader.getNodeName())) {
					origem = (Origem) context.convertAnother(reader.getValue(), Origem.class);
				} else if ("CST".equals(reader.getNodeName())) {
					cst = (Cst) context.convertAnother(reader.getValue(), Cst.class);
				} else if ("modBC".equals(reader.getNodeName())) {
					 determinacaoBaseCalculo = (DeterminacaoBaseCalculo) context.convertAnother(reader.getValue(), DeterminacaoBaseCalculo.class);
				} else if ("pRedBC".equals(reader.getNodeName())) {
					percentualReducaoBaseCalculo = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
				} else if ("vBC".equals(reader.getNodeName())) {
					baseCalculo = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				} else if ("pICMS".equals(reader.getNodeName())) {
					aliquota = (Aliquota) context.convertAnother(reader.getValue(), Aliquota.class);
				} else if ("vICMS".equals(reader.getNodeName())) {
					valor = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				} else if ("pDif".equals(reader.getNodeName())) {
					percentualDiferimento = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
				} else if ("modBCST".equals(reader.getNodeName())) {
					determinacaoBaseCalculoSt = (DeterminacaoBaseCalculoSt) context.convertAnother(reader.getValue(), DeterminacaoBaseCalculoSt.class);
				} else if ("pMVAST".equals(reader.getNodeName())) {
					percentualMargemValorAdicionado = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
				} else if ("pRedBCST".equals(reader.getNodeName())) {
					percentualReducaoBaseCalculoSt = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
				} else if ("pICMSST".equals(reader.getNodeName())) {
					aliquotaSt = (Aliquota) context.convertAnother(reader.getValue(), Aliquota.class);
				} else if ("vBCST".equals(reader.getNodeName())) {
					baseCalculoSt = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				} else if ("vICMSST".equals(reader.getNodeName())) {
					valorSt = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
				}
				reader.moveUp();
			}
			reader.moveUp();
		}
		
		SubstituicaoTributaria st = null;
		if (determinacaoBaseCalculoSt != null)
			st = new SubstituicaoTributaria(
				baseCalculoSt, 
				aliquotaSt, 
				valorSt,
				percentualReducaoBaseCalculoSt, 
				determinacaoBaseCalculoSt, 
				percentualMargemValorAdicionado);
		
		return new Icms(
				origem, 
				cst, 
				determinacaoBaseCalculo, 
				percentualReducaoBaseCalculo, 
				calcularDescontoRbc(percentualReducaoBaseCalculo, baseCalculo,
						aliquota), 
				null, 
				baseCalculo,
				aliquota,				
				valor, 
				st, 
				percentualDiferimento);
	}

	private Dinheiro calcularDescontoRbc(
			Percentual percentualReducaoBaseCalculo, 
			Dinheiro baseCalculo,Aliquota aliquota) {
		
		if (percentualReducaoBaseCalculo == null)
			return null;
		
		if (percentualReducaoBaseCalculo.maiorQueZero())
			return baseCalculo
				.dividir(1-percentualReducaoBaseCalculo.valor() / 100)
				.multiplicar(percentualReducaoBaseCalculo.valor() / 100)
				.multiplicar(aliquota.valor())
				.dividir(100.0);
		
		return Dinheiro.ZERO;
	}
	

}
