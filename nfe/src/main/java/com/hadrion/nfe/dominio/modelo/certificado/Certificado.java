package com.hadrion.nfe.dominio.modelo.certificado;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.apache.commons.io.FileUtils;

@Embeddable
@Access(AccessType.FIELD)
public class Certificado {
	
	@Transient //TODO persistir como blob - usar byte array? 
	private InputStream pkcs12;
	
	@Column(name="SENHA")
	private String senha;
	
	@Transient
	private String alias;
	@Transient
	private KeyStore ks;
	
	public Certificado(InputStream pkcs12, String senha) {
		super();
		this.pkcs12 = pkcs12;
		this.senha = senha;
	}
	
	public Certificado(File file, String senha){
		try {
			this.pkcs12 = FileUtils.openInputStream(file);
			this.senha = senha;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String senha(){
		return senha;
	}

	private InputStream pkcs12() {
		return pkcs12;
	}

	public PrivateKey privateKey() {
		try {
			return (PrivateKey)keyStore().getKey(alias(), senha().toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException
				| NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public X509Certificate x509Certificate() {
		try {
			return (X509Certificate) keyStore().getCertificate(alias());
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		}
	}
	
	public KeyStore keyStore(){
		if (ks == null){
			try {
				ks = KeyStore.getInstance("PKCS12");
				ks.load(pkcs12(),senha().toCharArray());
			} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
				throw new RuntimeException(e);
			}
		}
		return ks;
	}
	
	private String alias(){
		if (alias == null)
			try {
				alias = (String) keyStore().aliases().nextElement();
			} catch (KeyStoreException e) {
				throw new RuntimeException(e);
			}
		return alias;
	}

	public Date validoAPartir() {
		return x509Certificate().getNotBefore();
	}

	public Object validoAte() {
		return x509Certificate().getNotAfter();
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Certificado(){}
	
}
