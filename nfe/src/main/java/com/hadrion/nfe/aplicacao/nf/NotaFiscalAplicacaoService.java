package com.hadrion.nfe.aplicacao.nf;

import static com.hadrion.util.xml.XmlUtil.xmlParaInpuStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.transaction.Transactional;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hadrion.comum.paginacao.Pagina;
import com.hadrion.comum.paginacao.PaginaList;
import com.hadrion.comum.paginacao.Paginacao;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.config.MailProperties;
import com.hadrion.nfe.dominio.config.MailProperties.Server;
import com.hadrion.nfe.dominio.exception.EntidadeNaoEncontradoException;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.cancelamento.CancelarNotaService;
import com.hadrion.nfe.dominio.modelo.cancelamento.SolicitacaoCancelamento;
import com.hadrion.nfe.dominio.modelo.cce.RegistrarCartaCorrecaoService;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;
import com.hadrion.nfe.dominio.modelo.lote.EnviarLoteService;
import com.hadrion.nfe.dominio.modelo.lote.GeracaoLoteService;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.CartaCorrecao;
import com.hadrion.nfe.dominio.modelo.nf.Contingencia;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.ObterEmailService;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.cobranca.Duplicata;
import com.hadrion.nfe.dominio.modelo.nf.transporte.ModalidadeFrete;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.util.xml.XmlUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
@Transactional
public class NotaFiscalAplicacaoService {
	@Autowired
	private GeracaoLoteService geracaoLoteService;

	@Autowired
	private EnviarLoteService enviarLoteService;

	@Autowired
	private LoteRepositorio loteRepositorio;

	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;

	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private FilialRepositorio filialRepositorio;

	@Autowired
	private MailProperties mailProperties;
	
	@Autowired
	private ObterEmailService obterEmailService;
	
	@Autowired
	private CancelarNotaService cancelarNotaService;
	
	@Autowired
	private RegistrarCartaCorrecaoService registrarCartaCorrecaoService;
	
	private static final Logger logger=LoggerFactory.getLogger(NotaFiscalAplicacaoService.class);
	
	public List<NotaFiscalData> notasFicaisPendentesAutorizacaoResumo(
			Ambiente ambiente, Long empresa, String filial, Date inicio,
			Date fim, String notistaId, String notaFiscalId) {
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		NotaFiscalId notaFiscalIdFiltro = null;

		if (notaFiscalId != null)
			notaFiscalIdFiltro = new NotaFiscalId(notaFiscalId);

		for (DescritorNotaFiscal nf : notaFiscalRepositorio
				.notasPendentesAutorizacaoResumo(ambiente, empresa,
						new FilialId(filial), inicio, fim, notistaId,
						notaFiscalIdFiltro)) {
			result.add(new NotaFiscalData(
					filial,
					nf.notaFiscalId().id(), nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.emissao(), 
					nf.valor().valor(), 
					nf.publicoNome(), 
					nf.tipo(),
					nf.mensagem() != null ? new Long(nf.mensagem().codigo()) : null, 
					nf.mensagem() != null ? nf.mensagem().descricao() : null,
					nf.chave() != null ? String.valueOf(nf.chave()) : null,
					String.valueOf(nf.tipoEmissao()), 
					null,null,null,null,null,null,null,null,null,null,null,null));
		}

		return result;
	}

	public List<NotaFiscalData> notasFicaisPendentesAutorizacao(
			Ambiente ambiente, String filial) {
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();

		for (NotaFiscal nf : notaFiscalRepositorio.notasPendentesAutorizacao(
				new FilialId(filial), ambiente))
			result.add(construir(nf));

		return result;
	}

	public Pagina<NotaFiscalData> notasFicaisAutorizadasResumo(Ambiente ambiente,
			Long empresa, String filial, Date inicio, Date fim,
			String notistaId, String notaFiscalId,Long numero, Paginacao paginacao) {

		Pagina<NotaFiscal> notas = null;
		if (notistaId != null && !notistaId.isEmpty())
			notas = notaFiscalRepositorio.notasAutorizadas(
					new FilialId(filial), ambiente, new NotistaId(notistaId),paginacao);
		else
			if (numero==null)
				notas = notaFiscalRepositorio.notasAutorizadas(
						new FilialId(filial), ambiente,paginacao);
			else
				notas = notaFiscalRepositorio.notasAutorizadas(
						new FilialId(filial), ambiente, numero,paginacao);
		
		return new PaginaList<NotaFiscalData>(
				notas.stream()
					.map(nf -> construir(nf))
					.collect(Collectors.toList()), 
				notas.getTotalDePaginas(), 
				notas.getTotalDeElementos(), 
				notas.getNumeroDeElementos());

	}
	
	public Pagina<NotaFiscalData> notasFicaisCanceladasResumo(Ambiente ambiente,
			Long empresa, String filial, Date inicio, Date fim,
			String notistaId, String notaFiscalId, Paginacao paginacao) {
		
		Pagina<NotaFiscal> notas = null;
		if (notistaId != null && !notistaId.isEmpty())
			notas = notaFiscalRepositorio.notasCanceladas(
					new FilialId(filial), ambiente, new NotistaId(notistaId),paginacao);
		else
			notas = notaFiscalRepositorio.notasCanceladas(
					new FilialId(filial), ambiente,paginacao);
		
		return new PaginaList<NotaFiscalData>(
				notas.stream()
					.map(nf -> construir(nf))
					.collect(Collectors.toList()), 
				notas.getTotalDePaginas(), 
				notas.getTotalDeElementos(), 
				notas.getNumeroDeElementos());
	}

	public Pagina<NotaFiscalData> notasFicaisAutorizadasNaoImpressasResumo(
			Ambiente ambiente, Long empresa, String filial, Date inicio,
			Date fim, String notistaId, String notaFiscalId,Long numero,
			Paginacao paginacao) {

		Pagina<NotaFiscal> notas = null;

		if (notistaId != null && !notistaId.isEmpty())
			notas = notaFiscalRepositorio.notasAutorizadasNaoImpressas(
					new FilialId(filial), ambiente, new NotistaId(notistaId),paginacao);
		else
			if (numero==null)
				notas = notaFiscalRepositorio.notasAutorizadasNaoImpressas(
						new FilialId(filial), ambiente, paginacao);
			else
				notas = notaFiscalRepositorio.notasAutorizadasNaoImpressas(
						new FilialId(filial), ambiente, numero, paginacao);
		
		return new PaginaList<NotaFiscalData>(
				notas.stream()
					.map(nf -> construir(nf))
					.collect(Collectors.toList()), 
				notas.getTotalDePaginas(), 
				notas.getTotalDeElementos(), 
				notas.getNumeroDeElementos());
		
	}

	public ResponseEntity<InputStreamResource> imprimirDanfe(String notaFiscalId)
			throws IOException, JRException {
		NotaFiscal nf = notaFiscalRepositorio
				.notaFiscalPeloId(new NotaFiscalId(notaFiscalId));
		
		if (nf == null)
			nf = notaFiscalRepositorio.notaPendenteAutorizacao(new NotaFiscalId(notaFiscalId));
		
		if (nf == null)
			throw new RuntimeException("Nota Fiscal não encontrada.");
		
		nf.definirDanfeComoImpresso();
		notaFiscalRepositorio.salvar(nf);
		return obterDanfe(nf);
	}

	public ResponseEntity<InputStreamResource> preVisualizarDanfe(
			String notaFiscalId) throws IOException, JRException {
		NotaFiscal nf = notaFiscalRepositorio
				.notaPendenteAutorizacao(new NotaFiscalId(notaFiscalId));
		return obterDanfe(nf);
	}


	public String enviarNotas(EnviarNotasComando comando) {
		Ambiente ambiente = Ambiente.valueOf(comando.getAmbiente());
		
		Lote lote = null;
		
		logger.debug("entrando no método enviarNotas...");
		if (ambiente == Ambiente.PRODUCAO)
			lote = geracaoLoteService.gerarLoteEmProducao(notas(
					comando.getIds(), ambiente));
		else
			lote = geracaoLoteService.gerarLoteEmHomologacao(notas(
					comando.getIds(), ambiente));
		
		
		logger.debug("salvando lote...");
		loteRepositorio.salvar(lote);
		logger.debug("lote salvo.");
		
		logger.debug("enviando lote...");
		enviarLoteService.enviar(lote);
		logger.debug("lote enviado.");
		
		logger.debug("salvando lote depois do envio...");
		loteRepositorio.salvar(lote);
		logger.debug("lote salvo depois do envio.");
		
		logger.debug("saindo do método enviarNotas...");
		
		return String.valueOf(lote.loteId());

	}

	public void definirNotaComoRejeitada(DefinirNotaComoRejeitadaComando comando) {
		NotaFiscal nota = nota(comando.getNotaFiscalId());
		if (nota != null && !nota.estaAutorizada() && !nota.estaCancelada())
			nota.rejeitada(new Mensagem(comando.getMsgCodigo(), comando
					.getMsgDescricao()));
		notaFiscalRepositorio.salvar(nota);
	}

	public void definirNotaComoAutorizada(
			DefinirNotaComoAutorizadaComando comando) {
		NotaFiscal nota = nota(comando.getNotaFiscalId());
		if (nota != null && !nota.estaAutorizada() && !nota.estaCancelada())
			nota.autorizada(
					new NumeroProtocolo(comando.getNumeroProtocolo()),
					new Mensagem(comando.getMsgCodigo(), comando
							.getMsgDescricao()),
					comando.getDataHoraAutorizacao(),
					comando.getXmlProtocolo());
		notaFiscalRepositorio.salvar(nota);
	}

	public void atualizarModoOperacao(AtualizarModoOperacaoComando comando) {
		Filial filial = filialRepositorio.obterFilial(new FilialId(comando
				.getFilialId()));

		ModoOperacao modoOperacao = filial.modoOperacao();

		List<NotaFiscal> notas = notaFiscalRepositorio
				.notasPendentesAutorizacao(filial.filialId(), filial.ambiente());
		
		Contingencia contingencia = null;
		if (comando.getDataHoraContingencia() != null &&
				StringUtils.isNotEmpty(comando.getJustificativaContingencia()))
			contingencia = new Contingencia(comando.getDataHoraContingencia(), comando
					.getJustificativaContingencia());
		
		for (NotaFiscal nf : notas) {
			nf.alterarModoOperacao(modoOperacao,contingencia);
			notaFiscalRepositorio.salvar(nf);
		}
	}

	private NotaFiscal nota(String notaFiscalId) {
		return notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId(
				notaFiscalId));
	}

	private Set<NotaFiscal> notas(List<String> ids, Ambiente ambiente) {
		
		logger.debug("buscando {} nota(s)...",ids.size());
		
		List<NotaFiscalId> listaId = new ArrayList<NotaFiscalId>();		
		
		for (String notaFiscalId : ids)
			listaId.add(new NotaFiscalId(notaFiscalId));
		
		logger.debug("pesquisando no repositório {} ids ...",ids.size());
		HashSet<NotaFiscal> result = new HashSet<NotaFiscal>(
				notaFiscalRepositorio.notasPendentesAutorizacao(listaId,
						ambiente));
		logger.debug("pesquisa completada");
		
		logger.debug("busca completada");
		
		return result; 
	}

	private NotaFiscalData construir(NotaFiscal nf) {
		return new MontadorNotaFiscal(nf).construir();
	}
	
	private byte[] gerarDanfe(Document nfeProc,NotaFiscal nf) throws JRException{
		JasperReport jasperReport;JasperPrint jasperPrint;
		
    	Map<String,Object> parameters= new HashMap<String, Object>();
    	byte[] logo = empresaRepositorio.obterEmpresa(filialRepositorio.obterFilial(nf.filialId()).empresaId()).logo();
    	
    	parameters.put("REPORT_LOCALE", new Locale("pt","BR"));
    	
    	if (logo != null)
    		parameters.put("Logo", new ByteArrayInputStream(logo));
    	
    	if (nf.cobranca()!=null){
    		int i=1;
	    	for (Duplicata dup : nf.cobranca().duplicatas()) {
				parameters.put("FAT_NUMERO"+i,dup.numero());        
				parameters.put("FAT_VENCIMENTO"+i, dup.vencimento());        
				parameters.put("FAT_VALOR"+i, dup.valor().valor());        
	    		i++;
			}
		}
    	
    	InputStream reportStream = getClass().getClassLoader().getResourceAsStream("report/danfe.jasper");
    	jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
    	
    	
		JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlParaInpuStream(nfeProc), "/nfeProc/NFe/infNFe/det");
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,xmlDataSource);		
		return JasperExportManager.exportReportToPdf(jasperPrint);		
	}
	
	private Document gerarXml(NotaFiscal nf) throws JRException{
		
		Certificado certificado = empresaRepositorio.obterEmpresa(filialRepositorio.obterFilial(nf.filialId()).empresaId()).certificado();
		NotaFiscalSerializador serializador = new NotaFiscalSerializador(certificado);
		
		Document nfeProc = XmlUtil.novoDocument();
		nfeProc.normalizeDocument();
		Element root = nfeProc.createElementNS(
				"http://www.portalfiscal.inf.br/nfe", "nfeProc");
		root.setAttribute("versao", "3.10");
		nfeProc.appendChild(root);
		Document nfe = XmlUtil.parseXml(serializador.serializar(nf));
		Document prot = null;
		if (nf.xmlProtocolo() != null)
			prot = XmlUtil.parseXml(nf.xmlProtocolo());
		
		nfeProc.getDocumentElement().appendChild(
				nfeProc.importNode(nfe.getFirstChild(), true));
		if (prot != null)
			nfeProc.getDocumentElement().appendChild(
					nfeProc.importNode(prot.getFirstChild(), true));
		
		nfeProc.normalizeDocument();
		
		return nfeProc;
	}
	public Document xmlCce(String notaFiscalId){
		return gerarXmlCce(nota(notaFiscalId).cartaCorrecaoAtual());
	}
	private Document gerarXmlCce(CartaCorrecao cce){
		
		Document procEventoNFe = XmlUtil.novoDocument();
		procEventoNFe.normalizeDocument();
		Element root = procEventoNFe.createElementNS(
				"http://www.portalfiscal.inf.br/nfe", "procEventoNFe");
		procEventoNFe.appendChild(root);
		
		Element evento = procEventoNFe.createElement("evento");
		procEventoNFe.getDocumentElement().appendChild(evento);
		
		Document envio = XmlUtil.parseXml(cce.xmlEnvio());
		Document retorno = XmlUtil.parseXml(cce.xmlRetorno());
		
		evento.appendChild(
				procEventoNFe.importNode(envio.getFirstChild(), true));
		if (retorno != null){
			Element retEvento = procEventoNFe.createElement("retEvento");
			procEventoNFe.getDocumentElement().appendChild(retEvento);
			retEvento.appendChild(
					procEventoNFe.importNode(retorno.getFirstChild(), true));
		}
//		procEventoNFe.getDocumentElement().appendChild(
//				procEventoNFe.importNode(envio.getFirstChild(), true));
//		if (retorno != null)
//			procEventoNFe.getDocumentElement().appendChild(
//					procEventoNFe.importNode(retorno.getFirstChild(), true));
		
		procEventoNFe.normalizeDocument();
		
		return procEventoNFe;
	}
	
	private Server configuracaoEmail(FilialId filialId){
		Filial filial = filialRepositorio.obterFilial(filialId);
		
		if (StringUtils.isNotEmpty(filial.apelido()) && 
				mailProperties.getMail().containsKey(filial.apelido()))
			return mailProperties.get(filial.apelido().toLowerCase());
		
		Empresa empresa = empresaRepositorio.obterEmpresa(filial.empresaId());
		if (StringUtils.isNotEmpty(empresa.apelido()) && 
				mailProperties.getMail().containsKey(empresa.apelido().toLowerCase()))
			return mailProperties.get(empresa.apelido().toLowerCase());
		
		return null;
		
	}
	
	public void enviarEmailXmlEDanfe(NotaFiscal nf) throws IOException, MessagingException, JRException {
		
		SimpleMailMessage message = smm(nf);

		Server server = configuracaoEmail(nf.filialId());
		
		if (server == null)
			throw new RuntimeException("Configuração de e-mail não definida");
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		Properties properties = new Properties();
		if (server.getStarttls() != null)
			properties.put("mail.smtp.starttls.enable", server.getStarttls());
		
		if (server.getTrust() != null)
			properties.put("mail.smtp.ssl.trust", server.getTrust());
		
		mailSender.setJavaMailProperties(properties);
		mailSender.setUsername(server.getUsername());
		mailSender.setPassword(server.getPassword());
		mailSender.setHost(server.getHost());
		mailSender.setPort(server.getPort());		
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm, true);
		
		String filename = nf.chaveAcesso().toString();
		
		Document xml = gerarXml(nf);		
		byte[] pdf = gerarDanfe(xml,nf);
		
		helper.setFrom(message.getFrom());
		helper.setTo(message.getTo());
		helper.setSubject(message.getSubject());
		helper.setText(message.getText());		
		helper.addAttachment(filename + ".xml", new ByteArrayResource(IOUtils.toByteArray(XmlUtil.xmlParaInpuStream(xml))) , "application/xml");		
		helper.addAttachment(filename + ".pdf", new ByteArrayDataSource(pdf, "application/pdf"));
	
		mailSender.send(mm);
	}
	public String enviarEmail(EnviarEmailComando comando) throws IOException, MessagingException, JRException {
		NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId(comando.getIds().get(0)));
		
		if (nf != null)
			enviarEmailXmlEDanfe(nf);
			
		return String.valueOf("");

	}
	SimpleMailMessage smm(NotaFiscal nf){
		
		Server server = configuracaoEmail(nf.filialId());
		
		SimpleMailMessage ssimpleMailMessagemm = new SimpleMailMessage();
		
		List<Email> emails = obterEmailService.obterEmailsContatoDaNotaFiscal(nf.notaFiscalId(),nf.filialId().toString());
		
		if (emails.size() == 0 && nf.ambiente()==Ambiente.PRODUCAO)
			throw new RuntimeException("Nenhum contato de e-mail encontrado para Nota Fiscal: " 
					+ nf.numero());
		
		String to[] = new String[emails.size()]; 
		for (int i=0; i < emails.size(); i++)
			to[i] = emails.get(i).email();
		emails.toArray();
		
		ssimpleMailMessagemm.setFrom(server.getFrom());
		if (nf.ambiente()==Ambiente.HOMOLOGACAO) 
			ssimpleMailMessagemm.setTo("teste@hadrion.com.br");
		else
			ssimpleMailMessagemm.setTo(to);
		ssimpleMailMessagemm.setSubject("Arquivo XML e DANFE da NF-e número: "+nf.numero());
		String mensagem = "Segue anexo o arquivo XML e DANFE da nota fiscal número " 
				+ nf.numero() + ", em nome de " 
				+ (nf.tipoOperacao() == TipoOperacao.SAIDA ? 
						nf.destinatario().razaoSocial() :
						nf.emitente().razaoSocial())
				+ ".";
		ssimpleMailMessagemm.setText(mensagem);
		
		return ssimpleMailMessagemm;
	}
	
	public ResponseEntity<InputStreamResource> obterDanfe(NotaFiscal nf) throws JRException{
		
		byte[] pdf = gerarDanfe(gerarXml(nf),nf);
		
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(new MediaType("application", "pdf"));
		respHeaders.set("Cache-Control", "no-cache");
		respHeaders.set("Content-Disposition",
				"inline; filename=pre-" + nf.chaveAcesso() + ".pdf");
		InputStreamResource isr = new InputStreamResource(
				new ByteArrayInputStream(pdf));
		return new ResponseEntity<InputStreamResource>(isr, respHeaders,
				HttpStatus.OK);
	}

	public NotaFiscalData obterNotaFiscal(String notaFiscalId) {
		return construir(nota(notaFiscalId));
	}

	public String cancelar(CancelarNotaComando comando) {
		cancelarNotaService.cancelar(new SolicitacaoCancelamento(
				new NotaFiscalId(comando.getNotaFiscalId()), 
				StringUtils.trimToEmpty(comando.getJustificativa())));
		return String.valueOf(nota(comando.getNotaFiscalId()).numeroProtocoloCancelamento());
	}
	
	public void registrarCartaCorrecao(RegistrarCartaCorrecaoComando comando){
		registrarCartaCorrecaoService.registar(
				new NotaFiscalId(comando.getNotaFiscalId()), 
				StringUtils.trimToEmpty(comando.getCorrecao()));
	}

	
	public ResponseEntity<InputStreamResource> imprimirCce(
			String notaFiscalId) throws IOException, JRException {
		NotaFiscal nf = notaFiscalRepositorio
				.notaFiscalPeloId(new NotaFiscalId(notaFiscalId));
		return obterCce(nf);
	}
	public ResponseEntity<InputStreamResource> obterCce(NotaFiscal nf) throws JRException{
		
		byte[] pdf = gerarCce(gerarXmlCce(nf.cartaCorrecaoAtual()),nf.filialId());
		
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(new MediaType("application", "pdf"));
		respHeaders.set("Cache-Control", "no-cache");
		respHeaders.set("Content-Disposition",
				"inline; filename=pre-" + nf.chaveAcesso() + ".pdf");
		InputStreamResource isr = new InputStreamResource(
				new ByteArrayInputStream(pdf));
		return new ResponseEntity<InputStreamResource>(isr, respHeaders,
				HttpStatus.OK);
	}

	private byte[] gerarCce(Document cce,FilialId filialId) throws JRException{
		JasperReport jasperReport;JasperPrint jasperPrint;
		JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlParaInpuStream(cce), "/");

		InputStream reportStream = getClass().getClassLoader().getResourceAsStream("report/cce.jasper");
    	jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
    	
    	Map<String,Object> parameters= new HashMap<String, Object>();
    	
    	parameters.put("REPORT_LOCALE", new Locale("pt","BR"));
		//jasperReport = JasperCompileManager.compileReport(getClass().getClassLoader().getResourceAsStream("report/cce.jrxml"));
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,xmlDataSource);		
		return JasperExportManager.exportReportToPdf(jasperPrint);		
	}
	public ResponseEntity<InputStreamResource>  xmlNfe(String notaFiscalId) throws JRException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError{
		
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(new MediaType("application", "xml"));
		respHeaders.set("Cache-Control", "no-cache");
		respHeaders.set("Content-Disposition","attachment; filename=" + notaFiscalId + ".xml");
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
		Source xmlSource = new DOMSource(gerarXml(nota(notaFiscalId))); 
		Result outputTarget = new StreamResult(outputStream); 
		TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget); 
		InputStream is = new ByteArrayInputStream(outputStream.toByteArray());		
		InputStreamResource isr = new InputStreamResource(is);
		
		return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK); 
	}

	public void definirNotaEmLote(DefinirNotaFiscalEmLoteComando comando) {
		NotaFiscal nf = nota(comando.getNotaFiscalId());
		if (nf != null && !nf.estaAutorizada() && !nf.estaCancelada()){
			nf.definirComoEmLote();
			salvar(nf);
		}
	}
	
	private void salvar(NotaFiscal nf){
		this.notaFiscalRepositorio.salvar(nf);
	}

	public List<NotaFiscalData> notasFiscaisAutorizadas(List<NotaFiscalId> ids) {
		return notaFiscalRepositorio.notasAutorizadas(ids).stream()
				.map(n -> construir(n))
				.collect(Collectors.toList());
	}
	
	public Optional<NotaFiscalData> notaFiscalAutorizada(NotaFiscalId id) {
		return notaFiscalRepositorio.notaAutorizada(id)
				.map(n -> construir(n));
	}
	
	public NotaFiscalData notaAutorizadaParaMdfe(NotaFiscalId id, boolean autonomo, boolean fretePago) {
		
		NotaFiscal nf = nf(id);
		
		if (nf.transporte().modalidadeFrete() == ModalidadeFrete.EMITENTE && fretePago)
			if (nf.transporte().transportador().cpf()!=null && autonomo)
				return construir(nf);

		return null;
	}
	
	NotaFiscal nf(NotaFiscalId id){
		return notaFiscalRepositorio.notaAutorizada(id)
				.orElseThrow(() -> new EntidadeNaoEncontradoException("NotaFiscal",id));
	}
}
