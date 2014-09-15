package com.hadrion.nfe.port.adapters.xml.nf;

import java.util.Date;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Presenca;
import com.hadrion.nfe.dominio.modelo.nf.Processo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class NotaFiscalConverter extends AbstractConverter {
	
	private Ambiente ambiente;
	private String versaoAplicativo;
	
	public NotaFiscalConverter(Ambiente ambiente, String versaoAplicativo){
		this.ambiente = ambiente;
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
		convert("cNF", nf.codigoNumerico(), writer, context);
		convert("natOp", nf.naturezaOperacao(), writer, context);
		convert("indPag", nf.formaPagamento(), writer, context);
		convert("mod", nf.modelo(), writer, context);
		convert("serie", nf.serie(), writer, context);
		convert("nNF", nf.numero(), writer, context);
		convert("dEmi", nf.emissao(), writer, context);
		convert("dSaiEnt", nf.dataHora(), writer, context);
		convert("tpNF", nf.tipoOperacao(), writer, context);
		convert("idDest", nf.localDestino(), writer, context);
		convert("cMunFG", nf.municipioFatoGerador().codigo(), writer, context);
		convert("tpImp", nf.formatoDanfe(), writer, context);
		convert("tpEmis",nf.tipoEmissao().codigo(), writer, context);
		convert("cDV",nf.chaveAcesso().digitoVerificador(), writer, context);
		convert("tpAmb", ambiente(), writer, context);
		convert("finNFe", nf.finalidade(), writer, context);
		convert("indFinal", nf.consumidorFinal() ? 1 : 0, writer, context);
		convert("indPres", nf.presenca(), writer, context);
		convert("procEmi", nf.processo(), writer, context);
		convert("verProc", versaoAplicativo(), writer, context);
		
		if (nf.notaEmContingencia())
			context.convertAnother(nf.contingencia());
		
		for (Referencia referencia : nf.referencias())
			context.convertAnother(referencia);
		
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

	private Ambiente ambiente() {
		return this.ambiente;
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		
		List<Item> itens = null;
		Transporte transporte = null;
		Cobranca cobranca = null;
		Informacao informacaoFisco = null, informacaoContribuinte = null;
		Exportacao exportacao = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();

				ide(reader, context);

				emit(reader, context);

				reader.moveUp();
			}
			reader.moveUp();
		}
		return null;
//		return new NotaFiscal(notaFiscalId, chaveAcesso, naturezaOperacao,
//				formaPagamento, modelo, serie, numero, emissao, dataHora,
//				tipoOperacao, localDestino, municipioFatoGerador,
//				consumidorFinal, finalidade, presenca, processo, referencias,
//				emitente, destinatario, localRetirada, localEntrega, itens,
//				transporte, cobranca, informacaoFisco, informacaoContribuinte,
//				exportacao);
	}

	private void ide(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		NotaFiscalId notaFiscalId = null;
		ChaveAcesso chaveAcesso = null;
		String naturezaOperacao = null;
		FormaPagamento formaPagamento = null;
		Modelo modelo = null;
		Serie serie = null;
		Long numero = null;
		Date emissao = null, dataHora = null;
		TipoOperacao tipoOperacao = null;
		LocalDestino localDestino = null;
		Municipio municipioFatoGerador = null;
		boolean consumidorFinal = false;
		Finalidade finalidade = null;
		Presenca presenca = null;
		Processo processo = null;
		List<Referencia> referencias = null;
		Emitente emitente = null;
		Destinatario destinatario = null;
		LocalRetirada localRetirada = null;
		LocalEntrega localEntrega = null;
		//if ("cUF".equals(reader.getNodeName()))
			//uf = (Uf) context.convertAnother(reader.getValue(), Uf.class);
		if ("cNF".equals(reader.getNodeName()))
			chaveAcesso = (ChaveAcesso) context.convertAnother(reader.getValue(), String.class);
		if ("natOp".equals(reader.getNodeName()))
			naturezaOperacao = (String) context.convertAnother(reader.getValue(), String.class);
		if ("indPag".equals(reader.getNodeName()))
			formaPagamento = (FormaPagamento) context.convertAnother(reader.getValue(), FormaPagamento.class);
		if ("mod".equals(reader.getNodeName()))
			naturezaOperacao = (String) context.convertAnother(reader.getValue(), String.class);
		if ("serie".equals(reader.getNodeName()))
			serie = (Serie) context.convertAnother(reader.getValue(),String.class);
		if ("nNF".equals(reader.getNodeName()))
			notaFiscalId = (NotaFiscalId) context.convertAnother(reader.getValue(), String.class);
		if ("dEmi".equals(reader.getNodeName()))
			emissao = (Date) context.convertAnother(reader.getValue(),String.class);
		if ("dSaiEnt".equals(reader.getNodeName()))
			dataHora = (Date) context.convertAnother(reader.getValue(),String.class);
		if ("tpNF".equals(reader.getNodeName()))
			tipoOperacao = (TipoOperacao) context.convertAnother(reader.getValue(), String.class);
		if ("idDest".equals(reader.getNodeName()))
			localDestino = (LocalDestino) context.convertAnother(reader.getValue(), String.class);
		if ("cMunFG".equals(reader.getNodeName()))
			municipioFatoGerador = (Municipio) context.convertAnother(reader.getValue(), String.class);
		// if ("tpImp".equals(reader.getNodeName()))
		// tpe = (String) context.convertAnother(reader.getValue(),
		// String.class);
		// if ("cDV".equals(reader.getNodeName()))
		// naturezaOperacao = (String) context.convertAnother(reader.getValue(),
		// String.class);
		// if ("tpAmb".equals(reader.getNodeName()))
		// naturezaOperacao = (String) context.convertAnother(reader.getValue(),
		// String.class);
		/*
		 * if ("finNFe".equals(reader.getNodeName())) naturezaOperacao =
		 * (String) context.convertAnother(reader.getValue(), String.class); if
		 * ("indFinal".equals(reader.getNodeName())) naturezaOperacao = (String)
		 * context.convertAnother(reader.getValue(), String.class); if
		 * ("indPres".equals(reader.getNodeName())) naturezaOperacao = (String)
		 * context.convertAnother(reader.getValue(), String.class); if
		 * ("procEmi".equals(reader.getNodeName())) naturezaOperacao = (String)
		 * context.convertAnother(reader.getValue(), String.class); if
		 * ("verProc".equals(reader.getNodeName())) naturezaOperacao = (String)
		 * context.convertAnother(reader.getValue(), String.class);
		 */
	}

	private void emit(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
	}
}
