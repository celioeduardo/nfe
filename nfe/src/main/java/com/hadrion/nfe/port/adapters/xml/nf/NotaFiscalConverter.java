package com.hadrion.nfe.port.adapters.xml.nf;

import static org.apache.commons.lang.StringUtils.leftPad;

import java.util.ArrayList;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Contingencia;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class NotaFiscalConverter extends AbstractConverter {
	
	private String versaoAplicativo;
	
	public NotaFiscalConverter(String versaoAplicativo){
		this.versaoAplicativo = versaoAplicativo;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return NotaFiscal.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		NotaFiscal nf = (NotaFiscal) source;
		writer.startNode("ide");
		convert("cUF", nf.emitente().endereco().municipio().uf(), writer,context);
		convert("cNF", leftPad(String.valueOf(nf.codigoNumerico()), 8, "0"), writer, context);
		convert("natOp", nf.naturezaOperacao(), writer, context);
		convert("indPag", nf.formaPagamento(), writer, context);
		convert("mod", nf.modelo(), writer, context);
		convert("serie", nf.serie(), writer, context);
		convert("nNF", nf.numero(), writer, context);
		convert("dhEmi", nf.emissao(), writer, context);
		convert("dhSaiEnt", nf.dataHora(), writer, context);
		convert("tpNF", nf.tipoOperacao(), writer, context);
		convert("idDest", nf.localDestino(), writer, context);
		convert("cMunFG", nf.municipioFatoGerador().codigo(), writer, context);
		convert("tpImp", nf.formatoDanfe(), writer, context);
		convert("tpEmis",nf.tipoEmissao().codigo(), writer, context);
		convert("cDV",nf.chaveAcesso().digitoVerificador(), writer, context);
		convert("tpAmb", nf.ambiente(), writer, context);
		convert("finNFe", nf.finalidade(), writer, context);
		convert("indFinal", nf.consumidorFinal() ? 1 : 0, writer, context);
		convert("indPres", nf.presenca(), writer, context);
		convert("procEmi", nf.processo(), writer, context);
		convert("verProc", versaoAplicativo(), writer, context);
		
		if (nf.notaEmContingencia())
			context.convertAnother(nf.contingencia());
		
		for (Referencia referencia : nf.referencias()){
			writer.startNode("NFref");
			context.convertAnother(referencia);
			writer.endNode();
		}
		
		writer.endNode();

		convert("emit", nf.emitente(), writer, context);
		convert("dest", nf.destinatario(), writer, context);
		convert("retirada", nf.localRetirada(), writer, context);
		convert("entrega", nf.localEntrega(), writer, context);

		int i = 1;
		for (Item item : nf.itens()) {
			writer.startNode("det");
			writer.addAttribute("nItem", String.valueOf(i));
			context.convertAnother(item);
			writer.endNode();
			i++;
		}

		writer.startNode("total");
		writer.startNode("ICMSTot");
		convert("vBC", nf.totalBaseCalculoIcms(), writer, context);
		convert("vICMS", nf.totalIcms(), writer, context);
		convert("vICMSDeson", Dinheiro.ZERO, writer, context);
		convert("vBCST", nf.totalBaseCalculoIcmsSt(), writer, context);
		convert("vST", nf.totalIcmsSt(), writer, context);
		convert("vProd", nf.totalProdutos(), writer, context);
		convert("vFrete", nf.totalFrete(), writer, context);
		convert("vSeg", nf.totalSeguro(), writer, context);
		convert("vDesc", nf.totalDesconto(), writer, context);
		convert("vII", Dinheiro.ZERO, writer, context);
		convert("vIPI", Dinheiro.ZERO, writer, context);
		convert("vPIS", nf.totalPis(), writer, context);
		convert("vCOFINS", nf.totalCofins(), writer, context);
		convert("vOutro", nf.outrasDespesasAcessorias(), writer, context);
		convert("vNF", nf.total(), writer, context);
		convert("vTotTrib", nf.totalValorAproximadoTributos(), writer, context);
		writer.endNode();
		writer.endNode();

		writer.startNode("transp");
		context.convertAnother(nf.transporte());
		writer.endNode();

		convertIf("cobr", nf.cobranca(), writer, context);

		writer.startNode("infAdic");
		if (nf.informacaoFisco() != null)
			convertIf("infAdFisco", nf.informacaoFisco().texto(), writer,
					context);
		if (nf.informacaoContribuinte() != null)
			convertIf("infCpl", nf.informacaoContribuinte().texto(), writer,
					context);
		writer.endNode();

		convertIf("exporta", nf.exportacao(), writer, context);
	}

	private String versaoAplicativo() {
		return versaoAplicativo;
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Ambiente ambiente = null;
		Ide ide = null;
		Emitente emitente = null;
		Destinatario destinatario = null;
		LocalRetirada localRetirada = null;
		LocalEntrega localEntrega = null;
		List<Item> itens = new ArrayList<Item>();
//		Total total = null;
		Transporte transporte = null;
		Cobranca cobranca = null;
		InformacaoAdicional informacao = null;
		Exportacao exportacao = null;
		Contingencia contingencia = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("ide"))
				ide = new Ide(reader,context);
			if (reader.getNodeName().equals("emit"))
				emitente = (Emitente) context.convertAnother(reader.getValue(), Emitente.class);
			if (reader.getNodeName().equals("dest"))
				destinatario = (Destinatario) context.convertAnother(reader.getValue(), Destinatario.class);
			if (reader.getNodeName().equals("retirada"))
				localRetirada = (LocalRetirada) context.convertAnother(reader.getValue(), LocalRetirada.class);
			if (reader.getNodeName().equals("entrega"))
				localEntrega = (LocalEntrega) context.convertAnother(reader.getValue(), LocalEntrega.class);
			if (reader.getNodeName().equals("det"))
				itens.add((Item)context.convertAnother(reader.getValue(), Item.class));
//			if (reader.getNodeName().equals("total")){
//				reader.moveDown();
//				total = new Total(reader,context);
//				reader.moveUp();
//			}
			if (reader.getNodeName().equals("transp"))
				transporte = (Transporte) context.convertAnother(reader.getValue(), Transporte.class);
			if (reader.getNodeName().equals("cobr"))
				cobranca = (Cobranca) context.convertAnother(reader.getValue(), Cobranca.class);
			if (reader.getNodeName().equals("infAdic"))
				informacao = new InformacaoAdicional(reader, context);
			if (reader.getNodeName().equals("exporta"))
				exportacao = (Exportacao) context.convertAnother(reader.getValue(), Exportacao.class);
			if (reader.getNodeName().equals("tpAmb"))
				ambiente = (Ambiente) context.convertAnother(reader.getValue(), Ambiente.class);
			reader.moveUp();
		}
		
		if (ide.getDataHoraContingencia() != null)
			contingencia = new Contingencia(
					ide.getDataHoraContingencia(), ide.getJustificativaContingencia());
		
		return new NotaFiscal(
				ambiente,
				null, 
				new FilialId("4007474000116"),
				ide.getNaturezaOperacao(),
				ide.getFormaPagamento(), 
				ide.getModelo(), 
				ide.getSerie(), 
				ide.getNumero(), 
				ide.getEmissao(), 
				ide.getDataSaidaEntrada(),
				ide.getCodigoNumerico(),
				ide.getFormatoDante(),
				ide.getTipoEmissao(),
				ide.getTipoOperacao(), 
				ide.getLocalDestino(), 
				ide.getMunicipioFatoGerador(),
				ide.getConsumidorFinal(), 
				ide.getFinalidade(), 
				ide.getPresenca(), 
				ide.getProcesso(), 
				ide.getReferencias(),
				emitente, destinatario, localRetirada, localEntrega, itens,
				transporte, cobranca, 
				new Informacao(informacao.getFisco(), null),
				new Informacao(informacao.getContribuinte(), null),
				exportacao,contingencia);
	}
}
