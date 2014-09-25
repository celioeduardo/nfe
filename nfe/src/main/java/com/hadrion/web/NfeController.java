package com.hadrion.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 
@RestController
public class NfeController {
	@RequestMapping("/req")
	public String index(HttpServletRequest req){
		//req.getRemoteUser();
		return "Uhuhuhuhuh - Spring Boot is ON id:"+req.getSession().getId();
	}
	
}
