package com.hadrion.comum.notificacao;

import java.io.Serializable;
import java.util.Date;

import com.hadrion.comum.Afirmacao;
import com.hadrion.comum.dominio.modelo.EventoDominio;

public class Notificacao extends Afirmacao implements Serializable {

    private static final long serialVersionUID = 1L;

    private EventoDominio evento;
    private long notificacaoId;
    private Date ocorridoEm;
    private String nomeTipo;
    private int versao;

    public Notificacao(
            long notificacaoId,
            EventoDominio evento) {

        this.setEvento(evento);
        this.setNotificacaoId(notificacaoId);
        this.setOcorridoEm(evento.ocorridoEm());
        this.setNomeTipo(evento.getClass().getName());
        this.setVersao(evento.versaoEvento());
    }

    @SuppressWarnings("unchecked")
    public <T extends EventoDominio> T evento() {
        return (T) this.evento;
    }

    public long notificacaoId() {
        return this.notificacaoId;
    }

    public Date ocorridoEm() {
        return this.ocorridoEm;
    }

    public String nomeTipo() {
        return this.nomeTipo;
    }

    public int version() {
        return versao;
    }

    @Override
    public boolean equals(Object outroObjeto) {
        boolean equalObjects = false;

        if (outroObjeto != null && this.getClass() == outroObjeto.getClass()) {
            Notificacao objetoTipado = (Notificacao) outroObjeto;
            equalObjects = this.notificacaoId() == objetoTipado.notificacaoId();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (3017 * 197)
            + (int) this.notificacaoId();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Notificacao [evento=" + evento + ", notificacaoId=" + notificacaoId
                + ", ocorridoEm=" + ocorridoEm + ", nomeTipo="
                + nomeTipo + ", versao=" + versao + "]";
    }

    protected Notificacao() {
        super();
    }

    protected void setEvento(EventoDominio evento) {
        this.assertArgumentoNaoNulo(evento, "O evento é obrigatório.");
        
        this.evento = evento;
    }

    protected void setNotificacaoId(long notificacaoId) {
        this.notificacaoId = notificacaoId;
    }

    protected void setOcorridoEm(Date ocorridoEm) {
        this.ocorridoEm = ocorridoEm;
    }

    protected void setNomeTipo(String nomeTipo) {
        this.assertArgumentoNaoVazio(nomeTipo, "O nome do tipo é obrigatório");
        this.assertTamanhoArgumento(nomeTipo, 100, "O nome do tipo deve ter 100 caracteres ou menos.");

        this.nomeTipo = nomeTipo;
    }

    private void setVersao(int versao) {
        this.versao = versao;
    }
}
