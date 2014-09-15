package com.hadrion.nfe.port.adapters.xml.assinatura;

import static com.hadrion.util.xml.XmlUtil.paraString;
import static com.hadrion.util.xml.XmlUtil.paraXml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.KeyStore;
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
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AssinaturaTest {
	
	private InputStream certificado;
	private String senhaCertificado;
	
	private Document xml;
	
	@Before
	public void setUp(){
		certificado = carregarCertificado();
		senhaCertificado = "12345678";
		xml = carregarXml();
	}
	
	@Test
	public void assinarXml() throws Exception{
		String tagParaAssinar = "infNFe";
		Node noPaiAssinatura = xml.getDocumentElement(); 
		xml.normalizeDocument();
		
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
		
		List<Transform> transformList = new ArrayList<Transform>();
		
	    TransformParameterSpec tps = null;  
	    Transform envelopedTransform = fac.newTransform(Transform.ENVELOPED,tps);  
	    Transform c14NTransform = fac.newTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", tps);  

	    transformList.add(envelopedTransform);  
	    transformList.add(c14NTransform);  

	    NodeList elements = xml.getElementsByTagName(tagParaAssinar);  
	    Element el = (Element) elements.item(0);  
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

	    KeyStore ks = KeyStore.getInstance("PKCS12");
		ks.load(certificado,senhaCertificado.toCharArray());
		String alias = (String)ks.aliases().nextElement();
		PrivateKey key = (PrivateKey)ks.getKey(alias, senhaCertificado.toCharArray());
		X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
		
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
		
		System.out.println(paraString(xml));
	}
	
	private InputStream carregarCertificado(){
		final File arquivoCertificado =FileUtils.getFile("src","test","resources","assinatura","certificado.pfx");
		
		try {
			return FileUtils.openInputStream(arquivoCertificado); 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Document carregarXml(){
		final File arquivoXml = FileUtils.getFile("src","test","resources","assinatura","nfe.xml");
		
		String xml; 
		try {
			xml = FileUtils.readFileToString(arquivoXml,Charset.forName("UTF-8")); 
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return paraXml(xml);
	}
	
}
