package com.hadrion.nfe.dominio.modelo.nf.importacao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ImportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Intermediacao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ViaTransporte;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.util.DataUtil;

public class ImportacaoTest {
	int numero = 1;
	Date data = DataUtil.agora();
	String localDesembarque = "Santos";
	Uf ufDesembarque = Uf.SP; 
	Date dataDesembarque= DataUtil.agora();
	ViaTransporte viaTransporte = ViaTransporte.MARITIMA;
	String codigoExportador = "1a";
	Dinheiro valorArfmm = new Dinheiro(1.23);
	Intermediacao intermediacao = Intermediacao.CONTA_PROPRIA;
	Cnpj cnpjTerceiro=new Cnpj(74230061000181L);
	Uf ufTerceiro = Uf.BA;
	
	int numeroAdicao = 1;
	int sequencia = 1;
	String codigoFabricante = "abc2030";
	Dinheiro desconto = Dinheiro.ZERO;
	int pedido=123;
	int itemPedido=456;		
	
	@Test
	public void nova(){
		
		assertEquals(
				new ImportacaoItem(numero,data,localDesembarque,ufDesembarque,dataDesembarque,viaTransporte,codigoExportador,
						valorArfmm,intermediacao,cnpjTerceiro,ufTerceiro,pedido,itemPedido,
								Arrays.asList(
										new Adicao(numeroAdicao,sequencia,codigoFabricante,desconto))),
				new ImportacaoItem(numero,data,localDesembarque,ufDesembarque,dataDesembarque,viaTransporte,codigoExportador,						
						valorArfmm,intermediacao,cnpjTerceiro,ufTerceiro,pedido,itemPedido,
								Arrays.asList(
										new Adicao(numeroAdicao,sequencia,codigoFabricante,desconto)))
		);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void novaNulos(){
		
		assertEquals(
				new ImportacaoItem(numero,data,localDesembarque,ufDesembarque,dataDesembarque,viaTransporte,codigoExportador,
						valorArfmm,intermediacao,cnpjTerceiro,ufTerceiro,pedido,itemPedido,
						null),
				new ImportacaoItem(numero,data,localDesembarque,ufDesembarque,dataDesembarque,viaTransporte,codigoExportador,						
						valorArfmm,intermediacao,cnpjTerceiro,ufTerceiro,pedido,itemPedido,
						null));
	}

	@Test
	public void novaAdicao(){
		assertEquals(
				new Adicao(numeroAdicao,sequencia,codigoFabricante,desconto),
				new Adicao(numeroAdicao,sequencia,codigoFabricante,desconto));
	}
	
	@Test
	public void novaAdicaoNulos(){
		
		Adicao adicao = new Adicao(numeroAdicao,sequencia,codigoFabricante,null);		
		assertEquals(numeroAdicao, adicao.numero());
		assertEquals(sequencia, adicao.sequencia());
		assertEquals(codigoFabricante, adicao.fabricante());
		assertEquals(Optional.empty(), adicao.desconto());
				
	}
}
