package com.hadrion.nfe.port.adapters.xml.nf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.FormaPagamento;
import com.hadrion.nfe.dominio.modelo.nf.FormatoDanfe;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.Presenca;
import com.hadrion.nfe.dominio.modelo.nf.Processo;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

class Ide {
	
	private Uf  uf = null;
	private int codigoNumerico;
	private String naturezaOperacao = null;
	private FormaPagamento formaPagamento = null;
	private Modelo modelo = null;
	private Serie serie = null;
	private Long numero = null;
	private Date emissao = null, dataSaidaEntrada = null;
	private TipoOperacao tipoOperacao = null;
	private Municipio municipioFatoGerador = null;
	private LocalDestino localDestino = null;
	private FormatoDanfe formatoDante = null;
	private TipoEmissao tipoEmissao = null;
	private Finalidade finalidade = null;
	private boolean consumidorFinal = false;
	private Presenca presenca = null;
	private Processo processo = null;
	private Date dataHoraContingencia = null;
	private String justificativaContingencia = null;
	private List<Referencia> referencias = new ArrayList<Referencia>();
	
	public Ide(HierarchicalStreamReader reader,
			UnmarshallingContext context){
		
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			if ("cUF".equals(reader.getNodeName()))
				uf = (Uf) context.convertAnother(reader.getValue(), Uf.class);
			if ("cNF".equals(reader.getNodeName()))
				codigoNumerico = Integer.parseInt(reader.getValue());
			if ("natOp".equals(reader.getNodeName()))
				naturezaOperacao = (String) context.convertAnother(reader.getValue(), String.class);
			if ("indPag".equals(reader.getNodeName()))
				formaPagamento = (FormaPagamento) context.convertAnother(reader.getValue(), FormaPagamento.class);
			if ("mod".equals(reader.getNodeName()))
				modelo = (Modelo) context.convertAnother(reader.getValue(),Modelo.class);
			if ("serie".equals(reader.getNodeName()))
				serie = (Serie) context.convertAnother(reader.getValue(),Serie.class);
			if ("nNF".equals(reader.getNodeName()))
				numero = (Long) context.convertAnother(reader.getValue(), Long.class);
			if ("dhEmi".equals(reader.getNodeName()))
				emissao = (Date) context.convertAnother(reader.getValue(),Date.class);
			if ("dhSaiEnt".equals(reader.getNodeName()))
				dataSaidaEntrada = (Date) context.convertAnother(reader.getValue(),Date.class);
			if ("tpNF".equals(reader.getNodeName()))
				tipoOperacao = (TipoOperacao) context.convertAnother(reader.getValue(), TipoOperacao.class);
			if ("idDest".equals(reader.getNodeName()))
				localDestino = (LocalDestino) context.convertAnother(reader.getValue(), LocalDestino.class);
			if ("cMunFG".equals(reader.getNodeName()))
				municipioFatoGerador = new Municipio(Integer.parseInt(reader.getValue()), null, null);
			if ("tpImp".equals(reader.getNodeName()))
				formatoDante = (FormatoDanfe) context.convertAnother(reader.getValue(), FormatoDanfe.class);
			if ("tpEmis".equals(reader.getNodeName()))
				tipoEmissao = TipoEmissao.obterPeloCodigo(Integer.parseInt(reader.getValue()));
			if ("finNFe".equals(reader.getNodeName()))
				finalidade = (Finalidade) context.convertAnother(reader.getValue(), Finalidade.class);
			if ("indFinal".equals(reader.getNodeName()))
				consumidorFinal = reader.getValue() == "1";
			if ("indPres".equals(reader.getNodeName()))
				presenca = (Presenca) context.convertAnother(reader.getValue(), Presenca.class);
			if ("procEmi".equals(reader.getNodeName()))
				processo = (Processo) context.convertAnother(reader.getValue(), Processo.class);
			if ("dhCont".equals(reader.getNodeName()))
				dataHoraContingencia = (Date) context.convertAnother(reader.getValue(), Date.class);
			if ("xJust".equals(reader.getNodeName()))
				justificativaContingencia = reader.getValue();
			if ("NFref".equals(reader.getNodeName()))
				referencias.add((Referencia) context.convertAnother(reader.getValue(), Referencia.class));
			reader.moveUp();
		}
		
	}
	
	public List<Referencia> getReferencias(){
		return referencias;
	}
	
	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public int getCodigoNumerico() {
		return codigoNumerico;
	}

	public void setCodigoNumerico(int codigoNumerico) {
		this.codigoNumerico = codigoNumerico;
	}

	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Serie getSerie() {
		return serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Date getEmissao() {
		return emissao;
	}

	public void setEmissao(Date emissao) {
		this.emissao = emissao;
	}

	public Date getDataSaidaEntrada() {
		return dataSaidaEntrada;
	}

	public void setDataSaidaEntrada(Date dataSaidaEntrada) {
		this.dataSaidaEntrada = dataSaidaEntrada;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public Municipio getMunicipioFatoGerador() {
		return municipioFatoGerador;
	}

	public void setMunicipioFatoGerador(Municipio municipioFatoGerador) {
		this.municipioFatoGerador = municipioFatoGerador;
	}

	public LocalDestino getLocalDestino() {
		return localDestino;
	}

	public void setLocalDestino(LocalDestino localDestino) {
		this.localDestino = localDestino;
	}

	public FormatoDanfe getFormatoDante() {
		return formatoDante;
	}

	public void setFormatoDante(FormatoDanfe formatoDante) {
		this.formatoDante = formatoDante;
	}

	public TipoEmissao getTipoEmissao() {
		return tipoEmissao;
	}

	public void setTipoEmissao(TipoEmissao tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
	}

	public Finalidade getFinalidade() {
		return finalidade;
	}

	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}

	public boolean getConsumidorFinal() {
		return consumidorFinal;
	}

	public void setConsumidorFinal(boolean consumidorFinal) {
		this.consumidorFinal = consumidorFinal;
	}

	public Presenca getPresenca() {
		return presenca;
	}

	public void setPresenca(Presenca presenca) {
		this.presenca = presenca;
	}

	public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}

	public Date getDataHoraContingencia() {
		return dataHoraContingencia;
	}

	public void setDataHoraContingencia(Date dataHoraContingencia) {
		this.dataHoraContingencia = dataHoraContingencia;
	}

	public String getJustificativaContingencia() {
		return justificativaContingencia;
	}

	public void setJustificativaContingencia(String justificativaContingencia) {
		this.justificativaContingencia = justificativaContingencia;
	}
	
	
	
}
