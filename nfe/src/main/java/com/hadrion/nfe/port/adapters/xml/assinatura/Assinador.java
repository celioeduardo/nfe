package com.hadrion.nfe.port.adapters.xml.assinatura;

import static com.hadrion.util.xml.XmlUtil.parseXml;
import static com.hadrion.util.xml.XmlUtil.xmlParaString;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;

public class Assinador {
	private String tagParaAssinar;
	private String noPai;
	private Certificado certificado;
	
	public Assinador(String tagParaAssinar, String noPai, Certificado certificado) {
		this.tagParaAssinar = tagParaAssinar;
		this.noPai = noPai;
		this.certificado = certificado;
	}
	
	private Certificado certificado(){
		return certificado;
	}
	
	public void assinar(Document xml){
		try {
			assinarDocument(xml);
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
				| MarshalException | XMLSignatureException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void assinarDocument(Document xml) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, MarshalException, XMLSignatureException{
		xml.normalizeDocument();

		Node noPaiAssinatura = xml.getElementsByTagName(noPai).item(0);
		
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

		PrivateKey key = certificado().privateKey();
		X509Certificate cert = certificado().x509Certificate();
		
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
	}

	public static void assinarNfe(Document xml, Certificado certificado) {
		new Assinador("infNFe","NFe",certificado).assinar(xml);
	}
	
	public static String assinarNfe(String xml, Certificado certificado) {
		Document doc = parseXml(xml);
		assinarNfe(doc, certificado);
		return xmlParaString(doc);
		
	}

}
