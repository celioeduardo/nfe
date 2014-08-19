package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;

public class NotaFiscalTradutor {
	
	public static NotaFiscal paraNotaFiscal(ResultSet rs) {
		try {
			NotaFiscal nf= new NotaFiscal(
				new NotaFiscalId(rs.getString("NFE_GUID")),
				rs.getString("NATUREZA_OPERACAO"),
				FormaPagamento.valueOf(rs.getString("FORMA_PAGAMENTO")),
				new Modelo(rs.getString("MODELO")),
				new Serie(rs.getLong("SERIE")),
				rs.getLong("NUMERO"),
				rs.getDate("EMISSAO"),
				rs.getTimestamp("DATA_HORA"),
				TipoOperacao.valueOf(rs.getString("TIPO_OPERACAO")));
			
			return nf;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
