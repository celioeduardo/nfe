package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.sql.ResultSet;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;

public class NotaFiscalTradutor {
	
	public static NotaFiscal paraNotaFiscal(ResultSet rs) {
//		try {
//			NotaFiscal nf = new NotaFiscal(
//				new NotaFiscalId(rs.getString("NFE_GUID")),
//				rs.getString("NATUREZA_OPERACAO"),
//				FormaPagamento.valueOf(rs.getString("FORMA_PAGAMENTO")),
//				new Modelo(rs.getString("MODELO")),
//				new Serie(rs.getLong("SERIE")),
//				rs.getLong("NUMERO"),
//				rs.getDate("EMISSAO"),
//				rs.getTimestamp("DATA_HORA"),
//				TipoOperacao.valueOf(rs.getString("TIPO_OPERACAO")),
//				LocalDestino.valueOf(rs.getString("LOCAL_DESTINO")),
//				Uf.valueOf(rs.getString("UF_FATO_GERADOR")),
//				rs.getBoolean("CONSUMIDOR_FINAL"),
//				Finalidade.valueOf(rs.getString("FINALIDADE")),
//				Presenca.valueOf(rs.getString("PRESENCA")),
//				Processo.valueOf(rs.getString("PROCESSO")),
//				null);
//			//if (rs.getString("PROCESSO")==null)
//			
//			return nf;
//		} catch (SQLException e) {
//			throw new RuntimeException(e.getMessage());
//		}
		
		return null;
	}
}
