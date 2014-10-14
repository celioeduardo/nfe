package com.hadrion.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;

@RestController
@RequestMapping("/")
public class NfeController {
	
	@Autowired
	NotaFiscalAplicacaoService notaFiscalAplicacaoService; 
	
	@RequestMapping(value="/notas_fiscais/pendentes_autorizacao_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> pendentes_autorizacao_resumo(/*
			@RequestParam(value="empresa",required=false,defaultValue="")String _empresa,
			@RequestParam(value="filial",required=false,defaultValue="")String _filial,
			@RequestParam(value="inicio",required=false,defaultValue="")String _inicio,
			@RequestParam(value="fim",required=false,defaultValue="")String _fim,
			@RequestParam(value="usuario",required=false,defaultValue="")String _usuario,
			@RequestParam(value="notafiscalid",required=false,defaultValue="")String _notaFiscalId*/){
		
		Double empresa=null;Double filial=null;Date inicio=null;Date fim=null;String usuario=null;String notaFiscalId=null;
		
		//empresa = Double.parseDouble(_empresa);
		//filial = Double.parseDouble(_filial);

		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacaoResumo(empresa,filial,inicio,fim,usuario,notaFiscalId);
	}
	@RequestMapping("/notas_fiscais/pendentes_autorizacao")
	public List<NotaFiscalData> pendentes_autorizacao(HttpServletRequest req){
		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacao(); 
	}
	@RequestMapping("/notas_fiscais/configuracao")
	public Map<String, String> configuracao(HttpServletRequest req){
		HashMap<String, String> mapa= new HashMap<String, String>();		
		mapa.put("COD_EMPRESA" , "1");
		mapa.put("COD_CIDADE" , "621");
		mapa.put("NOM_EMPRESA" , "COOPERATIVA AGROPECUARIA DO ALTO PARANAIBA - COOPADAP");
		mapa.put("NOM_FANTASIA" , "COOPADAP");
		mapa.put("NOM_EMPR_RELAT" , "COOPERATIVA AGROPECUARIA DO ALTO PARANAIBA");
		return mapa; 
	}
	
	@RequestMapping("/barra")
	public String index(HttpServletRequest req){
		return "Uhuhuhuhuh - Spring Boot is ON id:"+req.getSession().getId();
	}
	@RequestMapping("/home")
	public String home(HttpServletRequest req){
		return "home";
	}
	
}
