package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hadrion.nfe.dominio.modelo.hospede.Hospede;
import com.hadrion.nfe.dominio.modelo.notista.Notista;

public interface NotistaRepositorioSpringData extends JpaRepository<Notista,Long>{

	Notista findByHospedeAndIdentificacao(Hospede hospede, String identificacao);

}
