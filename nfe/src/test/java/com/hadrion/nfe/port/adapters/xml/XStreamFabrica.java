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
		xstream.registerConverter(new LocalRetiradaConverter());
		xstream.registerConverter(new LocalEntregaConverter());
		xstream.registerConverter(new GtinConverter());
		xstream.registerConverter(new NcmConverter());
		xstream.registerConverter(new CfopConverter());
		xstream.registerConverter(new QuantidadeConverter());
		xstream.registerConverter(new DinheiroConverter());
		xstream.registerConverter(new ChaveAcessoConverter());
		xstream.registerConverter(new AliquotaConverter());
		xstream.registerConverter(new DescritorProdutoConverter());
		xstream.registerConverter(new ExportacaoConverter());
		xstream.registerConverter(new ExportacaoIndiretaConverter());
		xstream.registerConverter(new CombustivelConverter());
		xstream.registerConverter(new CideConverter());
		xstream.registerConverter(new IcmsConverter());
		xstream.registerConverter(new OrigemConverter());
		xstream.registerConverter(new CstConverter());
		xstream.registerConverter(new DeterminacaoBaseCalculoConverter());
		xstream.registerConverter(new DeterminacaoBaseCalculoStConverter());
		xstream.registerConverter(new PercentualConverter());
		xstream.registerConverter(new SubstituicaoTributariaConverter());
		return xstream;
	}
}
