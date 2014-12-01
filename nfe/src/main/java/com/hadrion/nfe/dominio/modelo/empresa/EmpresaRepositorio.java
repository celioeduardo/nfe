package com.hadrion.nfe.dominio.modelo.empresa;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;

public interface EmpresaRepositorio {

	Certificado obterCertificadoPelaEmpresa(EmpresaId empresaId);

	void salvar(Empresa empresa);

}
