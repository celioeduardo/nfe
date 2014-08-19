package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;

public class NotaFiscalTradutorTest {
	@Mock
	ResultSet rst;
	
	@Before
	public void setUp() throws SQLException{
		MockitoAnnotations.initMocks(this);
		when(rst.getString("NFE_GUID")).thenReturn("00FBCC5B1F25E835E050007F01003F32");
		when(rst.getString("NATUREZA_OPERACAO")).thenReturn("VENDA");
		when(rst.getString("FORMA_PAGAMENTO")).thenReturn("A_VISTA");
		when(rst.getString("MODELO")).thenReturn("55");
		when(rst.getLong("SERIE")).thenReturn(1L);
		when(rst.getLong("NUMERO")).thenReturn(1L);
		when(rst.getDate("EMISSAO")).thenReturn(java.sql.Date.valueOf("2014-08-19"));
		when(rst.getTimestamp("DATA_HORA")).thenReturn(java.sql.Timestamp.valueOf("2014-08-19 18:45:00"));
		when(rst.getString("TIPO_OPERACAO")).thenReturn("ENTRADA");
		
		when(rst.next()).thenReturn(true).thenReturn(false);
		
	}
	
	@Test
	public void traduzirParaNota(){
	
		NotaFiscal nf = NotaFiscalTradutor.paraNotaFiscal(rst);
		assertEquals(new NotaFiscalId("00FBCC5B1F25E835E050007F01003F32"),nf.notaFiscalId());
		assertEquals("VENDA",nf.naturezaOperacao());
		assertEquals(FormaPagamento.A_VISTA,nf.formaPagamento());
		assertEquals(new Modelo("55"),nf.modelo());
		assertEquals(new Serie(1L),nf.serie());
		assertEquals(new Long(1),nf.numero());
		assertEquals(java.sql.Date.valueOf("2014-08-19"),nf.emissao());
		assertEquals(java.sql.Timestamp.valueOf("2014-08-19 18:45:00"),nf.dataHora());
		assertEquals(TipoOperacao.ENTRADA,nf.tipoOperacao());
	}
}
