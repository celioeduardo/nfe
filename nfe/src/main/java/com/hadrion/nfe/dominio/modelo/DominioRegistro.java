package com.hadrion.nfe.dominio.modelo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamentoRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Component
public class DominioRegistro implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	@Autowired
	private SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio;
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	public static SolicitacaoCancelamentoRepositorio solicitacaoCancelamentoRepositorio(){
		return (SolicitacaoCancelamentoRepositorio) 
				applicationContext.getBean("solicitacaoCancelamentoRepositorio");
	}
	
	public static NotaFiscalRepositorio notaFiscalRepositorio(){
		return (NotaFiscalRepositorio) 
				applicationContext.getBean("notaFiscalRepositorio");
	}
	
	// public static EtapaRepositorio etapaRepositorio(){
	// return (EtapaRepositorio) applicationContext.getBean("etapaRepositorio");
	// }
	//
	// public static ArtefatoRepositorio artefatoRepositorio(){
	//
	// return (ArtefatoRepositorio)
	// applicationContext.getBean("artefatoRepositorio");
	// }
	//
	// public static MateriaPrimaRepositorio materiaPrimaRepositorio(){
	// return (MateriaPrimaRepositorio)
	// applicationContext.getBean("materiaPrimaRepositorio");
	// }
	// public static SubprodutoRepositorio subprodutoRepositorio(){
	// return (SubprodutoRepositorio)
	// applicationContext.getBean("subprodutoRepositorio");
	// }
	//
	// public static EntradaService entradaService(){
	// return (EntradaService) applicationContext.getBean("entradaService");
	// }

	@Override
	public synchronized void setApplicationContext(
			ApplicationContext applicationContext) throws BeansException {
		if (DominioRegistro.applicationContext == null) {
			DominioRegistro.applicationContext = applicationContext;
		}

	}
}