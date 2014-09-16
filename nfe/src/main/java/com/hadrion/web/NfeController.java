package com.hadrion.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NfeController {

	@RequestMapping("/")
	public String index(){
		return "Uhuhuhuhuh - Spring Boot is ON";
	}
	@RequestMapping("/teste")
	public String index2(){
		return "/public";
	}
	
}
