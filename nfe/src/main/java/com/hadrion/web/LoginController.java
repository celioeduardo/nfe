package com.hadrion.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView mav = new ModelAndView("login");
        mav.addObject("titulo", "Login");
        return mav;
	}
	
	@RequestMapping(value="/usuario_logado", method = RequestMethod.GET)
	public String usuarioLogado(HttpServletRequest req){
		return req.getRemoteUser();
	}
	
//	@RequestMapping(value={"/","index.html"}, method = RequestMethod.GET)
//	public ModelAndView index(HttpServletRequest req){
//		ModelAndView mav = new ModelAndView("index");
//		mav.addObject("username", req.getRemoteUser());
//        return mav;
//	}
}
