package com.hadrion.comum.notificacao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.hadrion.comum.Afirmacao;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_RASTREADOR_NOTIFICACAO")
@Table(name="RASTREADOR_NOTIFICACAO")
public class RastreadorNotificacaoPublicada extends Afirmacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name="VERSAO")
    private int versaoConcorrencia;
    
    @Column(name="ID_MAIS_RECENTE_PUB")
    private long notificacaoIdMaisRecentePublicada;
    
    @Column(name="NOME")
    private String nomeTipo;
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
    private long rastreadorNotificacaoPublicadaId;
    
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
