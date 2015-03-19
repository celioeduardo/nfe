package com.hadrion.nfe.dominio.modelo.nf;

import java.util.List;

import com.hadrion.nfe.tipos.Email;

public interface ObterEmailService {
	
	List<Email> obterEmailsContatoDaNotaFiscal(NotaFiscalId notaFiscalId,String filialId);

}
