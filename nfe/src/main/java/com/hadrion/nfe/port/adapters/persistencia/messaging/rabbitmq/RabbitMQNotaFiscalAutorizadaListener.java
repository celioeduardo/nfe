package com.hadrion.nfe.port.adapters.persistencia.messaging.rabbitmq;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.nfe.dominio.modelo.lote.NotaFiscalAutorizada;

@Component
//@Profile({"dev", "prod"})
@Profile({"!test"})
public class RabbitMQNotaFiscalAutorizadaListener extends RabbitNfeEventoListener{

//	@Autowired
//	protected EtapaAplicacaoService etapaAplicacaoService;
	
	@Override
	public String[] ouvindoPara() {
		return new String[]{NotaFiscalAutorizada.class.getName()};	
	}

	@Override
	public void tratar(String tipo, String mensagemTexto) {
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
//		NotificationReader reader = new NotificationReader(mensagemTexto);
//		
//		String artefatoId = reader.eventStringValue("artefatoId.id");
//		String perigoId = reader.eventStringValue("perigoId.id");
		
//		IdentificarEtapasComOcorrenciaPerigoRegistradaNoArtefatoComando comando = new IdentificarEtapasComOcorrenciaPerigoRegistradaNoArtefatoComando(
//				artefatoId,perigoId);
//		
//		etapaAplicacaoService.identificarEtapasComOcorrenciaPerigoRegistradaNoArtefato(comando);
		
	}

}
