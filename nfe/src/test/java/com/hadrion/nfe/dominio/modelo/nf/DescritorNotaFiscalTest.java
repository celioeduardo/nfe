package com.hadrion.nfe.dominio.modelo.nf;

import static com.hadrion.util.DataUtil.data;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

public class DescritorNotaFiscalTest {
	@Test
	public void equalsModelo(){
		assertEquals(new DescritorNotaFiscal(new NotaFiscalId("0215C27A36D5B0E0E050007F01004D45"),
				"E",
				new Cnpj(86675642000106L),
				new Cnpj(86675642000700L),
				2121L,
				new Serie(1L),
				data("02/09/14"),
				data("02/09/14"),
				"C",
				56L,
				"CEREALISTA SIQUEIRANDRADE LTDA.         ",
				new Dinheiro(1000.)),
				new DescritorNotaFiscal(new NotaFiscalId("0215C27A36D5B0E0E050007F01004D45"),
						"E",
						new Cnpj(86675642000106L),
						new Cnpj(86675642000700L),
						2121L,
						new Serie(1L),
						data("02/09/14"),
						data("02/09/14"),
						"C",
						56L,
						"CEREALISTA SIQUEIRANDRADE LTDA.         ",
						new Dinheiro(1000.)));
	}

}
