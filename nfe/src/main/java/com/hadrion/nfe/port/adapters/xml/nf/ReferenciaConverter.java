package com.hadrion.nfe.port.adapters.xml.nf;

import static com.hadrion.util.DataUtil.data;
import static com.hadrion.util.DataUtil.formatarData;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ReferenciaConverter extends AbstractConverter{

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return Referencia.class.isAssignableFrom(type);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Referencia ref = (Referencia) source;
		writer.startNode("NFref");
		
		if (ref.referenciaNfe()){
			convert("refNFe",ref.chaveAcesso(),writer,context);
		} else if (ref.referenciaNota1_1A()){
			writer.startNode("refNF");
			convert("cUF",ref.ufEmitente(),writer,context);
			convert("AAMM",formatarEmissao(ref.emissao()),writer,context);
			convert("CNPJ",ref.cnpjEmitente(),writer,context);
			convert("mod",ref.modelo(),writer,context);
			convert("serie",ref.serie(),writer,context);
			convert("nNF",ref.numero(),writer,context);
			writer.endNode();
		} else if (ref.referenciaNotaProdutorRural()){
			writer.startNode("refNFP");
			convert("cUF",ref.ufEmitente(),writer,context);
			convert("AAMM",formatarEmissao(ref.emissao()),writer,context);
			convertIf("CNPJ",ref.cnpjEmitente(),writer,context);
			convertIf("CPF",ref.cpfEmitente(),writer,context);
			convert("IE",ref.ie(),writer,context);
			convert("mod",ref.modelo(),writer,context);
			convert("serie",ref.serie(),writer,context);
			convert("nNF",ref.numero(),writer,context);
			writer.endNode();
		}
		writer.endNode();
	}
	
	private String formatarEmissao(Date emissao){
		return formatarData(emissao, "YYMM");
	}
	
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		ChaveAcesso chave = null;
		Uf ufEmitente = null;
		Date emissao = null;
		Cnpj cnpj = null;
		Cpf cpf = null;
		Modelo modelo = null;
		Serie serie = null;
		InscricaoEstadual ie = null;
		Long numero = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if ("refNFe".equals(reader.getNodeName())) 
					chave = (ChaveAcesso) context.convertAnother(reader.getValue(), ChaveAcesso.class);
				else if ("refNF".equals(reader.getNodeName()) || "refNFP".equals(reader.getNodeName())){
					while (reader.hasMoreChildren()) {
						reader.moveDown();
						if ("cUF".equals(reader.getNodeName()))
							ufEmitente = (Uf) context.convertAnother(reader.getValue(), Uf.class);
						else if ("AAMM".equals(reader.getNodeName()))
							emissao = criarDataPeloAnoMes(reader.getValue());
						else if ("CNPJ".equals(reader.getNodeName()))
							cnpj = (Cnpj) context.convertAnother(reader.getValue(), Cnpj.class);
						else if ("CPF".equals(reader.getNodeName()))
							cpf = (Cpf) context.convertAnother(reader.getValue(), Cpf.class);
						else if ("mod".equals(reader.getNodeName()))
							modelo = (Modelo) context.convertAnother(reader.getValue(), Modelo.class);
						else if ("serie".equals(reader.getNodeName()))
							serie = (Serie) context.convertAnother(reader.getValue(), Serie.class);
						else if ("IE".equals(reader.getNodeName()))
							ie = (InscricaoEstadual) context.convertAnother(reader.getValue(), InscricaoEstadual.class);
						else if ("nNF".equals(reader.getNodeName()))
							numero = (Long) context.convertAnother(reader.getValue(), Long.class);
						reader.moveUp();
					}
				}
				reader.moveUp();
			}
			reader.moveUp();
		}
		if (chave != null)
			return Referencia.nfe(chave);
		else if(Modelo.produtorRural(modelo))
			return cnpj != null ?
				Referencia.produtorRural(ufEmitente, emissao, cnpj, serie, numero, ie) :
				Referencia.produtorRural(ufEmitente, emissao, cpf, serie, numero, ie);
		else if(Modelo.modelo1_1A(modelo))
			return Referencia.modelo_1_1A(modelo, ufEmitente, emissao, cnpj, serie, numero);
		return null;
	}
	
	private Date criarDataPeloAnoMes(String anoMes){
		return data(anoMes+"01","yyMMdd");
	}

}
