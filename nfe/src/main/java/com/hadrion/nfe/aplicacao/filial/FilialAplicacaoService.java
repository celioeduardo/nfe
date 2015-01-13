package com.hadrion.nfe.aplicacao.filial;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.filial.data.FilialData;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.Contingencia;

@Service
@Transactional
public class FilialAplicacaoService {
	
	@Autowired
	private FilialRepositorio filialRepositorio;
	
	public List<FilialData> obterTodas(){
		List<FilialData> result = new ArrayList<FilialData>();
		
		for (Filial filial : filialRepositorio.obterTodas()) {
			result.add(construir(filial));
		}
		
		return result;
	}
	
	public FilialData obterPeloId(String filialId){
		return construir(filialRepositorio.obterFilial(new FilialId(filialId)));
	}
	
	private FilialData construir(Filial filial){
		return new FilialData(
				String.valueOf(filial.filialId()),
				filial.nome(),
				String.valueOf(filial.cnpj()),
				String.valueOf(filial.empresaId()),
				filial.modoOperacao(),
				filial.ambiente());
	}

	public List<FilialData> filiaisDaEmpresa(String id) {
		
		List<FilialData> result = new ArrayList<FilialData>();
		
		for (Filial filial : filialRepositorio.filiaisDaEmpresa(new EmpresaId(id))) {
			result.add(construir(filial));
		}
		
		return result;
	}
	
	public void alterarModoOperacao(AlterarModoOperacaoComando comando){
		Filial filial = filialRepositorio.obterFilial(new FilialId(comando.getFilialId()));
		switch (comando.getModoOperacao()) {
		case NORMAL:
			filial.operarEmModoNormal();
			break;
		case FS_DA: 
			filial.operarEmFsDa(new Contingencia(comando.getDataHoraContingencia(), comando.getJustificativaContingencia()));
			break;
		case SVC: 
			filial.operarEmSvc(new Contingencia(comando.getDataHoraContingencia(), comando.getJustificativaContingencia()));
			break;
		default:
			throw new RuntimeException("Modo de Operação [" + comando.getModoOperacao() + "] não tratado");
		}
	}
	
	
	
}
