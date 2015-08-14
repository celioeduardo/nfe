package com.hadrion.nfe.aplicacao.nf;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.DestinatarioData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.EmitenteData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.EnderecoData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.MunicipioData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.PlacaData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.TransportadorData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.VeiculoData;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData.VolumeData;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Placa;
import com.hadrion.nfe.dominio.modelo.nf.transporte.Veiculo;

public class MontadorNotaFiscal {

	private NotaFiscal nf;
	
	public MontadorNotaFiscal(NotaFiscal nf) {
		this.nf = nf;
	}
	
	public NotaFiscalData construir() {
		Double totalNota;
		if (nf.total().igualAZero() && nf.totalIcms().maiorQueZero())
			totalNota = nf.totalIcms().valor();
		else
			totalNota=nf.total().valor();
		
		NotaFiscalData data = new NotaFiscalData(
				nf.notaFiscalId().id(),
				nf.numero(),
				String.valueOf(nf.serie()),
				nf.emissao(),
				totalNota,
				nf.destinatario().razaoSocial(),
				nf.tipoOperacao().toString(),
				nf.mensagem() != null ? new Long(nf.mensagem().codigo()) : null,
				nf.mensagem() != null ? nf.mensagem().descricao() : null,
				nf.chaveAcesso() != null ? String.valueOf(nf.chaveAcesso()): null,
				String.valueOf(nf.tipoEmissao()),
				nf.tipoOperacao(),
				nf.dataHoraAutorizacao(),
				String.valueOf(nf.numeroProtocoloAutorizacao()),
				nf.dataHoraCancelamento(),
				String.valueOf(nf.numeroProtocoloCancelamento()),
				nf.cartaCorrecaoAtual() != null ? nf.cartaCorrecaoAtual().sequencia() : null,
				nf.cartaCorrecaoAtual() != null ? nf.cartaCorrecaoAtual().correcao() : null,
				construirEmitente(),
				construirDestinatario(),
				construirTransportador(),
				construirVeiculo(),
				construirVolumes());
				
		return data;
	}

	private EmitenteData construirEmitente(){
		return new EmitenteData(
				construirEndereco(nf.emitente().endereco()));
	}
	
	private EnderecoData construirEndereco(Endereco endereco){
		return Optional.ofNullable(endereco)
				.map(e -> new EnderecoData(construirMunicipio(e.municipio())))
				.orElse(null);
	}
	
	private DestinatarioData construirDestinatario(){
		return new DestinatarioData(
				construirEndereco(nf.destinatario().endereco()));
	}
	
	private TransportadorData construirTransportador(){
		return nf().transportador()
				.map(t -> new TransportadorData(t.razaoSocial(), t.cpf(), t.cnpj()))
				.orElse(null);
	}
	private VeiculoData construirVeiculo(){
		return nf().veiculo()
				.map(v -> new VeiculoData(construirPlacaData()))
				.orElse(null);
	}
	
	private PlacaData construirPlacaData(){
		return placa()
				.map(p -> new PlacaData(p.numero(), p.uf()))
				.orElse(null);
	}
	
	private Optional<Placa> placa(){
		return nf().veiculo()
				.map(Veiculo::placa);
	}
	
	private List<VolumeData> construirVolumes(){
		return nf.volumes().stream()
			.map(v -> new VolumeData(v.pesoBruto()))
			.collect(Collectors.toList());
	}
	
	private MunicipioData construirMunicipio(Municipio municipio){
		return Optional.ofNullable(municipio)
			.map(m -> new MunicipioData(m.codigo(), m.nome(), m.uf()))
			.orElse(null);
	}
	
	private NotaFiscal nf(){
		return nf;
	}
	
	
}
