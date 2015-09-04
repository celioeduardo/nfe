package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.cofins.Cofins;
import com.hadrion.nfe.dominio.modelo.cofins.CstCofins;
import com.hadrion.nfe.dominio.modelo.icms.DeterminacaoBaseCalculo;
import com.hadrion.nfe.dominio.modelo.icms.Icms;
import com.hadrion.nfe.dominio.modelo.icms.Origem;
import com.hadrion.nfe.dominio.modelo.nf.item.Cfop;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoIndireta;
import com.hadrion.nfe.dominio.modelo.nf.item.ExportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.Gtin;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.dominio.modelo.nf.item.Ncm;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ImportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.imposto.Imposto;
import com.hadrion.nfe.dominio.modelo.pis.CstPis;
import com.hadrion.nfe.dominio.modelo.pis.Pis;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.ItemDeserializer;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

public class ItemTradutorJsonTest {
	private static String JSON = 
			"{\r\n" + 
			"    \"produtoId\" : \"013924F59BB64CC4E050007F010060FB\",\r\n" + 
			"    \"codigo\" : \"74\",\r\n" + 
			"    \"descricao\" : \"TOTRIL 1 LT\",\r\n" + 
			"    \"gtin\" : \"1\",\r\n" + 
			"    \"ncm\" : \"38089329\",\r\n" + 
			"    \"nve\" : \"\",\r\n" + 
			"    \"cfop\" : 5102,\r\n" + 
			"    \"extIpi\" : \"\",\r\n" + 
			"    \"unidade\" : \"LT\",\r\n" + 
			"    \"quantidade\" : 24,\r\n" + 
			"    \"unitario\" : 119,\r\n" + 
			"    \"bruto\" : 2856,\r\n" + 
			"    \"gtinTributavel\" : \"1\",\r\n" + 
			"    \"unidadeTributavel\" : \"LT\",\r\n" + 
			"    \"quantidadeTributavel\" : 24,\r\n" + 
			"    \"unitarioTributacao\" : 119,\r\n" + 
			"    \"frete\" : 0,\r\n" + 
			"    \"seguro\" : 0,\r\n" + 
			"    \"desconto\" : 0,\r\n" + 
			"    \"acessorias\" : 0,\r\n" + 
			"	 \"exportacao\" : {\r\n" +
			"	 	\"drawback\" : 999,\r\n" +
			"	 	\"registroExportacao\" : 888,\r\n" +
			"	 	\"chaveNfRecebida\" : \"31131016832651000420550010000199361002699180\",\r\n" +
			"	 	\"quantidadeExportada\" : 777\r\n" +
			"	 }," +
			"    \"combustivel\" : \"\"\r\n" + 
			",\r\n" + 
			"	\"imposto\" : {\r\n" + 
			"		\"icms\" : {\r\n" + 
			"			\"origem\" : 0,\r\n" + 
			"			\"aliquotaSt\" : 0,\r\n" + 
			"			\"aliquota\" : 18,\r\n" + 
			"			\"coeficiente\" : 0.7,\r\n" + 
			"			\"percentualReducaoBc\" : 0.0,\r\n" + 
			"			\"percentualReducaoBcSt\" : 0.0,\r\n" + 
			"			\"cvSt\" : 0,\r\n" + 
			"			\"ivaSt\" : 0,\r\n" + 
			"			\"mvaSt\" : 0,\r\n" + 
			"			\"base\" : 2600.02,\r\n" + 
			"			\"baseSt\" : 0,\r\n" + 
			"			\"reducao\" : 200.57,\r\n" + 
			"			\"valor\" : 468,\r\n" + 
			"			\"ipi\" : 0,\r\n" + 
			"			\"outras\" : 0,\r\n" + 
			"			\"valorSt\" : 0,\r\n" + 
			"			\"st\" : 20\r\n" + 
			"		},\r\n" + 
			"		\"pis\" : {\r\n" + 
			"			\"aliquota\" : 1.65,\r\n" + 
			"			\"base\" : 3513.75,\r\n" + 
			"			\"valor\" : 57.98,\r\n" + 
			"			\"st\" : \"01\"\r\n" + 
			"		},\r\n" + 
			"		\"cofins\" : {\r\n" + 
			"			\"aliquota\" : 7.6,\r\n" + 
			"			\"base\" : 3513.75,\r\n" + 
			"			\"valor\" : 267.05,\r\n" + 
			"			\"st\" : \"01\"\r\n" + 
			"		},\r\n" + 
			"		\"valorAproximadoTributos\" : 0\r\n" + 
			"	},\r\n" +
			"	\"informacaoAdicional\" : \"ADICIONAL\"\r\n" + 
			"}";

	
	private GsonBuilder gsonBuilder;
	private Gson gson;
	
	@Before
	public void setUp(){
		gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Item.class, new ItemDeserializer());
		gson = gsonBuilder.create();
	}

	@Test
	public void converter() throws IOException{
		
		Item item = gson.fromJson(JSON, Item.class);

		assertEquals(new Item(
			new DescritorProduto(
				"74", 
				new Gtin("1"), 
				"TOTRIL 1 LT",
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
				exportacao(),
				null,
				importacao()),
			imposto(),
			"ADICIONAL"),item);
		
	}
	private ExportacaoItem exportacao(){
		return new ExportacaoItem(999L, 
				new ExportacaoIndireta(888L, 
					new ChaveAcesso("31131016832651000420550010000199361002699180"),
					new Quantidade(777.0)));
	}
	private List<ImportacaoItem> importacao(){
		return new ArrayList<ImportacaoItem>();
	}
	private Imposto imposto(){
		return new Imposto(Dinheiro.ZERO, 
				Icms.cst_00(Origem.NACIONAL,new Dinheiro(2600.02), new Aliquota(18),
						DeterminacaoBaseCalculo.VALOR_OPERACAO), 
				new Pis(CstPis.CST_01, new Dinheiro(3513.75), new Aliquota(1.65), .0, new Double(57.98)), 
				new Cofins(CstCofins.CST_01, new Dinheiro(3513.75), new Aliquota(7.6), .0, new Double(267.05)));		
	}
}
