package com.hadrion.nfe.port.adapters.xml;

import static com.hadrion.util.DataUtil.dataHora;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
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
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.FormatoDanfe;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Presenca;
import com.hadrion.nfe.dominio.modelo.nf.Processo;
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
import com.hadrion.nfe.dominio.modelo.nf.item.Exportacao;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
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
import com.hadrion.nfe.dominio.modelo.nf.transporte.TipoVeiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalConverter;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Percentual;
import com.hadrion.nfe.tipos.Quantidade;
import com.hadrion.nfe.tipos.Telefone;

public class NotaFiscalXmlTest extends AbstractXmlTest{
	
	private String XML;
	
	@Before
	public void setUp() {
		super.setUp();
		
		final File json =FileUtils.getFile("src","test", "resources", "nota.xml");
		
		try {
			XML = FileUtils.readFileToString(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		xstream.alias("infNFe", NotaFiscal.class);
	}
	
	@Test
	public void serializar(){
		xstream.registerConverter(
				new NotaFiscalConverter(
						Ambiente.HOMOLOGACAO,"1.0"));
		printXML(nf());
		assertXMLEquals(XML,toXML(nf()));
	}
	
	@Ignore @Test
	public void deserializar(){
		assertEquals(nf(),fromXML(XML));
	}
	private NotaFiscal nf(){
		return new NotaFiscal(
				new NotaFiscalId("12346"),
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
				new Municipio(3111606, "CAMPOS GERAIS", Uf.MG), 
				false, 
				Finalidade.NORMAL, 
				Presenca.OUTROS, 
				Processo.APLICATIVO_CONTRIBUINTE, 
				null, //Referencias
				emitente(), 
				destinatario(), 
				localRetirada(), 
				localEntrega(), 
				itens() , 
				transporte(),
				cobranca(), 
				informacaoFisco(), 
				informacaoContribuinte(), 
				exportacao());
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
						null, new Cpf(57133239191L), "JAIR FRIZON", null, 
						new Endereco(
								"RUA CASEMIRO DE ABREU", 
								"256", null, null, 
								new Municipio(3543402,"RIBEIRAO PRETO", Uf.SP), 
								Pais.BRASIL, null, null)), 
						null, 
				new Veiculo(TipoVeiculo.VEICULO,new Placa(Uf.GO,"KEP2310"),null, null), 
				new Volume(37220, "KG", null, null, 37220.0, 37220.0, null));
	}
	private Destinatario destinatario(){
		return new Destinatario(
			null,
			new Cpf(72014253668L),
			null,
			"DENISIO DONIZETE TEODORO",
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
			IndicadorIe.CONTRIBUINTE,
			new InscricaoEstadual("0012134380047"),
			123456L, //Inscrição Suframa
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
					new Ncm(31052000L),
					"ABCDEF",
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
					new Exportacao(12345678901L, 
						new ExportacaoIndireta(
							123456789012L, 
							new ChaveAcesso("29140600891206000310550010000110017000481161"),
							new Quantidade(50.1234))),
					new Combustivel(123456789L, new Quantidade(568.1234), Uf.SP, 
							new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14)))),
			new Imposto(Dinheiro.ZERO, 
				Icms.cst_51(Origem.NACIONAL,new Dinheiro(1000), Aliquota.ZERO,
						Percentual.ZERO,Percentual.ZERO, DeterminacaoBaseCalculo.VALOR_OPERACAO), 
				new Pis(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null), 
				new Cofins(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null)),
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
		return new Informacao("ICMS DIFERIDO CONFORME ITEM 25 , PARTE 1 DO ANEXO II, ARTIGO 8 DO DECRETO 43.080/2002.", null);
	}
	
	private Informacao informacaoContribuinte(){
		return new Informacao("FAVOR EFETUAR A RETIRADA NO PRAZO MÁXIMO DE 24 HORAS", null);
	}
	
	private com.hadrion.nfe.dominio.modelo.nf.Exportacao exportacao(){
		return new com.hadrion.nfe.dominio.modelo.nf.Exportacao(
				Uf.SP,"LOCAL DE EXPORTACAO","LOCAL DE DESPACHO");
	}
	
	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}	
}