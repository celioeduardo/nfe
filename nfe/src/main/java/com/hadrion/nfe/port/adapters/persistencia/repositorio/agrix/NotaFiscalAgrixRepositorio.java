package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Repository
@Transactional
@Profile("agrix")
public class NotaFiscalAgrixRepositorio implements NotaFiscalRepositorio{

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public List<NotaFiscal> pendentesTransmissao() {
		 return jdbc.query("Select * From VW_NFE_PENDENTES", new RowMapper<NotaFiscal>(){
			@Override
			public NotaFiscal mapRow(ResultSet rs, int rowNum){
				return NotaFiscalTradutor.paraNotaFiscal(rs);
			}
		});
	}

	@Override
	public void salvar(NotaFiscal notaFiscal) {
	}

	@Override
	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId) {
		 return jdbc.query("Select * From VW_NFE Where NFE_GUID = ?",
				 new Object[]{notaFiscalId.id()},
				 new ResultSetExtractor<NotaFiscal>(){
				@Override
				public NotaFiscal extractData(ResultSet rs) throws SQLException{
					if(rs.next())
						return NotaFiscalTradutor.paraNotaFiscal(rs);
					else 
						return null;
				}
			});
	}

	@Override
	public void limpar() {}

}
