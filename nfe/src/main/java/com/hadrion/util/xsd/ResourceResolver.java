package com.hadrion.util.xsd;

import java.io.InputStream;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

public class ResourceResolver implements LSResourceResolver{
	
	private String path;
	
	ResourceResolver(String path){
		this.path = path;
	}
	
	@Override
	public LSInput resolveResource(String type, String namespaceURI,
			String publicId, String systemId, String baseURI) {
		   // note: in this sample, the XSD's are expected to be in the root of the classpath

		InputStream resourceAsStream = this.getClass().getClassLoader()
	            .getResourceAsStream(path+systemId);
	    
	    return new Input(publicId, path+systemId, resourceAsStream);
	}

}
