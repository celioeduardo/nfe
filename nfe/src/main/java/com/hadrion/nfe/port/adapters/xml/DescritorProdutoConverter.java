package com.hadrion.nfe.port.adapters.xml;

import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DescritorProdutoConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return DescritorProduto.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		DescritorProduto produto = (DescritorProduto) source;
		novoNo("cProd", produto.codigo(), writer);
		convertIf("cEAN",produto.gtin(),writer,context);
		convert("xProd",produto.descricao(),writer,context);
		convert("NCM",produto.ncm(),writer,context);
		convertIf("NVE",produto.nve(),writer,context);
		convertIf("EXTIPI",produto.extipi(),writer,context);
		convert("CFOP",produto.cfop(),writer,context);
		convert("uCom",produto.unidadeComercial(),writer,context);
		convert("qCom",produto.quantidadeComercial(),writer,context);
		convert("vUnCom",produto.valorUnitarioComercializacao(),writer,context);
		convert("vProd",produto.totalBruto(),writer,context);
		convertIf("cEANTrib",produto.gtinTributavel(),writer,context);
		convert("uTrib",produto.unidadeTributavel(),writer,context);
		convert("qTrib",produto.quantidadeTributavel(),writer,context);
		convert("vUnTrib",produto.valorUnitarioTributacao(),writer,context);
		convert("vFrete",produto.totalFrete(),writer,context);
		convert("vSeg",produto.totalSeguro(),writer,context);
		convert("vDesc",produto.valorDesconto(),writer,context);
		convert("vOutro",produto.outrasDespesasAcessorias(),writer,context);
		convert("indTot",1,writer,context);
		convertIf("detExport",produto.exportacao(),writer,context);
		convertIf("comb",produto.combustivel(),writer,context);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		String codigo = null, descricao = null, unidadeTributavel = null,unidadeComercial = null,extipi = null,nve = null;
		Gtin gtin = null,gtinTributavel = null;  
		Ncm ncm = null;
		Cfop cfop = null;
		Double valorUnitarioComercializacao = null, valorUnitarioTributacao = null; 
		Quantidade quantidadeComercial = null,quantidadeTributavel = null;
		Dinheiro valorTotalBruto = null, totalFrete = null,valorDesconto = null, 
				outrasDespesasAcessorias = null, totalSeguro = null;
		Combustivel combustivel = null;
		ExportacaoItem exportacao = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("cProd".equals(reader.getNodeName())) {
				codigo = reader.getValue();
			} else if ("cEAN".equals(reader.getNodeName())) {
				gtin = (Gtin) context.convertAnother(reader.getValue(),Gtin.class);
			} else if ("xProd".equals(reader.getNodeName())) {
				descricao = reader.getValue();
			} else if ("NCM".equals(reader.getNodeName())) {
				ncm = (Ncm) context.convertAnother(reader.getValue(),Ncm.class);
			} else if ("NVE".equals(reader.getNodeName())) {
				nve = reader.getValue();
			} else if ("EXTIPI".equals(reader.getNodeName())) {
				extipi = reader.getValue();
			} else if ("CFOP".equals(reader.getNodeName())) {
				cfop = (Cfop) context.convertAnother(reader.getValue(),Cfop.class);
			} else if ("uCom".equals(reader.getNodeName())) {
				unidadeComercial = reader.getValue();
			} else if ("qCom".equals(reader.getNodeName())) {
				quantidadeComercial = (Quantidade) context.convertAnother(reader.getValue(),Quantidade.class);
			} else if ("vUnCom".equals(reader.getNodeName())) {
				valorUnitarioComercializacao = (Double) context.convertAnother(reader.getValue(),Double.class);
			} else if ("vProd".equals(reader.getNodeName())) {
				valorTotalBruto = (Dinheiro) context.convertAnother(reader.getValue(),Dinheiro.class);
			} else if ("cEANTrib".equals(reader.getNodeName())) {
				gtinTributavel = (Gtin) context.convertAnother(reader.getValue(),Gtin.class);
			} else if ("uTrib".equals(reader.getNodeName())) {
				unidadeTributavel = reader.getValue();
			} else if ("qTrib".equals(reader.getNodeName())) {
				quantidadeTributavel = (Quantidade) context.convertAnother(reader.getValue(),Quantidade.class);
			} else if ("vUnTrib".equals(reader.getNodeName())) {
				valorUnitarioTributacao = (Double) context.convertAnother(reader.getValue(),Double.class);
			} else if ("vFrete".equals(reader.getNodeName())) {
				totalFrete = (Dinheiro) context.convertAnother(reader.getValue(),Dinheiro.class);
			} else if ("vSeg".equals(reader.getNodeName())) {
				totalSeguro = (Dinheiro) context.convertAnother(reader.getValue(),Dinheiro.class);
			} else if ("vDesc".equals(reader.getNodeName())) {
				valorDesconto = (Dinheiro) context.convertAnother(reader.getValue(),Dinheiro.class);
			} else if ("vOutro".equals(reader.getNodeName())) {
				outrasDespesasAcessorias = (Dinheiro) context.convertAnother(reader.getValue(),Dinheiro.class);
			} else if ("detExport".equals(reader.getNodeName())) {
				exportacao = (ExportacaoItem) context.convertAnother(reader.getValue(),ExportacaoItem.class);
			} else if ("comb".equals(reader.getNodeName())) {
				combustivel = (Combustivel) context.convertAnother(reader.getValue(),Combustivel.class);
			}
			reader.moveUp();
		}
		return new DescritorProduto(
				codigo, gtin, descricao, 
				ncm, nve, extipi, cfop, 
				unidadeComercial, quantidadeComercial, 
				valorUnitarioComercializacao, valorTotalBruto, 
				gtinTributavel, unidadeTributavel, quantidadeTributavel, 
				valorUnitarioTributacao, totalFrete, totalSeguro, 
				valorDesconto, outrasDespesasAcessorias, exportacao, combustivel);
	}

}
