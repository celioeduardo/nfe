package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	
	
}
