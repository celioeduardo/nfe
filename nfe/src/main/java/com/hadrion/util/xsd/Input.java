package com.hadrion.util.xsd;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.w3c.dom.ls.LSInput;

public class Input implements LSInput {

	private String publicId;

	private String systemId;

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	public String getBaseURI() {
		return null;
	}

	public InputStream getByteStream() {
		return null;
	}

	public boolean getCertifiedText() {
		return false;
	}

	public Reader getCharacterStream() {
		return null;
	}

	public String getEncoding() {
		return null;
	}

	public String getStringData() {
		synchronized (inputStream) {
			BOMInputStream r = new BOMInputStream(inputStream);
			try {
				return IOUtils.toString(r);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public String getStringData2() {
		synchronized (inputStream) {
			try {
				BufferedReader r = new BufferedReader(new InputStreamReader(inputStream,"UTF8"));
				r.mark(4);
				if ('\ufeff' != r.read()) //Sem BOM marker 
					r.reset();
				
				StringBuffer sb = new StringBuffer();
				for (String s = ""; (s = r.readLine()) != null;) 
					sb.append(s);
				
				r.close();
				return sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public void setBaseURI(String baseURI) {
	}

	public void setByteStream(InputStream byteStream) {
	}

	public void setCertifiedText(boolean certifiedText) {
	}

	public void setCharacterStream(Reader characterStream) {
	}

	public void setEncoding(String encoding) {
	}

	public void setStringData(String stringData) {
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public BufferedInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(BufferedInputStream inputStream) {
		this.inputStream = inputStream;
	}

	private BufferedInputStream inputStream;

	public Input(String publicId, String sysId, InputStream input) {
		this.publicId = publicId;
		this.systemId = sysId;
		this.inputStream = new BufferedInputStream(input);
	}
}
