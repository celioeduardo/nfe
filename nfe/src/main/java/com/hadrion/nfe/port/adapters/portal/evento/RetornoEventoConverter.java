package com.hadrion.nfe.port.adapters.portal.evento;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoEvento;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.util.DataUtil;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class RetornoEventoConverter extends AbstractConverter{
	
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		NumeroProtocolo numeroProtocolo = null;
		Date dataHoraRegistroEvento = null;
		ChaveAcesso chaveAcesso = null;
		int codigoTipoEvento = -1;
		String descricaoTipoEvento = null; 
		int cStat = -1;
		String xMotivo = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if (reader.getNodeName().equals("chNFe"))
				chaveAcesso = new ChaveAcesso(reader.getValue());
			if (reader.getNodeName().equals("tpEvento"))
				codigoTipoEvento = Integer.valueOf(reader.getValue());
			if (reader.getNodeName().equals("xEvento"))
				descricaoTipoEvento = reader.getValue();
			if (reader.getNodeName().equals("cStat"))
				cStat = Integer.parseInt(reader.getValue());
			if (reader.getNodeName().equals("xMotivo"))
				xMotivo = reader.getValue();
			if (reader.getNodeName().equals("dhRegEvento"))
				dataHoraRegistroEvento = DataUtil.parseComTimezone(reader.getValue());
			if (reader.getNodeName().equals("nProt"))
				numeroProtocolo = new NumeroProtocolo(reader.getValue());
			reader.moveUp();
		}
		
		Mensagem mensagem = xMotivo != null ? new Mensagem(cStat, xMotivo) : null;
		
		return new RetornoEvento(chaveAcesso,codigoTipoEvento,descricaoTipoEvento,
				mensagem, dataHoraRegistroEvento,numeroProtocolo);
	}

	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return RetornoEvento.class.isAssignableFrom(type);
	}
	
	
}
