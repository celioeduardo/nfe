package com.hadrion.nfe.dominio.modelo.icms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Percentual;

@Embeddable
@Access(AccessType.FIELD)
public class IcmsInterestadual {
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_INT_BC_DEST"))
	protected Dinheiro baseCalculo;

	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_INT_PER_FCP"))
	private Percentual percentualFundoPobreza;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_INT_ALIQ_UF_DEST"))
	private Aliquota aliquotaUfDestino;

	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_INT_ALIQ_UF_DIF"))
	private Aliquota diferencialAliquota;
	
	@Embedded
	@AttributeOverride(name="valor", column=@Column(name="ICMS_INT_PER_PARTILHA"))
	private Percentual percentualPartilha;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_INT_VAL_FCP"))
	private Dinheiro valorFundoPobreza;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_INT_VAL_UF_DEST"))
	private Dinheiro valorUfDestino;
	
	@Embedded
	@AttributeOverride(name="quantia", column=@Column(name="ICMS_INT_VAL_UF_ORIGEM"))
	private Dinheiro valorUfOrigem;
	
	public Dinheiro baseCalculo() {
		return baseCalculo;
	}

	public Percentual percentualFundoPobreza() {
		return percentualFundoPobreza;
	}

	public Aliquota aliquotaUfDestino() {
		return aliquotaUfDestino;
	}

	public Aliquota diferencialAliquota() {
		return diferencialAliquota;
	}

	public Percentual percentualPartilha() {
		return percentualPartilha;
	}

	public Dinheiro valorFundoPobreza() {
		return valorFundoPobreza;
	}

	public Dinheiro valorUfDestino() {
		return valorUfDestino;
	}

	public Dinheiro valorUfOrigem() {
		return valorUfOrigem;
	}

	public IcmsInterestadual(Dinheiro baseCalculo, Percentual percentualFundoPobreza, Aliquota aliquotaUfDestino,
			Aliquota diferencialAliquota, Percentual percentualPartilha, Dinheiro valorFundoPobreza,
			Dinheiro valorUfDestino, Dinheiro valorUfOrigem) {
		super();
		this.baseCalculo = baseCalculo;
		this.percentualFundoPobreza = percentualFundoPobreza;
		this.aliquotaUfDestino = aliquotaUfDestino;
		this.diferencialAliquota = diferencialAliquota;
		this.percentualPartilha = percentualPartilha;
		this.valorFundoPobreza = valorFundoPobreza;
		this.valorUfDestino = valorUfDestino;
		this.valorUfOrigem = valorUfOrigem;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(3197,177)
				.append(baseCalculo())
				.append(percentualFundoPobreza())
				.append(aliquotaUfDestino())
				.append(diferencialAliquota())
				.append(percentualPartilha())
				.append(valorFundoPobreza())
				.append(valorUfDestino())
				.append(valorUfOrigem())
				.toHashCode();
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;
		
		if (objeto != null && this.getClass() == objeto.getClass()) {
			IcmsInterestadual objetoTipado = (IcmsInterestadual) objeto;
			objetosIguais = new EqualsBuilder()
					.append(baseCalculo(), objetoTipado.baseCalculo())
					.append(percentualFundoPobreza(), objetoTipado.percentualFundoPobreza())
					.append(aliquotaUfDestino(), objetoTipado.aliquotaUfDestino())
					.append(diferencialAliquota(), objetoTipado.diferencialAliquota())
					.append(percentualPartilha(), objetoTipado.percentualPartilha())
					.append(valorFundoPobreza(), objetoTipado.valorFundoPobreza())
					.append(valorUfDestino(), objetoTipado.valorUfDestino())
					.append(valorUfOrigem(), objetoTipado.valorUfOrigem())
					.isEquals();
		}
		return objetosIguais;
	}

	@Override
	public String toString() {
		return "IcmsInterestadual ["
			+	"baseCalculo"  + baseCalculo() 
			+	",percentualFundoPobreza" + percentualFundoPobreza()
			+	",aliquotaUfDestino" + aliquotaUfDestino()
			+	",diferencialAliquota" + diferencialAliquota()
			+	",percentualPartilha" + percentualPartilha()
			+	",valorFundoPobreza" + valorFundoPobreza()
			+	",valorUfDestino" + valorUfDestino()
			+	",valorUfOrigem" + valorUfOrigem()
			+	"]";
	}
	/*
	 * Somente para JPA
	 */
	public IcmsInterestadual(){}
}
