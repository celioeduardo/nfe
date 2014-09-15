package com.hadrion.nfe.port.adapters.xml.nf;

import com.hadrion.nfe.tipos.Dinheiro;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

class Total {
	private Dinheiro baseCalculoIcms;
	private Dinheiro icms;
	private Dinheiro icmsDesonerado;
	private Dinheiro baseCalculoIcmsSt;
	private Dinheiro st;
	private Dinheiro produtos;
	private Dinheiro frete;
	private Dinheiro seguro;
	private Dinheiro desconto;
	private Dinheiro impostoImportacao;
	private Dinheiro ipi;
	private Dinheiro pis;
	private Dinheiro cofins;
	private Dinheiro outrasDespesasAcessorias;
	private Dinheiro notaFiscal;
	private Dinheiro valorAproximadoTributos;
	
	public Total(HierarchicalStreamReader reader,
			UnmarshallingContext context){
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("vBC".equals(reader.getNodeName())) baseCalculoIcms = obter(reader.getValue(),context);
			if ("vICMS".equals(reader.getNodeName())) icms = obter(reader.getValue(),context);
			if ("vICMSDeson".equals(reader.getNodeName())) icmsDesonerado = obter(reader.getValue(),context);
			if ("vBCST".equals(reader.getNodeName())) baseCalculoIcmsSt = obter(reader.getValue(),context);
			if ("vST".equals(reader.getNodeName()))	st = obter(reader.getValue(),context);
			if ("vProd".equals(reader.getNodeName())) produtos = obter(reader.getValue(),context);
			if ("vFrete".equals(reader.getNodeName())) frete = obter(reader.getValue(),context);
			if ("vSeg".equals(reader.getNodeName())) seguro = obter(reader.getValue(),context);
			if ("vDesc".equals(reader.getNodeName())) desconto = obter(reader.getValue(),context);
			if ("vII".equals(reader.getNodeName())) impostoImportacao = obter(reader.getValue(),context);
			if ("vIPI".equals(reader.getNodeName())) ipi = obter(reader.getValue(),context);
			if ("vPIS".equals(reader.getNodeName())) pis = obter(reader.getValue(),context);
			if ("vCOFINS".equals(reader.getNodeName())) cofins = obter(reader.getValue(),context);
			if ("vOutro".equals(reader.getNodeName())) outrasDespesasAcessorias = obter(reader.getValue(),context);
			if ("vNF".equals(reader.getNodeName())) notaFiscal = obter(reader.getValue(),context);
			if ("vTotTrib".equals(reader.getNodeName())) valorAproximadoTributos = obter(reader.getValue(),context);
			
			reader.moveUp();
		}
		
	}
	
	public Dinheiro getBaseCalculoIcms() {
		return baseCalculoIcms;
	}

	public Dinheiro getIcms() {
		return icms;
	}

	public Dinheiro getIcmsDesonerado() {
		return icmsDesonerado;
	}

	public Dinheiro getBaseCalculoIcmsSt() {
		return baseCalculoIcmsSt;
	}

	public Dinheiro getSt() {
		return st;
	}

	public Dinheiro getProdutos() {
		return produtos;
	}

	public Dinheiro getFrete() {
		return frete;
	}

	public Dinheiro getSeguro() {
		return seguro;
	}

	public Dinheiro getDesconto() {
		return desconto;
	}

	public Dinheiro getImpostoImportacao() {
		return impostoImportacao;
	}

	public Dinheiro getIpi() {
		return ipi;
	}

	public Dinheiro getPis() {
		return pis;
	}

	public Dinheiro getCofins() {
		return cofins;
	}

	public Dinheiro getOutrasDespesasAcessorias() {
		return outrasDespesasAcessorias;
	}

	public Dinheiro getNotaFiscal() {
		return notaFiscal;
	}

	public Dinheiro getValorAproximadoTributos() {
		return valorAproximadoTributos;
	}

	private Dinheiro obter(String valor,UnmarshallingContext context){
		return (Dinheiro) context.convertAnother(valor, Dinheiro.class);
	}
	
}
