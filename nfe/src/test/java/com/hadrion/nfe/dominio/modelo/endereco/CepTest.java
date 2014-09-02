package com.hadrion.nfe.dominio.modelo.endereco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CepTest {
    @Test
    public void equalsCep(){
    	assertEquals(new Cep(35519000L), new Cep(35519000L));
    }
}
