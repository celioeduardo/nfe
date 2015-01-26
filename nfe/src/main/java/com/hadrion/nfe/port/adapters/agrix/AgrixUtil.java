package com.hadrion.nfe.port.adapters.agrix;

import java.util.ArrayList;
import java.util.List;

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
		return id.id().substring(2);
	}
	
}
