package com.hadrion.web;

import static com.hadrion.util.DataUtil.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;

@RestController
@RequestMapping("/notas_fiscais")
public class NfeController {
	
	@RequestMapping("/pendentes_autorizacao")
	public List<NotaFiscalData> pendentes_autorizacao(HttpServletRequest req){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		
		result.add(new NotaFiscalData(123456L, "1", data("01/01/2014"), 1564.87));
		result.add(new NotaFiscalData(6547L, "1", data("02/02/2014"), 45.67));
		result.add(new NotaFiscalData(1111L, "1", data("03/03/2014"), 99.11));
		result.add(new NotaFiscalData(123888L, "1", data("31/12/2014"), 654.88));
		
		return result;
	}

	
	@RequestMapping("/")
	public String index(HttpServletRequest req){
		//req.getRemoteUser();
		return "Uhuhuhuhuh - Spring Boot is ON id:"+req.getSession().getId();
	}
	
}
