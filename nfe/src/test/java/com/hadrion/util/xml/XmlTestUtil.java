package com.hadrion.util.xml;

import java.util.List;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;

public class XmlTestUtil {

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
	
}
