package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.nf.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Fatura;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Adicao;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Importacao;
import com.hadrion.nfe.dominio.modelo.nf.importacao.Intermediacao;
import com.hadrion.nfe.dominio.modelo.nf.importacao.ViaTransporte;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.publico.IndicadorIe;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.NotaFiscalTradutorJson;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Quantidade;
import com.hadrion.nfe.tipos.Telefone;
import com.hadrion.util.DataUtil;

public class NotaFiscalTradutorJsonTest {
	int numero = 123;
	Date data = DataUtil.data("25/08/15");
	String localDesembarque = "SANTOS";
	Uf ufDesembarque = Uf.SP; 
	Date dataDesembarque = DataUtil.data("25/08/15");
	ViaTransporte viaTransporte = ViaTransporte.MARITIMA;
	String codigoExportador = "123";
	Dinheiro valorArfmm = new Dinheiro(1.23);
	Intermediacao intermediacao = Intermediacao.CONTA_PROPRIA;
	Cnpj cnpjTerceiro = new Cnpj(74230061000181L);
	Uf ufTerceiro = Uf.RJ;
	
	int numeroAdicao = 321;
	int sequencia = 1;
	String codigoFabricante = "123456789ABC";
	Dinheiro desconto = new Dinheiro(1.03);
	int drawback = 45;
	int pedido = 654;
	int itemPedido = 1;

	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "json", "nota.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(
				FileUtils.readFileToString(json),
				Ambiente.HOMOLOGACAO);
		NotaFiscal nf = tradutor.converterNotaFiscal()[0];
		
		assertEquals("H-013924F30E424CC4E050007F010060FB",nf.notaFiscalId().id());
		
		assertEquals(new Long(618),nf.numero());
		assertEquals(new Serie(2L),nf.serie());
		assertEquals(new Modelo("55"),nf.modelo());
		assertEquals(data("28/09/2009"),nf.emissao());
		assertEquals(data("28/09/2009"),nf.dataHora());
		assertEquals(TipoOperacao.SAIDA,nf.tipoOperacao());
		assertEquals(LocalDestino.INTERNA,nf.localDestino());
		assertEquals(Finalidade.NORMAL, nf.finalidade());
		assertTrue(nf.temReferencias());
		assertTrue(nf.estaReferenciando(
				Referencia.nfe(new ChaveAcesso("013924F307774CC4E050007F010060FB"))));		
		assertFalse(nf.consumidorFinal());		
		assertEquals(4, nf.itens().size());
		assertEquals(new Item(
				new DescritorProduto(
				"9001", 
				new Gtin("1"), 
				"MILHO",
				new Ncm("38089329"),
				"", 
				"", 
				new Cfop(5102L),				
				"LT",
				new Quantidade(24.0),				
				new Double(119), 
				new Dinheiro(2856),
				new Gtin("1"), 
				"LT",				
				new Quantidade(24.0),
				new Double(119), 
				new Dinheiro(0), 
				Dinheiro.ZERO, 
				Dinheiro.ZERO, 
				Dinheiro.ZERO,
				new ExportacaoItem(999L, 
						new ExportacaoIndireta(888L, 
							new ChaveAcesso("31131016832651000420550010000199361002699180"),
							new Quantidade(777.0))), 
				null),
				new Imposto(Dinheiro.ZERO, 
						Icms.cst_00(Origem.NACIONAL,new Dinheiro(2600.02), new Aliquota(18),
								DeterminacaoBaseCalculo.VALOR_OPERACAO), 
						new Pis(CstPis.CST_01, new Dinheiro(3513.75), new Aliquota(1.65), .0, new Double(57.98)), 
						new Cofins(CstCofins.CST_01, new Dinheiro(3513.75), new Aliquota(7.6), .0, new Double(267.05))),
				"ADICIONAL"),nf.item(0));

		assertEquals(new Emitente(
						new Cnpj(86675642000106L), 
						null, 
						"COOPERATIVA AGROPECUARIA ALTO PARANAIBA", 
						"", 
						new Endereco("ROD. MG 235 KM 89", 
								"443",
								"CX P 37",
								"GUARDA DOS FERREIROS",
							    new Municipio(0,"SAO GOTARDO - MG",Uf.MG),
							    new Pais(1058L,"BRASIL"),
							    new Cep(38800000L),
							    new Telefone("3436161200")),
						new Telefone("3436161200"), 
						new InscricaoEstadual("6219059370082"), 
						new InscricaoEstadual("6219059370082"),
						null), 
					nf.emitente());
		
		assertEquals(new Destinatario(
						new Cnpj(7233848000100L), 
						null, 
						"",
						"OSPER AGROINDUSTRIAL S/A", 
						"", 
						new Endereco("ROD. BR 262 KM 443 S/N ", 
								"S/N",
								"",
								"ZONA RURAL",
							    new Municipio(0,"NOVA SERRANA - MG",Uf.MG),
							    new Pais(1058L,"BRASIL"),
							    new Cep(35519000L),
							    new Telefone("3732322434")),
						new Telefone("3732322434"), 
						IndicadorIe.CONTRIBUINTE,
						new InscricaoEstadual("452332065.00-50"),
						0L, //suframa
						new Email("JEANPETER@osper.ind.br")), 
					nf.destinatario());
		
		assertEquals(new LocalEntrega(
						new Cnpj(7233848000100L), 
						null, 
						new Endereco("ROD. BR 262 KM 443 S/N ",
							    "S/N",
							    "",
							    "ZONA RURAL",
							    new Municipio(0,"NOVA SERRANA - MG",Uf.MG),
							    new Pais(1058L,"BRASIL"),
							    new Cep(35519000L),
							    null))	
						,nf.localEntrega());

		assertEquals(new LocalRetirada(
				new Cnpj(86675642000106L), 
				null, 
				new Endereco("ROD. MG 235 KM 89",
						"443",
						"CX P 37",
						"GUARDA DOS FERREIROS",
						new Municipio(0,"SAO GOTARDO - MG",Uf.MG),
						new Pais(1058L,"BRASIL"),
						new Cep(38800000L),
					    null))	
		,nf.localRetirada());
		
		assertEquals(
			new Cobranca(
					new Fatura("DM-183898", new Dinheiro(3641.4), Dinheiro.ZERO),
					new Duplicata("DM-183898/01", data("05/08/04"), new Dinheiro(2856)),
					new Duplicata("DM-183898/02", data("29/09/04"), new Dinheiro(785.4))),
			nf.cobranca());
		

		assertEquals("Observação do Fisco",nf.informacaoFisco().texto());
		assertEquals("Observação do Contribuinte",nf.informacaoContribuinte().texto());
		
		assertEquals(
				new Exportacao(Uf.MG, "Local do Embarque", "Despacho"),
				nf.exportacao());
		
		assertEquals(new Volume(25,"SACAS","COPAMIL","8930",1000.0,1000.0,null),
				nf.transporte().volumes().get(0));
		
		assertEquals(1,nf.quantidadeImportacoes());
		
		assertEquals(importacao(),nf.obterImportacoes().iterator().next());
		
//		lotes = loteRepositorio.lotesDaNota(nfSemLote.notaFiscalId());
//		assertEquals(0,lotes.size());		
		
	}

	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}
	@SuppressWarnings("unused")
	private Date dataHota(String dataHora){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		try {
			return formatter.parse(dataHora);
		} catch (ParseException e) {
			return null;
		}	
		
	}
	
	public Importacao importacao(){
		
		return new Importacao(numero,data,localDesembarque,ufDesembarque,dataDesembarque,viaTransporte,codigoExportador,
						valorArfmm,intermediacao,cnpjTerceiro,ufTerceiro,
						new HashSet<Adicao>(Arrays.asList(new Adicao(numeroAdicao,sequencia,codigoFabricante,desconto,drawback,pedido,itemPedido))));

	}
}
