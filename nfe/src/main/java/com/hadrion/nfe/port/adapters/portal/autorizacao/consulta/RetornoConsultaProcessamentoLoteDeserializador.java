package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class RetornoConsultaProcessamentoLoteDeserializador extends AbstractConverter{
	
	private String xml;
	private XStream xstream;
	
	RetornoConsultaProcessamentoLoteDeserializador(String xml){
		this.xml = xml; 
	}

	RetornoConsultaProcessamentoLote deserializar() {
		return (RetornoConsultaProcessamentoLote) xstream().fromXML(xml);
	}
	
	private XStream xstream(){
		if (xstream == null){
			xstream = XStreamFabrica.criar();
		}
		
		xstream.registerConverter(this);
		xstream.alias("nfeRetAutorizacaoLoteResult", RetornoConsultaProcessamentoLote.class);
		return xstream;
	}
	

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Ambiente ambiente = null;
		int cStat = -1;
		String xMotivo = null;
		int cMsg = -1;
		String xMsg = null;
		
		List<ProtocoloNotaProcessada> protocolos = new LinkedList<ProtocoloNotaProcessada>();
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				if (reader.getNodeName().equals("tpAmb"))
					ambiente = (Ambiente) context.convertAnother(reader.getValue(), Ambiente.class);
				if (reader.getNodeName().equals("cStat"))
					cStat = Integer.parseInt(reader.getValue());
				if (reader.getNodeName().equals("xMotivo"))
					xMotivo = reader.getValue();
				if (reader.getNodeName().equals("cMsg"))
					cMsg = Integer.parseInt(reader.getValue());
				if (reader.getNodeName().equals("xMsg"))
					xMsg = reader.getValue();
				if (reader.getNodeName().equals("protNFe"))
					protocolos.addAll(protocolo(reader,context));
				reader.moveUp();
			}
			reader.moveUp();
		}
		
		return new RetornoConsultaProcessamentoLote(
				ambiente, 
				mensagem(cStat, xMotivo), 
				mensagemSefaz(cMsg, xMsg), 
				protocolos);
	}
	
	private List<ProtocoloNotaProcessada> protocolo(HierarchicalStreamReader reader,
			UnmarshallingContext context){
		
		List<ProtocoloNotaProcessada> protocolos = new LinkedList<ProtocoloNotaProcessada>();
		
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
			protocolos.add(new ProtocoloNotaProcessada(dataHoraProcessamento, numero, mensagem(cStat, xMotivo), chave));
			reader.moveUp();
		}
		
		return protocolos;
	}
	
	private Mensagem mensagem(int codigo, String descricao){
		if (codigo != -1 || descricao != null)
			return new Mensagem(codigo, descricao);
		return null;
	}
	private MensagemSefaz mensagemSefaz(int codigo, String descricao){
		if (codigo != -1 || descricao != null)
			return new MensagemSefaz(codigo, descricao);
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return RetornoConsultaProcessamentoLote.class.isAssignableFrom(type);
	}
	
}
