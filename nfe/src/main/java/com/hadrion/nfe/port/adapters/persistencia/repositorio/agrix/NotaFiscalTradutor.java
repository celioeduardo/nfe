package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;

public class NotaFiscalTradutor {
	
	public static NotaFiscal paraNotaFiscal(ResultSet rs) {
		try {
			return new NotaFiscal(
				new NotaFiscalId(rs.getString("NFE_GUID")),
				rs.getString("NATUREZA_OPERACAO"),
				FormaPagamento.valueOf(rs.getString("FORMA_PAGAMENTO")),
				new Modelo(rs.getString("MODELO")),
				new Serie(rs.getLong("SERIE")),
				rs.getLong("NUMERO"),
				rs.getDate("EMISSAO"),
				rs.getTimestamp("DATA_HORA"),
				TipoOperacao.valueOf(rs.getString("TIPO_OPERACAO")),
				LocalDestino.valueOf(rs.getString("LOCAL_DESTINO")),
				Uf.valueOf(rs.getString("UF_FATO_GERADOR")),
				rs.getBoolean("CONSUMIDOR_FINAL"),
				Finalidade.valueOf(rs.getString("FINALIDADE")));
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
