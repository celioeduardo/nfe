package com.hadrion.nfe.port.adapters.xml;

import static com.hadrion.util.DataUtil.data;
import static com.hadrion.util.DataUtil.dataHora;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.icms.Cst;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.nf.Contingencia;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.FormatoDanfe;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Presenca;
import com.hadrion.nfe.dominio.modelo.nf.Processo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Cobranca;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Fatura;
import com.hadrion.nfe.dominio.modelo.nf.informacao.Informacao;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.Cide;
import com.hadrion.nfe.dominio.modelo.nf.item.Combustivel;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalRetirada;
import com.hadrion.nfe.dominio.modelo.nf.publico.Crt;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.publico.IndicadorIe;
import com.hadrion.nfe.dominio.modelo.nf.transporte.ModalidadeFrete;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Placa;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalDeserializador;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Percentual;
import com.hadrion.nfe.tipos.Quantidade;
import com.hadrion.nfe.tipos.Telefone;

public class NotaFiscalXmlDeserializadorTest extends AbstractXmlTest{
	
	private String XML;
	
	@Before
	public void setUp() {
		super.setUp();
		
		final File json =FileUtils.getFile("src","test","resources","nf","nfe.xml");
		
		try {
			XML = FileUtils.readFileToString(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		xstream.alias("infNFe", NotaFiscal.class);
	}

	@Test @Ignore
	public void deserializar(){
		NotaFiscalDeserializador deserializador = new NotaFiscalDeserializador(Ambiente.HOMOLOGACAO);
		NotaFiscal nfDeserializada = deserializador.deserializar(XML);
		NotaFiscal nf = nf();
		assertEquals(nf.chaveAcesso(),nfDeserializada.chaveAcesso());
		assertEquals(nf.naturezaOperacao(),nfDeserializada.naturezaOperacao());
		assertEquals(nf.formaPagamento(),nfDeserializada.formaPagamento());
		assertEquals(nf.modelo(),nfDeserializada.modelo());
		assertEquals(nf.serie(),nfDeserializada.serie());
		assertEquals(nf.serie(),nfDeserializada.serie());
		assertEquals(nf.emissao(),nfDeserializada.emissao());
		assertEquals(nf.dataHora(),nfDeserializada.dataHora());
		assertEquals(nf.codigoNumerico(),nfDeserializada.codigoNumerico());
		assertEquals(nf.formatoDanfe(),nfDeserializada.formatoDanfe());
		assertEquals(nf.tipoEmissao(),nfDeserializada.tipoEmissao());
		assertEquals(nf.localDestino(),nfDeserializada.localDestino());
		assertEquals(nf.municipioFatoGerador(),nfDeserializada.municipioFatoGerador());
		assertEquals(nf.consumidorFinal(),nfDeserializada.consumidorFinal());
		assertEquals(nf.finalidade(),nfDeserializada.finalidade());
		assertEquals(nf.presenca(),nfDeserializada.presenca());
		assertEquals(nf.processo(),nfDeserializada.processo());
		assertEquals(nf.referencias(),nfDeserializada.referencias());
		assertEquals(nf.emitente(),nfDeserializada.emitente());
		assertEquals(nf.destinatario(),nfDeserializada.destinatario());
		assertEquals(nf.localRetirada(),nfDeserializada.localRetirada());
		assertEquals(nf.localEntrega(),nfDeserializada.localEntrega());
		assertEquals(nf.itens(),nfDeserializada.itens());
		assertEquals(nf.transporte(),nfDeserializada.transporte());
		assertEquals(nf.cobranca(),nfDeserializada.cobranca());
		assertEquals(nf.informacaoFisco(),nfDeserializada.informacaoFisco());
		assertEquals(nf.informacaoContribuinte(),nfDeserializada.informacaoContribuinte());
		assertEquals(nf.exportacao(),nfDeserializada.exportacao());
		assertEquals(nf.contingencia(),nfDeserializada.contingencia());
	}
	
	private NotaFiscal nf(){
		return new NotaFiscal(
				Ambiente.HOMOLOGACAO,
				new NotaFiscalId("12346"),
				new FilialId("4007474000116"),
				"VENDA DE PRODUTOS ADQ. TERCEIROS", 
				FormaPagamento.A_PRAZO, 
				new Modelo("55"), 
				new Serie(1L), 
				19936L, 
				dataHora("25/10/2013 00:00:00","GMT-02:00"), 
				dataHora("25/10/2013 00:00:00","GMT-02:00"), 
				269918,
				FormatoDanfe.RETRATO,
				TipoEmissao.NORMAL,
				TipoOperacao.SAIDA, 
				LocalDestino.INTERNA, 
				new Municipio(3111606, null, null), 
				false, 
				Finalidade.NORMAL, 
				Presenca.OUTROS, 
				Processo.APLICATIVO_CONTRIBUINTE, 
				referencias(),
				emitente(), 
				destinatario(), 
				localRetirada(), 
				localEntrega(), 
				itens() , 
				transporte(),
				cobranca(), 
				informacaoFisco(), 
				informacaoContribuinte(), 
				exportacao(),
				contingencia(),
				new NotistaId("COOPADAP"));
		
	}
	private Emitente emitente(){
		return new Emitente(
			new Cnpj(16832651000420L),
			null,
			"COOPERATIVA DOS CAF. DE CAMPOS GERAIS E CAMPO DO MEIO LTDA",
			"COOPERCAM",
			new Endereco(
				"R JOSE PEDRO DE ARAUJO", 
				"448",
				null,
				"DIST. CORREGO DO OURO",
			    new Municipio(3111606,"CAMPOS GERAIS",Uf.MG),
			    Pais.BRASIL,
			    new Cep(37163000),
			    new Telefone("3538535044")),
			null,
			new InscricaoEstadual("1163598340341"),
			null,
			Crt.REGIME_NORMAL);
	}
	private Transporte transporte(){
		return new Transporte(
				ModalidadeFrete.DESTINATARIO_REMETENTE, 
				new Transportador(
						null, new Cpf(57133239191L), "JAIR FRIZON", new InscricaoEstadual("0012134380047"), 
						new Endereco(
								"RUA CASEMIRO DE ABREU 256",null, null, null, 
								new Municipio(3543402,"RIBEIRAO PRETO", Uf.SP), 
								null, null, null)), 
						null, 
				new Veiculo(null,new Placa(Uf.GO,"KEP2310"),null, null), 
				new Volume(37220, "KG", null, null, 37220.0, 37220.0, null));
	}
	private Destinatario destinatario(){
		return new Destinatario(
			new Cnpj(99999999000191L),
			null,
			null,
			"NF-E EMITIDA EM AMBIENTE DE HOMOLOGACAO - SEM VALOR FISCAL",
			null,
			new Endereco(
				"ESTRADA CORREGO DO OURO PARA FAMA KM 02", 
				"S/N",
				null,
				"ZONA RURAL",
				new Municipio(3111606,"CAMPOS GERAIS",Uf.MG),
			    Pais.BRASIL,
			    new Cep(37160000),
			    new Telefone("3599716587")),
			null,
			IndicadorIe.NAO_CONTRIBUINTE,
			new InscricaoEstadual("0012134380047"),
			12345678L, //Inscrição Suframa
			new Email("hadrion@hadrion.com.br"));
	}
	
	private LocalRetirada localRetirada(){
		return new LocalRetirada(
				new Cnpj(86675642000106L),
				null,
				new Endereco(
					"RODOVIA BR 020 KM 449", 
					"S/N",
					"F.ASA BRAN",
					"RODA VELHA",
				    new Municipio(2928901,"SAO DESIDERIO",Uf.BA),
				    null,
				    null,
				    null));
	}
	private LocalEntrega localEntrega(){
		return new LocalEntrega(
				new Cnpj(86675642000106L),
				null,
				new Endereco(
					"RODOVIA BR 020 KM 449", 
					"S/N",
					"F.ASA BRAN",
					"RODA VELHA",
					new Municipio(2928901,"SAO DESIDERIO",Uf.BA),
					null,
					null,
					null));
	}
	
	private List<Item> itens(){
		List<Item> result = new ArrayList<Item>();
		Item item = new Item( 
			new DescritorProduto(
					"00000000000000006152",
					new Gtin(12345678L),
					"SOJA EM GRAOS DEPOSITO",
					new Ncm("31052000"),
					"AB1234",
					"123",
					new Cfop(5102L),
					"SC",
					new Quantidade(30.0),
					59.7716666667,
					new Dinheiro(1793.15),
					new Gtin(12345678L),
					"SC",
					new Quantidade(30.0),
					59.7716666667,
					new Dinheiro(100.0),
					new Dinheiro(99.0),
					new Dinheiro(88.0),
					new Dinheiro(77.0),
					new ExportacaoItem(110203073L, 
						new ExportacaoIndireta(
							123456789012L, 
							new ChaveAcesso("29140600891206000310550010000110017000481161"),
							new Quantidade(50.1234))),
					new Combustivel(110203073L, new Quantidade(568.1234), Uf.SP, 
							new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14))),
					null),
			new Imposto(Dinheiro.ZERO,
				new Icms(Origem.NACIONAL, Cst.CST_51, DeterminacaoBaseCalculo.VALOR_OPERACAO, 
						Percentual.ZERO, Dinheiro.ZERO, Dinheiro.ZERO, new Dinheiro(500.78), Aliquota.ZERO, Dinheiro.ZERO, null, Percentual.ZERO),
				new PisDeserializado(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null,Dinheiro.ZERO), 
				new CofinsDeserializado(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null,Dinheiro.ZERO)),
			"Informação Adicional");
		result.add(item);
		return result;
	}
	
	private Cobranca cobranca(){
		return new Cobranca(
				new Fatura("DCO-19936", new Dinheiro(1732.5), new Dinheiro(0.5)), 
				new Duplicata("DCO-19936/1", data("28/10/2013") , new Dinheiro(1732)));
	}
	
	private Informacao informacaoFisco(){
		return new Informacao("ICMS DIFERIDO CONFORME ITEM 25 , PARTE 1 DO ANEXO II, ARTIGO 8 DO DECRETO 43.080/2002.");
	}
	
	private Informacao informacaoContribuinte(){
		return new Informacao("FAVOR EFETUAR A RETIRADA NO PRAZO MÁXIMO DE 24 HORAS");
	}
	
	private com.hadrion.nfe.dominio.modelo.nf.Exportacao exportacao(){
		return new com.hadrion.nfe.dominio.modelo.nf.Exportacao(
				Uf.SP,"LOCAL DE EXPORTACAO","LOCAL DE DESPACHO");
	}
	
	private Contingencia contingencia(){
		return new Contingencia(dataHora("24/10/13 15:35:00", TimeZone.getTimeZone("GMT-02:00")), 
				"Teste de entrada em contingência");
	}
	
	private List<Referencia> referencias(){
		List<Referencia> result = new ArrayList<Referencia>();
		result.add(Referencia.nfe(new ChaveAcesso("31131016832651000420550010000199361002699180")));
		result.add(Referencia.modelo_1_1A(
				new Modelo("1"), Uf.MG, data("01/05/13"),new Cnpj(32750618000165L), new Serie(3), 123456L));
		result.add(Referencia.produtorRural(
				Uf.MG,data("01/05/13"),new Cpf(56115316600L), new Serie(2), 654321L, InscricaoEstadual.ISENTO));
		return result;
	}
}
