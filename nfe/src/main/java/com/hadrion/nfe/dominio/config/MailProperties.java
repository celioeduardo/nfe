package com.hadrion.nfe.dominio.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class MailProperties {
	
	public static class Server {
		String host;
	    int port;
	    String username;
		String password;
		String from;
		String starttls;
		String debug;
		String auth;
		String trust;
		
		public String getStarttls() {
			return starttls;
		}
		public void setStarttls(String starttls) {
			this.starttls = starttls;
		}
		public String getDebug() {
			return debug;
		}
		public void setDebug(String debug) {
			this.debug = debug;
		}
		public String getAuth() {
			return auth;
		}
		public void setAuth(String auth) {
			this.auth = auth;
		}
		public String getTrust() {
			return trust;
		}
		public void setTrust(String trust) {
			this.trust = trust;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}    
	    
	 }
	

	private String defaultEncoding = "UTF-8";

	private Map<String,Server> mail = new HashMap<String, Server>();
	
//	public MailProperties(){
//		properties.put("mail.debug", "true");
//		properties.put("mail.smtp.starttls.enable","true");
//		properties.put("mail.smtp.auth","true");
//		properties.put("mail.smtp.ssl.trust","smtp.gmail.com");		
//	}
	
	public Map<String, Server> getMail() {
		return mail;
	}

	public String getDefaultEncoding() {
		return this.defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public Server get(String chave) {
		if (!this.mail.containsKey(chave))
			return new Server();
		return this.mail.get(chave);
	}

}
