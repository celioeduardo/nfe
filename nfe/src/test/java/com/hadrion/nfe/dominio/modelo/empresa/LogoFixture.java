package com.hadrion.nfe.dominio.modelo.empresa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

public class LogoFixture {
	
	public static File logoFile(){
		return FileUtils.getFile("src","test","resources","report","logo.jpg");
	}
	
	public static InputStream logoInputStream() throws FileNotFoundException{
		
		return new FileInputStream("src/test/resources/report/logo.jpg");
	}

}
