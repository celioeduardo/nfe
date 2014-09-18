package com.hadrion.nfe.port.adapters.httpclient;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ClientCustomSSLTest {
	
	protected KeyStore pkcs12() throws Exception{
		KeyStore trustStore = KeyStore.getInstance("PKCS12");
		File file = FileUtils.getFile("src","test","resources","assinatura","certificado.pfx");
        FileInputStream instream = new FileInputStream(file);
        try {
            trustStore.load(instream, "12345678".toCharArray());
        } finally {
            instream.close();
        }
        return trustStore;
	}
	
	protected KeyStore keystore() throws Exception{
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		File file = FileUtils.getFile("src","test","resources","ws","keystore");
        FileInputStream instream = new FileInputStream(file);
        try {
            trustStore.load(instream, "javanfe".toCharArray());
        } finally {
            instream.close();
        }
        return trustStore;
	}
	/**
	 * Teste Ignorado. Usado somente para explorar o HTTP Client
	 */
	public void executarChamada() throws Exception{
		//KeyStore trustStore = keystore();

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
        		//.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
        		.loadTrustMaterial(null, new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
						return true;
					}
				})
                .loadKeyMaterial(pkcs12(),"12345678".toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {

            HttpGet httpget = new HttpGet("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRecepcao2");
            
            System.out.println("executing request" + httpget.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
	}
	
	
}
