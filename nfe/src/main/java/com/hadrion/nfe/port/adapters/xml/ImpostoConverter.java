package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.IcmsInterestadual;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ImpostoConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Imposto.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Imposto imp = (Imposto) source;
		convertIf("vTotTrib",imp.valorTotalAproximado(),writer,context);
		convertIf("ICMS",imp.icms(),writer,context);
		convertIf("ICMSUFDest",imp.icmsInterestadual(),writer,context);
		convertIf("PIS",imp.pis(),writer,context);
		convertIf("COFINS",imp.cofins(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Dinheiro valorTotalAproximado = null;
		Icms icms = null;
		Pis pis = null;
		Cofins cofins = null;
		IcmsInterestadual icmsInter = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("vTotTrib".equals(reader.getNodeName())) {
				valorTotalAproximado = (Dinheiro) context.convertAnother(reader.getValue(), Dinheiro.class);
			} else if ("ICMS".equals(reader.getNodeName())) {
				icms = (Icms) context.convertAnother(reader.getValue(), Icms.class);
			} else if ("PIS".equals(reader.getNodeName())) {
				pis = (Pis) context.convertAnother(reader.getValue(), Pis.class);
			} else if ("COFINS".equals(reader.getNodeName())) {
				cofins = (Cofins) context.convertAnother(reader.getValue(), Cofins.class);
			} else if ("ICMSUFDEST".equals(reader.getNodeName())) {
				icmsInter = (IcmsInterestadual) context.convertAnother(reader.getValue(), IcmsInterestadual.class);
			}
			reader.moveUp();
		}
		return new Imposto(valorTotalAproximado, icms, pis, cofins, icmsInter);
	}

}
