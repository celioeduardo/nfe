package com.hadrion.nfe.port.adapters.xml.assinatura;

import static com.hadrion.util.xml.XmlTestUtil.assertXMLEquals;
import static com.hadrion.util.xml.XmlUtil.parseXml;
import static com.hadrion.util.xml.XmlUtil.xmlParaString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;

public class AssinaturaTest {
	private Certificado certificado;
	
	private Document xml;
	
	@Before
	public void setUp() throws Exception{
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678");
		xml = carregarXml();
	}
	
	@Test
	public void assinador() throws Exception{
		Assinador.assinarNfe(xml,certificado);
		assertXMLEquals(xmlParaString(
				carregarXmlAssinado()), 
				limparInformacao(xmlParaString(xml)));
	}
	
	@Test
	public void assinarXml() throws Exception{
		String tagParaAssinar = "infNFe";

		xml.normalizeDocument();
		
		Node noPaiAssinatura = xml.getElementsByTagName("NFe").item(0);
		
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		
		List<Transform> transformList = new ArrayList<Transform>();
		
		TransformParameterSpec tps = null;  
		Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,tps);  
		Transform c14NTransform = fac.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);  
		
		transformList.add(envelopedTransform);  
		transformList.add(c14NTransform);  
		
		NodeList elements = xml.getElementsByTagName(tagParaAssinar);  
		Element el = (Element) elements.item(0);
		el.setIdAttribute("Id", true);
		String id = el.getAttribute("Id");
		
		Reference ref = fac.newReference
				("#"+id, fac.newDigestMethod(DigestMethod.SHA1, null),
						transformList,
						null,null);
		
		SignedInfo si = fac.newSignedInfo
				(fac.newCanonicalizationMethod
						(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null), 
						fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), 
						Collections.singletonList(ref));
		
		PrivateKey key = certificado.privateKey();
		X509Certificate cert = certificado.x509Certificate();
		
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List<Object> x509Content = new ArrayList<Object>();
		x509Content.add(cert);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
		
		DOMSignContext dsc = null;
		if (noPaiAssinatura != null) 
			dsc = new DOMSignContext(key,noPaiAssinatura);
		else
			dsc = new DOMSignContext(key,xml.getDocumentElement());
		XMLSignature signature = fac.newXMLSignature(si, ki);
		signature.sign(dsc);
		
		String atual = limparInformacao(xmlParaString(xml));
		
		assertXMLEquals(
				xmlParaString(carregarXmlAssinado()), 
				atual);
	}
	
	@Test
	public void validarAssinatura() throws Exception{
		X509Certificate cert = certificado.x509Certificate();
		
		xml = carregarXmlAssinado();
		
		Element infNfe = (Element)xml.getElementsByTagName("infNFe").item(0);
		infNfe.setIdAttribute("Id", true);
		
		NodeList nl=xml.getElementsByTagNameNS(XMLSignature.XMLNS,"Signature");
		
		DOMValidateContext valContext=new DOMValidateContext(cert.getPublicKey(),nl.item(0));
		XMLSignatureFactory factory=XMLSignatureFactory.getInstance("DOM");
		XMLSignature s=factory.unmarshalXMLSignature(valContext);
		assertTrue(s.validate(valContext));

		@SuppressWarnings("unchecked")
		List<Reference> references= s.getSignedInfo().getReferences();
		assertEquals(1, references.size());

		Reference ref = references.get(0);
		assertEquals(
				"Problema ao validar assinatura pela Referência: [Ref id=" + ref.getId() + ":uri="+ ref.getURI()+ "] validity status:"+ ref.validate(valContext),
				"#NFe31141086675642000106550020002048531000000013",ref.getURI());
		assertTrue(ref.validate(valContext));
		
	}
	
	private Document carregarXml() throws Exception{

		final File arquivoXml = FileUtils.getFile("src","test","resources","assinatura","nfe.xml");
		
		String xml; 
		try {
			xml = FileUtils.readFileToString(arquivoXml,Charset.forName("UTF-8")); 
			//xml = FileUtils.readFileToString(arquivoXml); 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return parseXml(xml);
	}
	
	private Document carregarXmlAssinado(){
		final File arquivoXml = FileUtils.getFile("src","test","resources","assinatura","nfe-assinada.xml");
		
		String xml; 
		try {
			xml = FileUtils.readFileToString(arquivoXml,Charset.forName("UTF-8"));
			//xml = FileUtils.readFileToString(arquivoXml);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return parseXml(xml);
	}
	
	private String limparInformacao(String texto){
		String result = StringUtils.replace(StringUtils.replace(texto,"\n",""),"\r","");
		return result;
	}
}
