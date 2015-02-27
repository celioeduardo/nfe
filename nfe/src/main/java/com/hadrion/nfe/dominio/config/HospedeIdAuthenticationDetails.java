package com.hadrion.nfe.dominio.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class HospedeIdAuthenticationDetails extends WebAuthenticationDetails{
	
	private static final long serialVersionUID = 1L;
	
	private String hospedeId;
	
	public HospedeIdAuthenticationDetails(HttpServletRequest request) {
		super(request);
		hospedeId = request.getParameter("hospedeId");
	}
	
	public String getHospedeId(){
		return hospedeId;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			HospedeIdAuthenticationDetails objetoTipado = (HospedeIdAuthenticationDetails) objeto;
			objetosIguais = this.getHospedeId().equals(objetoTipado.getHospedeId());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (135 * 11) 
				+ this.getHospedeId().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "HospedeIdAuthenticationDetails [hospedeId=" + getHospedeId()
				+ ",sessionId="+ getSessionId()
				+ ",remoteAddress="+ getRemoteAddress()
				+ "]";
	}
	
}
