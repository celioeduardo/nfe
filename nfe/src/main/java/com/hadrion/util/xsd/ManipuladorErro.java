package com.hadrion.util.xsd;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class ManipuladorErro implements ErrorHandler {
	
	static class Par{
		String chave;
		String valor;
		
		protected Par(String chave, String valor) {
			this.chave = chave;
			this.valor = valor;
		}
		
		@Override
		public String toString() {
			return chave+"="+valor;
		}
		
	}
	
	private List<Par> inconsistencias;
	private static final String ALERTA = "ALERTA";
	private static final String ERRO = "ERRO";
	private static final String ERRO_FATAL = "ERRO_FATAL";
	
	String errosComoTexto(){
		StringBuffer str = new StringBuffer();
		for (String msg : erros()) 
			str.append(msg+"\n");
		return str.toString();
	}
	
	List<String> erros(){
		List<String> result = new ArrayList<String>();
		for (Par msg : getInconsistencias()) {
			if (ERRO.equals(msg.chave) || ERRO_FATAL.equals(msg.chave))
				result.add(msg.valor);
		}	
		return result;
	}


	String inconsistenciasComoTexto(){
		StringBuffer str = new StringBuffer();
		for (Par msg : getInconsistencias()) {
			str.append(msg.valor+"\n");
		}
		return str.toString();
	}
	
	private List<Par> getInconsistencias(){
		if (inconsistencias == null)
			inconsistencias = new ArrayList<Par>();
		return inconsistencias;
	}
	public void warning(SAXParseException e) throws SAXException {
    	getInconsistencias().add(new Par(ALERTA, e.getMessage()));
    }

    public void error(SAXParseException e) throws SAXException {
    	getInconsistencias().add(new Par(ERRO, e.getMessage()));
    }

    public void fatalError(SAXParseException e) throws SAXException {
    	getInconsistencias().add(new Par(ERRO_FATAL, e.getMessage()));
    }

	public int quantidadeErros() {
		return erros().size();
	}

	public String erro(int i) {
		return erros().get(i);
	}

	void registrarErro(String mensagem) {
		getInconsistencias().add(new Par(ERRO, mensagem));
		
	}

}
