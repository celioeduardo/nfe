package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.GsonBuilder;
import com.hadrion.nfe.dominio.modelo.nf.locais.LocalEntrega;
import com.hadrion.nfe.tipos.Cnpj;

public class LocalEntregaTradutorJsonTest {
	
	
	public void traduzirLocalEntrega() throws IOException{
		
		final File file = FileUtils.getFile("src","test", "resources", "local.json");
		
		String json = FileUtils.readFileToString(file);

		final GsonBuilder gsonBuilder = new GsonBuilder()
		.enableComplexMapKeySerialization()
		.serializeNulls();

		LocalEntrega local = gsonBuilder.create().fromJson(json, LocalEntrega.class);		
		
		assertEquals(new Cnpj(7233848000100L),local.cnpj());
	}
	
	/*private class gsonEstrategia implements FieldNamingStrategy
	{
		@Override
		public String translateName(Field field)
		{
			String name = field.getName();
			char newFirstChar = Character.toLowerCase(name.charAt(1));
			return newFirstChar + name.substring(2);
		}
	}*/
}
