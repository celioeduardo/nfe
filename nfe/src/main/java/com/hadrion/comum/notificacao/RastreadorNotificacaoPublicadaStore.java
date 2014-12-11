package com.hadrion.comum.notificacao;

import java.util.List;

public interface RastreadorNotificacaoPublicadaStore {

    public RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada();

    public RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada(String nomeTipo);

    public void rastrearNotificacaoMaisRecentePublicada(
            RastreadorNotificacaoPublicada rastreadorNotificacaoPublicada,
            List<Notificacao> notificacoes);

    public String nomeTipo();
}
