package com.hadrion.nfe.dominio.modelo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.comum.dominio.modelo.EventoDominioPublicadorDefault;
import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoRepositorio;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Component
public class DominioRegistro implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	public static SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio(){
		return (SolicitacaoCancelamentoRepositorio) 
				applicationContext.getBean("solicitacaoCancelamentoRepositorio");
	}
	
	public static NotaFiscalRepositorio notaFiscalRepositorio(){
		return (NotaFiscalRepositorio) 
				applicationContext.getBean("notaFiscalRepositorio");
	}
	
	public static LoteRepositorio loteRepositorio(){
		return (LoteRepositorio) 
				applicationContext.getBean("loteRepositorio");
	}
	
	public static EventoDominioPublicador eventoDominioPublicador(){
		return (EventoDominioPublicador) applicationContext.getBean(EventoDominioPublicadorDefault.class);
	}
	@Override
	public synchronized void setApplicationContext(
			ApplicationContext applicationContext) throws BeansException {
		if (DominioRegistro.applicationContext == null) {
			DominioRegistro.applicationContext = applicationContext;
		}

	}
}