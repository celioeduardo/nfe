package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculoSt;
import com.hadrion.nfe.dominio.modelo.icms.SubstituicaoTributaria;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class SubstituicaoTributariaConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return SubstituicaoTributaria.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		SubstituicaoTributaria st = (SubstituicaoTributaria) source;
		convert("modBCST",st.determinacaoBaseCalculo(),writer,context);
		convert("pMVAST",st.percentualMargemValorAdicionado(),writer,context);
		convert("pRedBCST",st.percentualReducaoBaseCalculo(),writer,context);
		convert("vBCST",st.baseCalculo(),writer,context);
		convert("pICMSST",st.aliquota(),writer,context);
		convert("vICMSST",st.valor(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		DeterminacaoBaseCalculoSt determinacaoBaseCalculo = null;
		Percentual percentualReducaoBaseCalculo = null, percentualMargemValorAdicionado = null; 
		Dinheiro baseCalculo = null, valor = null;
		Aliquota aliquota = null;

		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("modBCST".equals(reader.getNodeName())) {
				determinacaoBaseCalculo = (DeterminacaoBaseCalculoSt) context.convertAnother(reader.getValue(), DeterminacaoBaseCalculoSt.class);
			} else if ("pMVAST".equals(reader.getNodeName())) {
				percentualMargemValorAdicionado = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
			} else if ("pRedBCST".equals(reader.getNodeName())) {
				percentualReducaoBaseCalculo = (Percentual) context.convertAnother(reader.getValue(), Percentual.class);
			} else if ("pICMSST".equals(reader.getNodeName())) {
				aliquota = (Aliquota) context.convertAnother(reader.getValue(), Aliquota.class);
			} else if ("vBCST".equals(reader.getNodeName())) {
				baseCalculo = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
			} else if ("vICMSST".equals(reader.getNodeName())) {
				valor = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
			}
			reader.moveUp();
		}
		return new SubstituicaoTributariaConvertida(percentualReducaoBaseCalculo, null, 
				aliquota, determinacaoBaseCalculo, percentualMargemValorAdicionado, baseCalculo, valor);
	}

}
