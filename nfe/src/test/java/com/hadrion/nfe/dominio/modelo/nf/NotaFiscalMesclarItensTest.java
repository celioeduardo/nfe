package com.hadrion.nfe.dominio.modelo.nf;

import static com.hadrion.util.DataUtil.dataHora;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
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
import com.hadrion.nfe.dominio.modelo.nf.publico.Crt;
import com.hadrion.nfe.dominio.modelo.nf.publico.Destinatario;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.nf.publico.IndicadorIe;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
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

public class NotaFiscalMesclarItensTest extends DominioTest{
	
	private NotaFiscal nf;
	private NotaFiscal nfAtualizada;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		nf = novaNf(itens());
		nfAtualizada = novaNf(itensAtualizados());
		
		assertEquals(nf.itens().size(), nfAtualizada.itens().size());
		
		nf.mesclar(nfAtualizada);
		
		assertEquals(nf.itens().size(), nfAtualizada.itens().size());
	}
	
	@Test
	public void mesclar(){
		NotaFiscal nf = new NotaFiscal(
				Ambiente.PRODUCAO,
				new NotaFiscalId("123456"),
				new FilialId("4007474000116"));
		
		assertEquals(Ambiente.PRODUCAO,nf.ambiente());
		assertEquals(new FilialId("4007474000116"),nf.filialId());
	}
	
	private static NotaFiscal novaNf(List<Item> itens){
		return new NotaFiscal(
			Ambiente.HOMOLOGACAO,
			new NotaFiscalId("ASDFA654654654ASDF"),
			new FilialId("4007474000116"),
			"VENDA DE PRODUTOS ADQ. TERCEIROS", 
			FormaPagamento.A_PRAZO, 
			new Modelo("55"), 
			new Serie(1L), 
			12345679L, 
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
			null,
			emitente(), 
			destinatario(), 
			null, 
			null, 
			itens, 
			null,
			null, 
			null, 
			null, 
			null,
			null,
			new NotistaId("COOPADAP"));
	}
	
	static List<Item> itens(){
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
							new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14)))),
			new Imposto(Dinheiro.ZERO, 
				Icms.cst_51(Origem.NACIONAL,new Dinheiro(1000), Aliquota.ZERO,
						Percentual.ZERO,Dinheiro.ZERO, new Percentual(1), DeterminacaoBaseCalculo.VALOR_OPERACAO), 
				new Pis(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null), 
				new Cofins(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null)),
			"Informação Adicional");
		result.add(item);
		return result;
	}
	static List<Item> itensAtualizados(){
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
									new Cide(new Dinheiro(500.78), new Aliquota(18.0), new Dinheiro(90.14)))),
									new Imposto(Dinheiro.ZERO, 
										Icms.cst_51(Origem.NACIONAL,new Dinheiro(500), Aliquota.ZERO,
											Percentual.ZERO,Dinheiro.ZERO, new Percentual(1), DeterminacaoBaseCalculo.VALOR_OPERACAO), 
											new Pis(CstPis.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null), 
											new Cofins(CstCofins.CST_99, Dinheiro.ZERO, Aliquota.ZERO, null, null)),
				"Informação Adicional");
		result.add(item);
		return result;
	}
	
	static Emitente emitente(){
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
	static Destinatario destinatario(){
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
}
