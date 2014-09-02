package com.hadrion.nfe.dominio.modelo.endereco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class MunicipioTest {
    @Test
    public void equalsMunicipio(){
    	assertEquals(new Municipio("NOVA SERRANA - MG",Uf.MG), new Municipio("NOVA SERRANA - MG",Uf.MG));
    }
}
