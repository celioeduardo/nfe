package com.hadrion.nfe.dominio.modelo.cofins;

/**
 * <p>Código da Situação Tributária para PIS <a href="http://www.nfe.fazenda.gov.br/portal/">portal</a></p>
<ul>
<li>01=Operação Tributável (base de cálculo = valor da
operação alíquota normal (cumulativo/não
cumulativo));</li>
<li>02=Operação Tributável (base de cálculo = valor da
operação (alíquota diferenciada));</li>
</ul>
<br>03=Operação Tributável (base de cálculo = quantidade
vendida x alíquota por unidade de produto);
<br>04=Operação Tributável (tributação monofásica (alíquota
zero));
<br>05=Operação Tributável (Substituição Tributária);
<br>06=Operação Tributável (alíquota zero);
<br>07=Operação Isenta da Contribuição;
<br>08=Operação Sem Incidência da Contribuição;
<br>09=Operação com Suspensão da Contribuição;
<br>49=Outras Operações de Saída;
<br>50=Operação com Direito a Crédito - Vinculada
Exclusivamente a Receita Tributada no Mercado
Interno;
<br>51=Operação com Direito a Crédito - Vinculada
Exclusivamente a Receita Não Tributada no Mercado
Interno;
<br>52=Operação com Direito a Crédito – Vinculada
Exclusivamente a Receita de Exportação;
<br>53=Operação com Direito a Crédito - Vinculada a
Receitas Tributadas e Não-Tributadas no Mercado
Interno;
<br>54=Operação com Direito a Crédito - Vinculada a
Receitas Tributadas no Mercado Interno e de
Exportação;
<br>55=Operação com Direito a Crédito - Vinculada a
Receitas Não-Tributadas no Mercado Interno e de
Exportação;
<br>56=Operação com Direito a Crédito - Vinculada a
Receitas Tributadas e Não-Tributadas no Mercado
Interno, e de Exportação;
<br>60=Crédito Presumido - Operação de Aquisição
Vinculada Exclusivamente a Receita Tributada no
Mercado Interno;
<br>61=Crédito Presumido - Operação de Aquisição
Vinculada Exclusivamente a Receita Não-Tributada no
Mercado Interno;
<br>62=Crédito Presumido - Operação de Aquisição
Vinculada Exclusivamente a Receita de Exportação;
<br>63=Crédito Presumido - Operação de Aquisição
Vinculada a Receitas Tributadas e Não-Tributadas no
Mercado Interno;
<br>64=Crédito Presumido - Operação de Aquisição
Vinculada a Receitas Tributadas no Mercado Interno e
de Exportação;
<br>65=Crédito Presumido - Operação de Aquisição
Vinculada a Receitas Não-Tributadas no Mercado
Interno e de Exportação;
<br>66=Crédito Presumido - Operação de Aquisição
Vinculada a Receitas Tributadas e Não-Tributadas no
Mercado Interno, e de Exportação;
<br>67=Crédito Presumido - Outras Operações;
<br>70=Operação de Aquisição sem Direito a Crédito;
<br>71=Operação de Aquisição com Isenção;
<br>72=Operação de Aquisição com Suspensão;
<br>73=Operação de Aquisição a Alíquota Zero;
<br>74=Operação de Aquisição; sem Incidência da
Contribuição;
<br>75=Operação de Aquisição por Substituição Tributária;
<br>98=Outras Operações de Entrada;
<br>99=Outras Operações;
 */
public enum CstCofins {
	//TODO - Completar Cst COFINS
	CST_01(1),
	CST_02(2),
	CST_03(3),
	CST_04(4),
	CST_05(5),
	CST_06(6),
	CST_07(7),
	CST_08(8),
	CST_09(9),
	CST_99(99);
	
	private int codigo;
	
	CstCofins(int codigo){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}

	public static CstCofins obterPeloCodigo(int codigo) {
		if (codigo == 1) return CST_01;
		if (codigo == 2) return CST_02;
		if (codigo == 3) return CST_03;
		if (codigo == 4) return CST_04;
		if (codigo == 5) return CST_05;
		if (codigo == 6) return CST_06;
		if (codigo == 7) return CST_07;
		if (codigo == 8) return CST_08;
		if (codigo == 9) return CST_09;
		if (codigo == 99) return CST_99;
		return null;
	}
}