package com.hadrion.nfe.dominio.modelo.lote;
/**
 * 
 * @author celioeduardo
 * 5.5 Número do Recibo de Lote
 * O número do Recibo do Lote deve ser gerado pelo Portal da Secretaria 
 * de Fazenda Estadual, com a seguinte regra de formação:
 * - 2 posições com o Código da UF onde foi entregue o lote (codificação do IBGE);
 * - 1 posição com o Tipo de Autorizador (0 ou 1=SEFAZ normal, 2=Contingência SCAN - RFB,
 *   3=SEFAZ VIRTUAL-RS, 4=SEFAZ VIRTUAL-RFB);
 * - 12 posições numéricas sequenciais.
 * O sistema utiliza a codificação da UF definida pelo IBGE:
￼
	Formação:
	Código da UF - 02 caracteres
	Tipo Autorizador - 01 caracter
	Sequencial - 12 caracteres

*/
public class NumeroReciboLote {
	private String numero;
	
	public NumeroReciboLote(String numero){
		this.numero = numero;
	}
	
	private String numero(){
		return numero;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			NumeroReciboLote objetoTipado = (NumeroReciboLote) objeto;
			objetosIguais = this.numero().equals(objetoTipado.numero());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (1894 * 4) 
				+ this.numero().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "NumeroReciboLote [numero=" + numero() + "]";
	}
	
}
