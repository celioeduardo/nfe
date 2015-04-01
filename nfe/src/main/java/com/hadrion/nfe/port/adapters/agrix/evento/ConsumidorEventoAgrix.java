package com.hadrion.nfe.port.adapters.agrix.evento;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
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
		
		List<RastreadorEventoAgrixConsumido> rastreadores = obterRastreadores();
		
		for (RastreadorEventoAgrixConsumido rastreador : rastreadores) {
			List<EventoAgrix> eventos = 
					eventoAgrixService.obterEventosDesde(
							rastreador.eventoIdMaisRecenteConsumido(),
							rastreador.owner());
			 
			for (EventoAgrix eventoAgrix : eventos) {
				consumirEvento(eventoAgrix);
				rastreador.setEventoIdMaisRecenteConsumido(eventoAgrix.id());
			}
			
			store.save(rastreador);	
		}
		
	}
	
	private List<RastreadorEventoAgrixConsumido> obterRastreadores(){
		List<RastreadorEventoAgrixConsumido> rastreadores = store.findAll();
		
		if (rastreadores.size() == 0)
			return Collections.singletonList(new RastreadorEventoAgrixConsumido("RASTREADOR",null));
		else
			return rastreadores;
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
	    String serie = object.get("serie").getAsString();
	    
	    long numero = object.get("numero").getAsLong();
	    int codigoFilial = object.get("codFilial").getAsInt();
	    long cnpjFilial = object.get("cnpjFilial").getAsLong();
	    String justificativa = object.get("justificativa").getAsString();
		
	    Filial filial = filial(AgrixUtil.paraFilialId(codigoFilial, cnpjFilial));
	    
	    if (StringUtils.isBlank(serie))
	    	serie = "0";
	    
	    NovaInutilizacaoComando comando = new NovaInutilizacaoComando(
	    		guid,
	    		filial.ambiente(),
	    		Integer.parseInt(serie),
	    		numero,numero,
	    		justificativa,
	    		AgrixUtil.paraFilialId(codigoFilial, cnpjFilial));
	    
	    inutilizacaoAplicacaoService.novaInutilizacao(comando);
	}
	
	private Filial filial(String filialId){
		return filialRepositorio.obterFilial(new FilialId(filialId));
	}
	
}
