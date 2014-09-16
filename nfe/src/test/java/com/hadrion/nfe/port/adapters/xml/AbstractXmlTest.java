package com.hadrion.nfe.port.adapters.xml;

import org.junit.Before;

import com.hadrion.util.xml.XmlTestUtil;
import com.thoughtworks.xstream.XStream;

public abstract class AbstractXmlTest {
	protected XStream xstream;
	
	@Before
	public void setUp() {
		xstream = XStreamFabrica.criar();
	}
	
	protected Object fromXML(String xml){
		return xstream.fromXML(xml);
	}
	
	protected String toXML(Object object){
		return xstream.toXML(object);
	}
	
	protected void printXML(Object object){
		System.out.println(toXML(object));
	}
	
	public static void assertXMLEquals(String expectedXML, String actualXML) {
		XmlTestUtil.assertXMLEquals(expectedXML, actualXML);
	}

}
