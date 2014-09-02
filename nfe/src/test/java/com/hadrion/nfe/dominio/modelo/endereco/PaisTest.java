package com.hadrion.nfe.dominio.modelo.endereco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PaisTest {
    @Test
    public void equalsPais(){
    	assertEquals(new Pais(1L,"BRASIL"), new Pais(1L,"BRASIL"));
    }
}
