package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Presenca;
import com.hadrion.nfe.dominio.modelo.nf.Processo;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;

public class NotaFiscalTradutorTest {
	@Mock
	ResultSet rs;
	
	@Before
	public void setUp() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(rs.getString("NFE_GUID")).thenReturn("00FBCC5B1F25E835E050007F01003F32");
		when(rs.getString("NATUREZA_OPERACAO")).thenReturn("VENDA");
		when(rs.getString("FORMA_PAGAMENTO")).thenReturn("A_VISTA");
		when(rs.getString("MODELO")).thenReturn("55");
		when(rs.getLong("SERIE")).thenReturn(1L);
		when(rs.getLong("NUMERO")).thenReturn(1L);
		when(rs.getDate("EMISSAO")).thenReturn(java.sql.Date.valueOf("2014-08-19"));
		when(rs.getTimestamp("DATA_HORA")).thenReturn(java.sql.Timestamp.valueOf("2014-08-19 18:45:00"));
		when(rs.getString("TIPO_OPERACAO")).thenReturn("ENTRADA");
		when(rs.getString("LOCAL_DESTINO")).thenReturn("INTERNA");
		when(rs.getString("UF_FATO_GERADOR")).thenReturn("SP");
		when(rs.getBoolean("CONSUMIDOR_FINAL")).thenReturn(false);
		when(rs.getString("FINALIDADE")).thenReturn("NORMAL");
		when(rs.getString("PRESENCA")).thenReturn("NAO_PRESENCIAL");
		when(rs.getString("PROCESSO")).thenReturn("APLICATIVO_CONTRIBUINTE");
		when(rs.getString("REFERENCIA")).thenReturn("DEVOLUCAO");
		
		when(rs.next()).thenReturn(true).thenReturn(false);		
	}
	
	//@Test
	public void traduzirParaNota(){
	
		NotaFiscal nf = NotaFiscalTradutor.paraNotaFiscal(rs);
		assertEquals(new NotaFiscalId("00FBCC5B1F25E835E050007F01003F32"),nf.notaFiscalId());
		assertEquals("VENDA",nf.naturezaOperacao());
		assertEquals(FormaPagamento.A_VISTA,nf.formaPagamento());
		assertEquals(new Modelo("55"),nf.modelo());
		assertEquals(new Serie(1L),nf.serie());
		assertEquals(new Long(1),nf.numero());
		assertEquals(java.sql.Date.valueOf("2014-08-19"),nf.emissao());
		assertEquals(java.sql.Timestamp.valueOf("2014-08-19 18:45:00"),nf.dataHora());
		assertEquals(TipoOperacao.ENTRADA,nf.tipoOperacao());
		assertEquals(LocalDestino.INTERNA,nf.localDestino());
		assertEquals(Uf.SP,nf.municipioFatoGerador());
		//assertEquals(FormatoDanfe.NORMAL_RETRATO,nf.formatoDanfe());
		assertEquals(Finalidade.NORMAL,nf.finalidade());		
		assertEquals(false,nf.consumidorFinal());
		assertEquals(Presenca.NAO_PRESENCIAL,nf.presenca());
		assertEquals(Processo.APLICATIVO_CONTRIBUINTE,nf.processo());
		//assertEquals(Referencia.DEVOLUCAO,nf.referencia());
		//assertEquals(new NotaFiscalId("00FBCC5B1F25E835E050007F01003F33"),nf.documentoReferenciado().notaFiscalId());
		
	}
}
