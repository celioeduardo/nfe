package com.hadrion.nfe.port.adapters.ws;

import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

public class WebServiceTemplateFabrica {
	
	@Autowired
	SoapMessageFactory messageFactory;
	
	public WebServiceTemplate criar(KeyStore pkcs12,String senha){
		WebServiceTemplate template = new WebServiceTemplate(messageFactory());
		template.setMessageSender(webServiceMessageSender(pkcs12,senha));
		return template;
	}
	
	private SoapMessageFactory messageFactory(){
		return messageFactory;
	}
	
	private WebServiceMessageSender webServiceMessageSender(KeyStore pkcs12, String senha){
		HttpComponentsMessageSender messageSender = 
				new HttpComponentsMessageSender(httpClient(pkcs12,senha));
		return messageSender;
	}
	
	private HttpClient httpClient(KeyStore pkcs12, String senha){
		SSLContext sslcontext;
		try {
			sslcontext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustStrategy() {
						@Override
						public boolean isTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
							return true;
						}
					})
			        .loadKeyMaterial(pkcs12,senha.toCharArray())
			        .build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        
        return HttpClients.custom()
        		.addInterceptorFirst(new ContentLengthHeaderRemover())
                .setSSLSocketFactory(sslsf)
                .build();
	}
	
	private static class ContentLengthHeaderRemover implements HttpRequestInterceptor{

        @Override
        public void process(HttpRequest request, HttpContext context) 
                throws HttpException, IOException {

            // fighting org.apache.http.protocol.RequestContent's 
            // ProtocolException("Content-Length header already present");
            request.removeHeaders(HTTP.CONTENT_LEN);
        }
    }
	
}
