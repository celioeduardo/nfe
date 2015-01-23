package com.hadrion.nfe.dominio.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations = "classpath:prop.properties")
public class PropFlat {
	
	private String nome;
	
	private List<String> valores = new ArrayList<String>();

	private Map<String,Server> servers = new HashMap<String, Server>();
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Map<String, Server> getServers() {
		return servers;
	}

	public String getNome() {
		return nome;
	}

	public List<String> getValores() {
		return valores;
	}
	
	public static class Server{
		String host;
		int port;
		
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public int getPort() {
			return port;
		}
	}
	
}
