package com.hadrion.nfe.dominio.modelo.nf.importacao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.util.DataUtil;

public class ImportacaoTest {
	@Test
	public void nova(){
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
		
		assertEquals(
				new Importacao(numero,data,localDesembarque,ufDesembarque,dataDesembarque,viaTransporte,codigoExportador,
						valorArfmm,intermediacao,cnpjTerceiro,ufTerceiro,
						new Adicao(numeroAdicao,sequencia,codigoFabricante)),
				new Importacao(numero,data,localDesembarque,ufDesembarque,dataDesembarque,viaTransporte,codigoExportador,						
						valorArfmm,intermediacao,cnpjTerceiro,ufTerceiro,
						new Adicao(numeroAdicao,sequencia,codigoFabricante))
		);
	}
	@Test
	public void novaAdicao(){
		int numero = 1;
		int sequencia = 1;
		String codigoFabricante = "abc2030";
		assertEquals(
				new Adicao(numero,sequencia,codigoFabricante),
				new Adicao(numero,sequencia,codigoFabricante)
				);
	}
}
