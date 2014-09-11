package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.item.Cide;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class NotaFiscalConverter extends AbstractConverter{

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
		convert("cUF",nf.emitente().endereco().municipio().uf(),writer,context);
		//TODO Incluir Chave Acesso na Nota Fiscal
		convert("cNF","00269918",writer,context);
		convert("natOp",nf.naturezaOperacao(),writer,context);
		convert("indPag",nf.formaPagamento(),writer,context);
		convert("mod",nf.modelo(),writer,context);
		convert("serie",nf.serie(),writer,context);
		convert("nNF",nf.numero(),writer,context);
		convert("dEmi",nf.emissao(),writer,context);
		convert("dSaiEnt",nf.dataHora(),writer,context);
		convert("tpNF",nf.tipoOperacao(),writer,context);
		convert("idDest",nf.localDestino(),writer,context);
		convert("cMunFG",nf.municipioFatoGerador().codigo(),writer,context);
		//TODO Definir Tipo de Impressão do DANFE
		convert("tpImp",1,writer,context);
		//TODO Definir Tipo de Emissão do DANFE
		convert("tpEmis",1,writer,context);
		//TODO Digito Verificador da Chave de Acesso
		convert("cDV",0,writer,context);
		//TODO Ambiente
		convert("tpAmb",2,writer,context);
		convert("finNFe",nf.finalidade(),writer,context);
		convert("indFinal",nf.consumidorFinal() ? 1 : 0, writer,context);
		convert("indPres",nf.presenca(), writer,context);
		convert("procEmi",nf.processo(), writer,context);
		//TODO verProc - Informar a versão do aplicativo emissor
		convert("verProc","1.0", writer,context);
		//TODO dhCont - Data e Hora de entrada em contingência
		//TODO xJust - Justificativa de entrada em contingência
		//TODO referencias - Referências
		writer.endNode();
		
		convert("emit",nf.emitente(), writer,context);
		convert("dest",nf.destinatario(), writer,context);
		convert("retirada",nf.localRetirada(), writer,context);
		convert("entrega",nf.localEntrega(), writer,context);
		
		//Itens
		int i = 1;
		for (Item item : nf.itens()) {
			writer.startNode("det");
			writer.addAttribute("nItem", String.valueOf(i));
			context.convertAnother(item);
			writer.endNode();
			i++;
		}
		
		//Totais
		writer.startNode("total");
		writer.startNode("ICMSTot");
		convert("vBC",nf.totalBaseCalculoIcms(),writer,context);
		convert("vICMS",nf.totalIcms(),writer,context);
		convert("vICMSDeson",Dinheiro.ZERO,writer,context);
		convert("vBCST",nf.totalBaseCalculoIcmsSt(),writer,context);
		convert("vST",nf.totalIcmsSt(),writer,context);
		convert("vProd",nf.totalProdutos(),writer,context);
		convert("vFrete",nf.totalFrete(),writer,context);
		convert("vSeg",nf.totalSeguro(),writer,context);
		convert("vDesc",nf.totalDesconto(),writer,context);
		convert("vII",Dinheiro.ZERO,writer,context);
		convert("vIPI",Dinheiro.ZERO,writer,context);
		convert("vPIS",nf.totalPis(),writer,context);
		convert("vCOFINS",nf.totalCofins(),writer,context);
		convert("vOutro",nf.outrasDespesasAcessorias(),writer,context);
		convert("vNF",nf.total(),writer,context);
		convert("vTotTrib",nf.totalValorAproximadoTributos(),writer,context);
		writer.endNode();
		writer.endNode();
		
		//TODO transp - Incluir transporte na Nota Fiscal 
		writer.startNode("transp");
		writer.startNode("modFrete");
		writer.setValue("9");
		writer.endNode();
		writer.endNode();
		
		//Cobrança
		convertIf("cobr",nf.cobranca(),writer,context);
		
		//Informações Adicionais
		writer.startNode("infAdic");
		if (nf.informacaoFisco() != null)
			convertIf("infAdFisco", nf.informacaoFisco().texto(), writer, context);
		if (nf.informacaoContribuinte() != null)
			convertIf("infCpl", nf.informacaoContribuinte().texto(), writer, context);
		writer.endNode();
		
		//Exportação
		convertIf("exporta",nf.exportacao(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Long codAnp = null;
		Quantidade quantidade = null;
		Uf ufConsumo = null;
		Cide cide = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("cProdANP".equals(reader.getNodeName())) {
				codAnp = Long.parseLong(reader.getValue());
			} else if ("qTemp".equals(reader.getNodeName())) {
				quantidade = (Quantidade) context.convertAnother(reader.getValue(), Quantidade.class);
			} else if ("UFCons".equals(reader.getNodeName())) {
				ufConsumo = (Uf) context.convertAnother(reader.getValue(), Uf.class);
			} else if ("CIDE".equals(reader.getNodeName())) {
				cide = (Cide) context.convertAnother(reader.getValue(), Cide.class);
			}
			reader.moveUp();
		}
		return new Combustivel(codAnp, quantidade, ufConsumo, cide);
	}

}
