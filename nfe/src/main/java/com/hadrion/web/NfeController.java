package com.hadrion.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

 
@RestController
public class NfeController {
	@RequestMapping("/req")
	public String index(HttpServletRequest req){
		//req.getRemoteUser();
		return "Uhuhuhuhuh - Spring Boot is ON";
	}
	
}
