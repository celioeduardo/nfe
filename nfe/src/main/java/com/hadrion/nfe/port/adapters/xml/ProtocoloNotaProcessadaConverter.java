package com.hadrion.nfe.port.adapters.xml;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ProtocoloNotaProcessada;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ProtocoloNotaProcessadaConverter extends AbstractConverter{
	
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			Date dataHoraProcessamento = null;
			NumeroProtocolo numero = null;
			int cStat = -1;
			String xMotivo = null;
			ChaveAcesso chave = null;
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if (reader.getNodeName().equals("chNFe"))
					chave = new ChaveAcesso(reader.getValue());
				if (reader.getNodeName().equals("dhRecbto"))
					dataHoraProcessamento = (Date) context.convertAnother(reader.getValue(), Date.class);
				if (reader.getNodeName().equals("nProt"))
					numero = new NumeroProtocolo(reader.getValue());
				if (reader.getNodeName().equals("cStat"))
					cStat = Integer.parseInt(reader.getValue());
				if (reader.getNodeName().equals("xMotivo"))
					xMotivo = reader.getValue();
				reader.moveUp();
			}
			
			return new ProtocoloNotaProcessada(
				dataHoraProcessamento, 
				numero, 
				mensagem(cStat, xMotivo), 
				chave);
			
			//reader.moveUp();
		}
		
		return null;
	}
	
	private Mensagem mensagem(int codigo, String descricao){
		if (codigo != -1 || descricao != null)
			return new Mensagem(codigo, descricao);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return ProtocoloNotaProcessada.class.isAssignableFrom(type);
	}
	
}
