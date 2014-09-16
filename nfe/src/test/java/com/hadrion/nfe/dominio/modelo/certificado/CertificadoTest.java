package com.hadrion.nfe.dominio.modelo.certificado;

import static com.hadrion.util.DataUtil.dataHota;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class CertificadoTest {
	private File arquivo;
	private String senha;
	
	@Before
	public void setUp(){
		arquivo =  FileUtils.getFile("src","test","resources","assinatura","certificado.pfx");
		senha = "12345678";
	}
	
	@Test
	public void dados() throws IOException{
		Certificado certificado = new Certificado(arquivo,senha);
		assertEquals(dataHota("15/04/2014 14:22:00"), certificado.validoAPartir());
		assertEquals(dataHota("15/04/2015 14:22:00"), certificado.validoAte());
		assertNotNull(certificado.privateKey());
		
		Principal principal = certificado.x509Certificate().getSubjectDN();
		assertEquals(
				"CN=COOPERATIVA AGROPECUARIA DO ALTO PARANAIBA:86675642000106, OU=AR KRYPTON, OU=RFB e-CNPJ A1, OU=Secretaria da Receita Federal do Brasil - RFB, O=ICP-Brasil, L=SA O GOTARDO, ST=MG, C=BR",
				principal.getName());

		// Emissor do Certificado
	    principal = certificado.x509Certificate().getIssuerDN();
	    assertEquals(
				"CN=AC SERASA RFB v2, OU=Secretaria da Receita Federal do Brasil - RFB, O=ICP-Brasil, C=BR",
				principal.getName());
	}
}
