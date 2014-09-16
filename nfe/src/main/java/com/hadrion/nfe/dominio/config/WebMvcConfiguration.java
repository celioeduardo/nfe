package com.hadrion.nfe.dominio.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfiguration
{
	
	/*private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
        "classpath:/META-INF/resources/", "classpath:/resources/",
        "classpath:/static/", "classpath:/public/" };
    @Bean
    public WebMvcConfigurerAdapter defaultWebMvcConfiguration()
    {
        return new favoritoWebMvcConfiguration();
    }

    public class favoritoWebMvcConfiguration extends WebMvcConfigurerAdapter
    {
    	@Override
    	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    		
    	    if (!registry.hasMappingForPattern("/**")) {
    	        registry.addResourceHandler("/public/").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    	    }
    	}
    }*/
}