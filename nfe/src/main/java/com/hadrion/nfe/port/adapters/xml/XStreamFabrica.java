package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.port.adapters.xml.cobranca.CobrancaConverter;
import com.hadrion.nfe.port.adapters.xml.cobranca.DuplicataConverter;
import com.hadrion.nfe.port.adapters.xml.cobranca.FaturaConverter;
import com.hadrion.nfe.port.adapters.xml.exportacao.ExportacaoNotaConverter;
import com.hadrion.nfe.port.adapters.xml.nf.ContingenciaConverter;
import com.hadrion.nfe.port.adapters.xml.nf.ModeloConverter;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalIdConverter;
import com.hadrion.nfe.port.adapters.xml.nf.PresencaConverter;
import com.hadrion.nfe.port.adapters.xml.nf.ReferenciaConverter;
import com.hadrion.nfe.port.adapters.xml.transporte.ModalidadeFreteConverter;
import com.hadrion.nfe.port.adapters.xml.transporte.TransportadorConverter;
import com.hadrion.nfe.port.adapters.xml.transporte.TransporteConverter;
import com.hadrion.nfe.port.adapters.xml.transporte.VeiculoConverter;
import com.hadrion.nfe.port.adapters.xml.transporte.VolumeConverter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStreamFabrica {
	
	public static XStream criar(){
		XStream xstream = new XStream(new DomDriver("UTF-8"));
		xstream.setMode(XStream.NO_REFERENCES);
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
		xstream.registerConverter(new EmailConverter());
		xstream.registerConverter(new EmitenteConverter());
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
		xstream.registerConverter(new ExportacaoItemConverter());
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
		xstream.registerConverter(new PisConverter());
		xstream.registerConverter(new CstPisConverter());
		xstream.registerConverter(new CofinsConverter());
		xstream.registerConverter(new CstCofinsConverter());
		xstream.registerConverter(new ImpostoConverter());
		xstream.registerConverter(new ItemConverter());
		xstream.registerConverter(new TransporteConverter());
		xstream.registerConverter(new TransportadorConverter());
		xstream.registerConverter(new VolumeConverter());
		xstream.registerConverter(new ModalidadeFreteConverter());
		xstream.registerConverter(new VeiculoConverter());
		xstream.registerConverter(new CobrancaConverter());
		xstream.registerConverter(new FaturaConverter());
		xstream.registerConverter(new DuplicataConverter());
		xstream.registerConverter(new ExportacaoNotaConverter());
		xstream.registerConverter(new FormaPagamentoConverter());
		xstream.registerConverter(new ModeloConverter());
		xstream.registerConverter(new SerieConverter());
		xstream.registerConverter(new TipoOperacaoConverter());
		xstream.registerConverter(new LocalDestinoConverter());
		xstream.registerConverter(new FinalidadeConverter());
		xstream.registerConverter(new PresencaConverter());
		xstream.registerConverter(new ProcessoConverter());
		xstream.registerConverter(new NotaFiscalIdConverter());
		xstream.registerConverter(new UfConverter());
		xstream.registerConverter(new AmbienteConverter());
		xstream.registerConverter(new ContingenciaConverter());
		xstream.registerConverter(new ReferenciaConverter());
		xstream.registerConverter(new NumeroProtocoloConverter());
		
		return xstream;
	}
}
