package com.hadrion.util.xml;

import java.io.IOException;
import java.io.StringReader;
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

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

public class XmlUtil {
	
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
	
	public static Document parseXml(String xml){
		InputSource is =  new InputSource();
		is.setCharacterStream(new StringReader(xml));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		
		Document doc;
		try {
			doc = builder.parse(is);
		} catch (SAXException | IOException e) {
			throw new RuntimeException(e);
		}

		return doc;
	} 
	
	
	public static String xmlCompacto(XStream x, Object o) {
		StringWriter sw = new StringWriter();
		x.marshal(o,  new CompactWriter(sw));
		return sw.toString();
	}
	
}
