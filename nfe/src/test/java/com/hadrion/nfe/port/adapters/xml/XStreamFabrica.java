package com.hadrion.nfe.port.adapters.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamFabrica {
	public static XStream criar(){
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new CpfConverter());
		xstream.registerConverter(new CnpjConverter());
		xstream.registerConverter(new DateConverter());
		xstream.registerConverter(new FormaPagamentoConverter());
		xstream.registerConverter(new ModeloConverter());
		xstream.registerConverter(new SerieConverter());
		xstream.registerConverter(new TipoOperacaoConverter());
		xstream.registerConverter(new FormatoDanfeConverter());
		xstream.registerConverter(new LocalDestinoConverter());
		xstream.registerConverter(new FinalidadeConverter());
		xstream.registerConverter(new ProcessoConverter());
		xstream.registerConverter(new TelefoneConverter());
		xstream.registerConverter(new InscricaoEstadualConverter());
		xstream.registerConverter(new CrtConverter());
		xstream.registerConverter(new CepConverter());
		xstream.registerConverter(new PaisConverter());
		xstream.registerConverter(new MunicipioConverter());
		xstream.registerConverter(new EnderecoConverter());
		xstream.registerConverter(new EmitenteConverter());
		xstream.registerConverter(new EmailConverter());
		xstream.registerConverter(new DestinatarioConverter());
		xstream.registerConverter(new IndicadorIeConverter());
		return xstream;
	}
}
