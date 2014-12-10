package com.hadrion.comum.notificacao;

import java.io.Serializable;

import com.hadrion.comum.Afirmacao;

public class RastreadorNotificacaoPublicada extends Afirmacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private int versaoConcorrencia;
    private long notificacaoIdMaisRecentePublicada;
    private long rastreadorNotificacaoPublicadaId;
    private String nomeTipo;

    public RastreadorNotificacaoPublicada(String nomeTipo) {
        this();

        this.setNomeTipo(nomeTipo);
    }

    public void falhaQuandoViolarConcorrencia(int versao) {
        this.assertEstadoVerdadeiro(
                versao == this.versaoConcorrencia(),
                "Violação de Concorrência: Dado obsoleto detectado. Entidade já foi modificada.");
    }

    public long notificacaoIdMaisRecentePublicada() {
        return this.notificacaoIdMaisRecentePublicada;
    }

    public void setNotificacaoIdMaisRecentePublicada(long notificacaoIdMaisRecentePublicada) {
        this.notificacaoIdMaisRecentePublicada = notificacaoIdMaisRecentePublicada;
    }

    public long rastreadorNotificacaoPublicadaId() {
        return this.rastreadorNotificacaoPublicadaId;
    }

    public String nomeTipo() {
        return this.nomeTipo;
    }

    @Override
    public boolean equals(Object outroObjeto) {
        boolean equalObjects = false;

        if (outroObjeto != null && this.getClass() == outroObjeto.getClass()) {
            RastreadorNotificacaoPublicada objetoTipado = (RastreadorNotificacaoPublicada) outroObjeto;
            equalObjects =
                this.rastreadorNotificacaoPublicadaId() == objetoTipado.rastreadorNotificacaoPublicadaId() &&
                this.nomeTipo().equals(objetoTipado.nomeTipo()) &&
                this.notificacaoIdMaisRecentePublicada() == objetoTipado.notificacaoIdMaisRecentePublicada();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (11575 * 241)
            + (int) this.rastreadorNotificacaoPublicadaId()
            + (int) this.notificacaoIdMaisRecentePublicada()
            + this.nomeTipo().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "RastreadorNotificacaoPublicada [notificacaoIdMaisRecentePublicada=" + notificacaoIdMaisRecentePublicada
                + ", rastreadorNotificacaoPublicadaId=" + rastreadorNotificacaoPublicadaId + ", nomeTipo=" + nomeTipo + "]";
    }

    protected RastreadorNotificacaoPublicada() {
        super();
    }

    protected int versaoConcorrencia() {
        return this.versaoConcorrencia;
    }

    protected void setVersaoConcorrencia(int versaoConcorrencia) {
        this.versaoConcorrencia = versaoConcorrencia;
    }

    protected void setRastreadorNotificacaoPublicadaId(long rastreadorNotificacaoPublicadaId) {
        this.rastreadorNotificacaoPublicadaId = rastreadorNotificacaoPublicadaId;
    }

    protected void setNomeTipo(String nomeTipo) {
        this.assertArgumentoNaoVazio(nomeTipo, "O nome do rastreador é obrigatório.");
        this.assertTamanhoArgumento(nomeTipo, 100, "O nome do rastreador deve ter 100 caracteres ou menos.");

        this.nomeTipo = nomeTipo;
    }
}
