package com.hadrion.nfe.port.adapters.portal.inutilizacao;

import java.util.Date;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;
import com.hadrion.nfe.port.adapters.xml.AbstractConverter;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.util.DataUtil;
import com.hadrion.util.xml.XmlUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

class RetornoInutilizacaoDeserializador extends AbstractConverter{
	
	private Document xmlEnvio;
	private String xmlRetorno;
	private XStream xstream;

	RetornoInutilizacaoDeserializador(Document xmlEnvio,
			String xmlRetorno){
		this.xmlEnvio = xmlEnvio;
		this.xmlRetorno = xmlRetorno;
	}

	RetornoInutilizacao deserializar() {
		return (RetornoInutilizacao) xstream().fromXML(xmlRetorno);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		int cStat = -1;
		String xMotivo = null;
		String nProt = null;
		String dhRecbto = null;
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				while (reader.hasMoreChildren()) {
					reader.moveDown();
					if (reader.getNodeName().equals("cStat"))
						cStat = Integer.parseInt(reader.getValue());
					if (reader.getNodeName().equals("xMotivo"))
						xMotivo = reader.getValue();
					if (reader.getNodeName().equals("nProt"))
						nProt = reader.getValue();
					if (reader.getNodeName().equals("dhRecbto"))
						dhRecbto = reader.getValue();
					reader.moveUp();
				}
				reader.moveUp();
			}
			reader.moveUp();
		}
		
		NumeroProtocolo numeroProtocolo = (nProt != null ? new NumeroProtocolo(nProt) : null);
		Mensagem mensagem = (xMotivo != null ? new Mensagem(cStat,xMotivo) : null);
		Date dataHoraProcessamento = (dhRecbto != null ? parseDataHora(dhRecbto) : null);
		
		return new RetornoInutilizacao(
				numeroProtocolo, mensagem, dataHoraProcessamento, 
				XmlUtil.xmlParaString(xmlEnvio),xmlRetorno);
		
	}
	
	private Date parseDataHora(String data){
		return DataUtil.data(StringUtils.replace(data, "T", " "),"yy-MM-dd HH:mm:ss");
	}
	
	private XStream xstream(){
		if (xstream == null){
			xstream = XStreamFabrica.criar();
		}
		
		xstream.registerConverter(this);
		xstream.alias("nfeInutilizacaoNF2Result", RetornoInutilizacao.class);
		return xstream;
	}
	
	@Override
	public boolean canConvert(@SuppressWarnings("rawtypes") Class type) {
		return RetornoInutilizacao.class.isAssignableFrom(type);
	}
	
	
}
