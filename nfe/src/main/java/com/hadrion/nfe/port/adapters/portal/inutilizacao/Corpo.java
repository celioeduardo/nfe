package com.hadrion.nfe.port.adapters.portal.inutilizacao;

import static com.hadrion.util.xml.XmlUtil.parseXml;

import org.w3c.dom.Document;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.util.xml.XmlUtil;
import com.thoughtworks.xstream.XStream;

public class Corpo {
	private Inutilizacao inutilizacao;
	private Uf uf;
	private Cnpj cnpj;
	private Certificado certificado;
	private XStream xstream;
	private InutilizacaoSerializador serializador;
	
	public Corpo(Inutilizacao inutilizacao, Uf uf, Cnpj cnpj, 
			Certificado certificado) {
		super();
		this.inutilizacao = inutilizacao;
		this.certificado = certificado;
		this.uf = uf;
		this.cnpj = cnpj;
		serializador =  new InutilizacaoSerializador(certificado());
	}

	public Document gerar() {
		Document doc = parseXml(xstream().toXML(this));
		
		Document inutilizacaoXml = parseXml(serializador.serializar(inutilizacao, uf, cnpj));
		doc.getDocumentElement().appendChild(doc.importNode(inutilizacaoXml.getFirstChild(), true));
		
//		doc.getDocumentElement().setAttribute("xmlns", uriPortal());
//		doc.getDocumentElement().setAttribute("versao", versaoLayout());
		
		return doc;
		
//		return parseXml(xstream().toXML(this));
//		Node enviLote = doc.getElementsByTagName("inutNFe").item(0);
//		
//		Document inutilizacaoXml = parseXml(serializador.serializar(inutilizacao, uf, cnpj));
//		enviLote.appendChild(doc.importNode(inutilizacaoXml.getFirstChild(), true));
//	
//		return doc;
	}
	
	public String gerarComoString(){
		return XmlUtil.xmlParaString (gerar());
	}
	
	protected Certificado certificado(){
		return certificado;
	}
	
	protected Ambiente ambiente(){
		return inutilizacao.ambiente();
	}
	
	private XStream xstream(){
		if (xstream == null){
			xstream = XStreamFabrica.criar();
		}
		
		xstream.registerConverter(new InutilizacaoConverter(uf,cnpj));
		xstream.registerConverter(new CorpoConverter());
		xstream.alias("nfeDadosMsg", Corpo.class);
		return xstream;
	}
	
	protected String versaoDados(){
		return "2.00";
	}
	
	protected String uriWebService(){
		return "http://www.portalfiscal.inf.br/nfe/wsdl/NfeInutilizacao2";
	}
	
	
}
