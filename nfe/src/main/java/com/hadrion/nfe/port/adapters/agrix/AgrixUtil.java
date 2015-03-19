package com.hadrion.nfe.port.adapters.agrix;

import java.util.ArrayList;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

public class AgrixUtil {
	
	public static String schema(String filial) {
		if (filial==null)
			return null;
		
		if (filial.equals("1-14559347000183") || filial.equals("1-14559347000183") ||
			filial.equals("1-18460956000131") || filial.equals("1-4737717000171") ||
			filial.equals("1-4949198000105")  || filial.equals("1-12498527000177") ||
			filial.equals("1-14432562000119") || filial.equals("1-17587513000143")
			|| filial.equals("1-16832651000188"))
			return "COOPERCAM";
		
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
	
	public static String paraFilialId(int codigoFilial, long cnpjFilial){
		return codigoFilial + "-" + cnpjFilial;
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
