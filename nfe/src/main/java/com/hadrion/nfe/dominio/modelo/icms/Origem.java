package com.hadrion.nfe.dominio.modelo.icms;
/*
0=Nacional, exceto as indicadas nos códigos 3 a 5;
1=Estrangeira - Importação direta, exceto o código 6;
2=Estrangeira - Adquirida no mercado interno, exceto a
indicada no código 7;
3=Nacional, mercadoria ou bem com Conteúdo de
Importação superior a 40%;
4=Nacional, em conformidade com processos produtivos
Nota Fiscal eletrônica
Nota Técnica 2013.005
básicos previstos em legislação;
5=Nacional, mercadoria ou bem com Conteúdo de
Importação inferior ou igual a 40%;
6=Estrangeira - Importação direta, sem similar nacional,
constante em lista da CAMEX e gás natural;
7=Estrangeira - Adquirida no mercado interno, sem similar
nacional, constante em lista da CAMEX e gás natural.
 */
public enum Origem {
	NACIONAL(0),
	EXTRANGEIRA_IMP_DIRETA(1),
	EXTRANGEIRA_MERC_INTERNO(2),
	NACIONAL_SUPERIOR_40(3),
	NACIONAL_PROC_PRODUTIVO(4),
	NACIONAL_INFERIOR_40(5),
	EXTRANGEIRA_IMPORTACAO_CAMEX(6),
	EXTRANGEIRA_MERC_INTERNO_CAMEX(7);
	
	private int codigo;
	
	Origem(int codigo){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
}
