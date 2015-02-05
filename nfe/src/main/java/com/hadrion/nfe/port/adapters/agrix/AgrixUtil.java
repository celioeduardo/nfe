package com.hadrion.nfe.port.adapters.agrix;

import java.util.ArrayList;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class AgrixUtil {
	
	public static String schema() {
		return null;
	}
	
	public static List<String> notaFiscalIdToGuid(List<NotaFiscalId> ids){
		List<String> result = new ArrayList<String>();
		for (NotaFiscalId id : ids) {
			result.add(notaFiscalIdToGuid(id));
		}
		return result;
	}
	
	public static String notaFiscalIdToGuid(NotaFiscalId id){
		if (id == null || id.id() == null)
			return null;
		return notaFiscalIdToGuid(id.id());
	}

	public static String notaFiscalIdToGuid(String id) {
		if (id == null)
			return null;
		return  id.substring(2);
	}
	
	public static Ambiente ambientePelaNotaFiscalId(String notaFiscalId){
		if (notaFiscalId == null) return null;
		
		if (notaFiscalId.startsWith("H-"))
			return Ambiente.HOMOLOGACAO;
		else if (notaFiscalId.startsWith("P-"))
			return Ambiente.PRODUCAO;
		return null;
	}
	public static Ambiente ambientePelaNotaFiscalId(NotaFiscalId notaFiscalId){
		return ambientePelaNotaFiscalId(String.valueOf(notaFiscalId));
	}
	
}
