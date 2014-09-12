package com.hadrion.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeradorDVModulo11 {
	private int peso = 2;
	
	private String numero;
	
	public GeradorDVModulo11(String numero){
		this.numero = numero;
	}
	
	public int gerar(){
		this.peso = 2;
		
		if (numero == null || numero.length() == 0)
			return -1;
		
		int[] valores = new int[numero.length()];
		List<Integer> pesos = new ArrayList<Integer>();
		
		for (int i = 0; i < numero.length(); i++){
			try {
				valores[i] = Integer.parseInt(Character.toString(numero.charAt(i)));
			} catch (Exception e) {
				throw new RuntimeException("Prolema na conversão para (int). Posição: "+i
						+", Valor: "+numero.charAt(i)
						+", Número: "+numero);
			}
			
			pesos.add(this.obterPeso());
		}
		
		Collections.reverse(pesos);
		
		int soma = this.somarPonderacao(valores, pesos);
		int digito = this.obterDigito(soma);
		return digito;
	}
	
	private int obterPeso(){
		if (this.peso > 9)
			this.peso = 2;
		int result = this.peso;
		this.peso++;
		return result;
	}
	
	private int somarPonderacao(int[] valores, List<Integer> pesos){
		int resultado = 0;
		int indice = 0;
		for (Integer peso : pesos){
			resultado += peso * valores[indice];
			indice++;
		}
		return resultado;
	}
	
	private int obterDigito(int soma){
		int resultadoDiv = soma % 11;
		if (resultadoDiv == 0 || resultadoDiv == 1)
			return 0;
		int digito = 11 - resultadoDiv;
		return digito;
	}
}
