package com.hadrion.nfe.port.adapters.portal.inutilizacao;

import static com.hadrion.util.DataUtil.formatarData;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Cnpj;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class InutilizacaoConverter extends AbstractConverter{
	
	private Uf uf;
	private Cnpj cnpj;
	
	InutilizacaoConverter(Uf uf, Cnpj cnpj) {
		this.uf = uf;
		this.cnpj = cnpj;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Inutilizacao.class.isAssignableFrom(type);	
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Inutilizacao inutilizacao = (Inutilizacao) source;
		
		writer.addAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe");
		writer.addAttribute("versao", "3.10");
		
		writer.startNode("infInut");
		writer.addAttribute("Id", id(inutilizacao));
		convert("tpAmb", inutilizacao.ambiente(), writer, context);
		convert("xServ", "INUTILIZAR", writer, context);
		convert("cUF", uf, writer, context);
		convert("ano", anoInutilizacao(), writer, context);
		convert("CNPJ", cnpj, writer, context);
		convert("mod", "55", writer, context);
		convert("serie", inutilizacao.serie(), writer, context);
		convert("nNFIni", inutilizacao.numeroInicial(), writer, context);
		convert("nNFFin", inutilizacao.numeroFinal(), writer, context);
		convert("xJust", inutilizacao.justificativa(), writer, context);
		writer.endNode();
	}
	
	private String id(Inutilizacao inutilizacao){
		return "ID" + 
				lpad(uf.codigo(), 2) +
				lpad(anoInutilizacao(),2) + 
				lpad(cnpj,14) +
				"55" +
				lpad(inutilizacao.serie(),3) +
				lpad(inutilizacao.numeroInicial(),9)+
				lpad(inutilizacao.numeroFinal(),9);
	}
	
	private int anoInutilizacao(){
		return Integer.valueOf(formatarData(new Date(), "yy"));
	}
	
	private String lpad(Object valor, int tamanho){
		return StringUtils.leftPad(  
				valor == null ? "" : String.valueOf(valor), 
				tamanho,"0");
	}

	@Override
	public Inutilizacao unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}

}
