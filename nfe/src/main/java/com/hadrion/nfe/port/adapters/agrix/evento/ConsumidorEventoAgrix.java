package com.hadrion.nfe.port.adapters.agrix.evento;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hadrion.nfe.aplicacao.inutilizacao.InutilizacaoAplicacaoService;
import com.hadrion.nfe.aplicacao.inutilizacao.NovaInutilizacaoComando;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.port.adapters.agrix.AgrixUtil;

@Service
@Transactional
class ConsumidorEventoAgrix {
	
	@Autowired
	private InutilizacaoAplicacaoService inutilizacaoAplicacaoService;
	
	@Autowired
	private FilialRepositorio filialRepositorio;
	
	@Autowired
	private RastreadorEventoAgrixConsumidoStore store;
	
	@Autowired
	private EventoAgrixService eventoAgrixService;
	
	void consumirEventos(){
		
		RastreadorEventoAgrixConsumido rastreador = obterRastreador();
		
		List<EventoAgrix> eventos = 
				eventoAgrixService.obterEventosDesde(rastreador.eventoIdMaisRecenteConsumido());
		 
		for (EventoAgrix eventoAgrix : eventos) {
			consumirEvento(eventoAgrix);
			rastreador.setEventoIdMaisRecenteConsumido(eventoAgrix.id());
		}
		
		store.save(rastreador);
		
	}
	
	private RastreadorEventoAgrixConsumido obterRastreador(){
		List<RastreadorEventoAgrixConsumido> rastreadores = store.findAll();
		
		if (rastreadores.size() == 0)
			return new RastreadorEventoAgrixConsumido("RASTREADOR");
		else
			return rastreadores.get(0);
	}
	
	private void consumirEvento(EventoAgrix evento){
		if ("NOTA_SAIDA_INUTILIZADA".contentEquals(evento.type()) || 
				"NOTA_ENTRADA_INUTILIZADA".contentEquals(evento.type()))
			consumirEventoNotaInutilizada(evento.body());
	}
	
	private void consumirEventoNotaInutilizada(String json){
		JsonParser parser = new JsonParser();
	    JsonObject object = parser.parse(json).getAsJsonObject();
	    
	    String guid = object.get("guid").getAsString();
	    int serie = object.get("serie").getAsInt();
	    long numero = object.get("numero").getAsLong();
	    int codigoFilial = object.get("codFilial").getAsInt();
	    long cnpjFilial = object.get("cnpjFilial").getAsLong();
	    String justificativa = object.get("justificativa").getAsString();
		
	    Filial filial = filial(AgrixUtil.paraFilialId(codigoFilial, cnpjFilial));
	    
	    NovaInutilizacaoComando comando = new NovaInutilizacaoComando(
	    		guid,
	    		filial.ambiente(),
	    		serie,
	    		numero,numero,
	    		justificativa,
	    		AgrixUtil.paraFilialId(codigoFilial, cnpjFilial));
	    
	    inutilizacaoAplicacaoService.novaInutilizacao(comando);
	}
	
	private Filial filial(String filialId){
		return filialRepositorio.obterFilial(new FilialId(filialId));
	}
	
}
