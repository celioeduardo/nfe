package com.hadrion.nfe.dominio.modelo.certificado;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class CertificadoFixture {
	
	public static Certificado certificado(){
		File arquivo =  FileUtils.getFile("src","test","resources","assinatura","certificado.pfx");
		String senha = "12345678";
		return new Certificado(arquivo,senha);
	}
	
}
