package com.hadrion.nfe.port.adapter.notificacao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.comum.notificacao.RastreadorNotificacaoPublicada;

public interface RastreadorNotificacaoPublicadaSpringData extends JpaRepository<RastreadorNotificacaoPublicada, Long> {

	RastreadorNotificacaoPublicada findByNomeTipo(String nomeTipo);
	
}
