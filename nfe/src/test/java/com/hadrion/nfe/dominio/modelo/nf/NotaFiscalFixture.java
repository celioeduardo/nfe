package com.hadrion.nfe.dominio.modelo.nf;

import static com.hadrion.util.DataUtil.data;
import static com.hadrion.util.DataUtil.dataHora;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
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
import com.hadrion.nfe.dominio.modelo.nf.transporte.TipoVeiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transportador;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Transporte;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Volume;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Percentual;
import com.hadrion.nfe.tipos.Quantidade;
import com.hadrion.nfe.tipos.Telefone;

public class NotaFiscalFixture {
	
	public static NotaFiscal nfEmProducao(){
		return nfEmProducao(new NotaFiscalId("12346"));
	}
	public static NotaFiscal nfEmProducao(NotaFiscalId notaFiscalId){
		return new NotaFiscal(
				Ambiente.PRODUCAO,
				notaFiscalId,
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
				new Municipio(3111606, "CAMPOS GERAIS", Uf.MG), 
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
				contingencia());
		
	}
	
	public static NotaFiscal nfEmHomologacao(){
		return nfEmHomologacao(new NotaFiscalId("12346"));
	}
	
	public static NotaFiscal nfEmHomologacao(NotaFiscalId notaFiscalId){
		return new NotaFiscal(
				Ambiente.HOMOLOGACAO,
				notaFiscalId,
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
				new Municipio(3111606, "CAMPOS GERAIS", Uf.MG), 
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
				contingencia());
		
	}
	private static Emitente emitente(){
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
	private static Transporte transporte(){
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
	private static Destinatario destinatario(){
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
			12345678L, //Inscrição Suframa
			new Email("hadrion@hadrion.com.br"));
	}
	
	private static LocalRetirada localRetirada(){
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
	private static LocalEntrega localEntrega(){
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
	
	private static List<Item> itens(){
		List<Item> result = new ArrayList<Item>();
		Item item = new Item( 
			new DescritorProduto(
					"00000000000000006152",
					new Gtin(12345678L),
					"SOJA EM GRAOS DEPOSITO",
					new Ncm(31052000L),
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
							new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14)))),
			new Imposto(Dinheiro.ZERO, 
				Icms.cst_51(Origem.NACIONAL,new Dinheiro(1000), Aliquota.ZERO,
						Percentual.ZERO,new Percentual(1), DeterminacaoBaseCalculo.VALOR_OPERACAO), 
				new Pis(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null), 
				new Cofins(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null)),
			"Informação Adicional");
		result.add(item);
		return result;
	}
	
	private static Cobranca cobranca(){
		return new Cobranca(
				new Fatura("DCO-19936", new Dinheiro(1732.5), new Dinheiro(0.5)), 
				new Duplicata("DCO-19936/1", data("28/10/2013") , new Dinheiro(1732)));
	}
	
	private static Informacao informacaoFisco(){
		return new Informacao("ICMS DIFERIDO CONFORME ITEM 25 , PARTE 1 DO ANEXO II, ARTIGO 8 DO DECRETO 43.080/2002.", null);
	}
	
	private static Informacao informacaoContribuinte(){
		return new Informacao("FAVOR EFETUAR A RETIRADA NO PRAZO MÁXIMO DE 24 HORAS", null);
	}
	
	private static com.hadrion.nfe.dominio.modelo.nf.Exportacao exportacao(){
		return new com.hadrion.nfe.dominio.modelo.nf.Exportacao(
				Uf.SP,"LOCAL DE EXPORTACAO","LOCAL DE DESPACHO");
	}
	
	private static Contingencia contingencia(){
		return new Contingencia(dataHora("24/10/13 15:35:00", TimeZone.getTimeZone("GMT-02:00")), 
				"Teste de entrada em contingência");
	}
	
	private static List<Referencia> referencias(){
		List<Referencia> result = new ArrayList<Referencia>();
		result.add(Referencia.nfe(new ChaveAcesso("31131016832651000420550010000199361002699180")));
		result.add(Referencia.modelo_1_1A(
				new Modelo("1"), Uf.MG, data("24/05/13"),new Cnpj(32750618000165L), new Serie(3), 123456L));
		result.add(Referencia.produtorRural(
				Uf.MG,data("24/05/13"),new Cpf(56115316600L), new Serie(2), 654321L, InscricaoEstadual.ISENTO));
		return result;
	}
	
	public static NotaFiscal nfEmProducaoAutorizada() {
		NotaFiscal nf = nfEmProducao();
		nf.emitida();
		nf.autorizada();
		return nf;
	}
}
