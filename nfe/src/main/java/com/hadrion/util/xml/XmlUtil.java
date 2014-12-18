package com.hadrion.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class XmlUtil {
	public static Document novoDocument(){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		
		return builder.newDocument();
	}
	
	public static boolean xmlEquals(String arg1, String arg2){
		XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff;
		try {
			diff = new DetailedDiff(XMLUnit.compareXML(arg1, arg2));
		} catch (Exception e) {
			return false;
		}

        return diff.getAllDifferences().size() == 0;
	}
	
	public static String xmlParaString(Node doc){
		DOMSource xmlSource = new DOMSource(doc);
		
		StringWriter sw = new StringWriter();
		Result result = new StreamResult(sw);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		}
		
		try {
			transformer.transform(xmlSource, result);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		return sw.toString();
	}
	
	public static InputStream xmlParaInpuStream(Node doc){
		DOMSource xmlSource = new DOMSource(doc);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Result result = new StreamResult(outputStream);
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		}
		
		try {
			transformer.transform(xmlSource, result);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
		return new ByteArrayInputStream(outputStream.toByteArray());
	}
	
	public static Document parseXml(String xml){
		
		//InputSource is =  new InputSource(new StringReader(xml));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		}
		
		try {
			doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		} catch (SAXException | IOException e) {
		}
		
		return doc;
		
//		InputSource is =  new InputSource();
//		is.setCharacterStream(new StringReader(xml));
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder;
//		
//		try {
//			builder = dbf.newDocumentBuilder();
//		} catch (ParserConfigurationException e) {
//			throw new RuntimeException(e);
//		}
//		
//		Document doc;
//		try {
//			doc = builder.parse(is);
//		} catch (SAXException | IOException e) {
//			throw new RuntimeException(e);
//		}
//
//		return doc;
	} 
	
	
	public static String xmlCompacto(XStream x, Object o) {
		StringWriter sw = new StringWriter();
		x.marshal(o,  new CompactWriter(sw));
		return sw.toString();
	}
	
}
