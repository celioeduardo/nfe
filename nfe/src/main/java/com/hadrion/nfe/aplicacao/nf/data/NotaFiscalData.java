package com.hadrion.nfe.aplicacao.nf.data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Cpf;

public class NotaFiscalData {
	
	public static class EmitenteData {
		String nome;
		EnderecoData endereco;

		public EmitenteData(String nome,EnderecoData endereco) {
			super();
			this.nome=nome;
			this.endereco = endereco;
		}
	}
	
	public static class DestinatarioData {
		String nome;
		EnderecoData endereco;
		public DestinatarioData(String nome,EnderecoData endereco) {
			super();
			this.nome=nome;
			this.endereco = endereco;
		}
	}
	
	public static class EnderecoData {
		MunicipioData municipio;
		public EnderecoData(MunicipioData municipio) {
			this.municipio = municipio;
		}
	}
	
	public static class MunicipioData {
		int codigo;
		String nome;
		String uf;
		public MunicipioData(int codigo, String nome, Object uf) {
			super();
			this.codigo = codigo;
			this.nome = nome;
			this.uf = String.valueOf(uf);
		}
	}
	
	public static class TransportadorData {
		String nome;
		Long cpf;
		Long cnpj;
		public TransportadorData(String nome, Cpf cpf, Cnpj cnpj) {
			super();
			this.nome = nome;
			
			Optional.ofNullable(cpf)
				.map(Cpf::numero)
				.ifPresent(n -> this.cpf = n);
				
			Optional.ofNullable(cnpj)
				.map(Cnpj::numero)
				.ifPresent(n -> this.cnpj = n);
		}
	}
	
	public static class VeiculoData {
		PlacaData placa;
		public VeiculoData(PlacaData placa) {
			this.placa = placa;
		}
	}
	
	public static class PlacaData {
		String numero;
		String uf;
		public PlacaData(String numero, Uf uf) {
			this.numero = numero;
			this.uf = String.valueOf(uf);
		}
	}
	
	public static class VolumeData {
		Double pesoBruto;
		public VolumeData(Double pesoBruto) {
			this.pesoBruto = pesoBruto;
		}
	}
	
	String filialId;
	String notaFiscalId;
	Long numero;
	String serie;
	Date emissao;
	Double total;
	String publicoNome;
	String tipo;
	Long msgCodigo;
	String msgDescricao;
	String chave;
	String tipoEmissao;
	String tipoOperacao;
	Date dataHoraAutorizacao;
	String numeroProtocoloAutorizacao;
	Date dataHoraCancelamento;
	String numeroProtocoloCancelamento;
	Integer cceSequencia;
	String cceCorrecao;
	
	EmitenteData emitente;
	DestinatarioData destinatario;
	TransportadorData transportador;
	VeiculoData veiculo;
	List<VolumeData> volumes;
	
	public NotaFiscalData(){}
	
	public NotaFiscalData(String filialId, String notaFiscalId, Long numero, String serie,
			Date emissao, Double total,String publicoNome, String tipo, Long msgCodigo,
			String msgDescricao, String chave, String tipoEmissao, TipoOperacao tipoOperacao,
			Date dataHoraAutorizacao, String numeroProtocoloAutorizacao,
			Date dataHoraCancelamento, String numeroProtocoloCancelamento,
			Integer cceSequencia, String cceCorrecao, 
			EmitenteData emitente,
			DestinatarioData destinatario,
			TransportadorData transportador,
			VeiculoData veiculo,
			List<VolumeData> volumes ) {
		super();
		this.notaFiscalId = notaFiscalId;
		this.filialId = filialId;
		this.numero = numero;
		this.serie = serie;
		this.emissao = emissao;
		this.total = total;
		this.publicoNome = publicoNome;
		this.tipo = tipo;
		this.msgCodigo = msgCodigo;
		this.msgDescricao = msgDescricao;
		this.chave = chave;
		this.tipoEmissao = tipoEmissao;
		this.tipoOperacao = String.valueOf(tipoOperacao);
		this.dataHoraAutorizacao = dataHoraAutorizacao;
		this.numeroProtocoloAutorizacao = numeroProtocoloAutorizacao;
		this.dataHoraCancelamento = dataHoraCancelamento;
		this.numeroProtocoloCancelamento = numeroProtocoloCancelamento;
		this.cceSequencia = cceSequencia;
		this.cceCorrecao = cceCorrecao;
		this.emitente = emitente;
		this.destinatario = destinatario;
		this.transportador = transportador;
		this.veiculo = veiculo;
		this.volumes = volumes;
	}

	
}
