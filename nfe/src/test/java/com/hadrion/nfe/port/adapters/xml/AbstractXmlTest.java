package com.hadrion.nfe.port.adapters.xml;

import java.io.StringWriter;
import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.junit.Before;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;

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
	
	public static void assertXMLEquals(String expectedXML, String actualXML) {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff;
		try {
			diff = new DetailedDiff(XMLUnit.compareXML(expectedXML, actualXML));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

        List<?> allDifferences = diff.getAllDifferences();
        Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());
    }

	protected String xmlCompacto(XStream x, Object o) {
		StringWriter sw = new StringWriter();
		x.marshal(o,  new CompactWriter(sw));
		return sw.toString();
	}
}
