package com.hadrion.nfe.port.adapters.xml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.hadrion.nfe.dominio.modelo.icms.IcmsInterestadual;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Percentual;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class IcmsInterestadualConverter extends AbstractConverter implements Converter {

	private NumberFormat fmt;
	
	public IcmsInterestadualConverter(){
		fmt = new DecimalFormat("#0.0000",DecimalFormatSymbols.getInstance(Locale.US));
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return IcmsInterestadual.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
		IcmsInterestadual icms = (IcmsInterestadual) source;
		
		writer.startNode("ICMSUFDest");
		convert("vBCUFDest", icms.baseCalculo(), writer, context);
		convert("pFCPUFDest", formatarPercentualDiferimento(icms.percentualFundoPobreza()), writer, context);
		convert("pICMSUFDest", formatarAliquota(icms.aliquotaUfDestino()), writer, context);
		convert("pICMSInter", formatarAliquota(icms.diferencialAliquota()), writer, context);
		convert("pICMSInterPart", formatarPercentualDiferimento(icms.percentualPartilha()), writer, context);
		convert("vFCPUFDest", icms.valorFundoPobreza(), writer, context);
		convert("vICMSUFDest", icms.valorUfDestino(), writer, context);
		convert("vICMSUFRemet", icms.valorUfOrigem(), writer, context);
		convert("vBC", icms.baseCalculo(), writer, context);
		writer.endNode();
	}
	
	private String formatarAliquota(Aliquota aliquota){
		return fmt.format(aliquota.valor());
	}
	private String formatarPercentualDiferimento(Percentual percentual){
		if (percentual.valor() == 100.0)
			return "100";
		return fmt.format(percentual.valor());
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		// TODO MARCELO escrever unmarshal no converter
		return null;				
	}
	
	

}
