package com.hadrion.nfe.dominio.modelo.certificado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;

@Service
public class CertificadoService {

	@Autowired
	private FilialRepositorio filialRepositorio;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	public Certificado obterCertificadoPelaFilial(FilialId filialId){
		
		if (filialId == null)
			throw new RuntimeException("É obrigatório informar FilialId");
		
		Filial filial = filialRepositorio.obterFilial(filialId);
		
		if (filial == null)
			throw new RuntimeException("Filial não encontrada. "+filialId);
		
		return obterCertificadoPelaEmpresa(filial.empresaId());
		
	}
	public Certificado obterCertificadoPelaEmpresa(EmpresaId empresaId){
		
		if (empresaId == null)
			throw new RuntimeException("É obrigatório informar EmpresaId");
		
		Empresa empresa = empresaRepositorio.obterEmpresa(empresaId);
		
		if (empresa == null)
			throw new RuntimeException("Empresa não encontrada. "+empresaId);
		
		return empresa.certificado();
	}
	
}
