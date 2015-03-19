package com.hadrion.nfe.port.adapters.agrix.evento;

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
@SequenceGenerator(name="SEQ", sequenceName="SQ_AGRIX_RASTREADOR_EVENTO")
@Table(name="AGRIX_RASTREADOR_EVENTO")
class RastreadorEventoAgrixConsumido extends Afirmacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Version
    @Column(name="VERSAO")
    private int versaoConcorrencia;
    
    @Column(name="ID_MAIS_RECENTE_CONS")
    private long eventoIdMaisRecenteConsumido;
    
    @Column(name="NOME")
    private String nomeTipo;
    
    @Column(name="OWNER")
    private String owner;
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
    private long id;
    
    public RastreadorEventoAgrixConsumido(String nomeTipo, String owner) {
        this();
        this.setNomeTipo(nomeTipo);
        this.setOwner(owner);
    }
    
    private void setOwner(String owner) {
    	this.owner = owner;
	}

	public long eventoIdMaisRecenteConsumido() {
        return this.eventoIdMaisRecenteConsumido;
    }

    public void setEventoIdMaisRecenteConsumido(long eventoIdMaisRecenteConsumido) {
        this.eventoIdMaisRecenteConsumido = eventoIdMaisRecenteConsumido;
    }

    public long id() {
        return this.id;
    }

    public String nomeTipo() {
        return this.nomeTipo;
    }
    
    public String owner(){
    	return this.owner;
    }
    
    @Override
    public boolean equals(Object outroObjeto) {
        boolean equalObjects = false;

        if (outroObjeto != null && this.getClass() == outroObjeto.getClass()) {
            RastreadorEventoAgrixConsumido objetoTipado = (RastreadorEventoAgrixConsumido) outroObjeto;
            equalObjects =
                this.id() == objetoTipado.id() &&
                this.nomeTipo().equals(objetoTipado.nomeTipo()) &&
                this.eventoIdMaisRecenteConsumido() == objetoTipado.eventoIdMaisRecenteConsumido();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (15975 * 141)
            + (int) this.id()
            + (int) this.eventoIdMaisRecenteConsumido()
            + this.nomeTipo().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "RastreadorEventoAgrixConsumido [eventoIdMaisRecenteConsumido=" + eventoIdMaisRecenteConsumido
                + ", id=" + id + ", nomeTipo=" + nomeTipo + "]";
    }

    protected RastreadorEventoAgrixConsumido() {
        super();
    }

    protected int versaoConcorrencia() {
        return this.versaoConcorrencia;
    }

    protected void setVersaoConcorrencia(int versaoConcorrencia) {
        this.versaoConcorrencia = versaoConcorrencia;
    }

    protected void setId(long id) {
        this.id = id;
    }

    protected void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }
}
