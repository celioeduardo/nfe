package com.hadrion.nfe.aplicacao.nf;

import static com.hadrion.util.xml.XmlUtil.xmlParaInpuStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.transaction.Transactional;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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

import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.config.MailProperties;
import com.hadrion.nfe.dominio.config.MailProperties.Server;
import com.hadrion.nfe.dominio.modelo.Ambiente;
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
import com.hadrion.nfe.dominio.modelo.nf.Contingencia;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.ObterEmailService;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;
import com.hadrion.nfe.tipos.Email;
import com.hadrion.util.xml.XmlUtil;

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
	
	public List<NotaFiscalData> notasFicaisPendentesAutorizacaoResumo(
			Ambiente ambiente, Double empresa, String filial, Date inicio,
			Date fim, String notistaId, String notaFiscalId) {
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		NotaFiscalId notaFiscalIdFiltro = null;

		if (notaFiscalId != null)
			notaFiscalIdFiltro = new NotaFiscalId(notaFiscalId);

		for (DescritorNotaFiscal nf : notaFiscalRepositorio
				.notasPendentesAutorizacaoResumo(ambiente, empresa,
						new FilialId(filial), inicio, fim, notistaId,
						notaFiscalIdFiltro)) {
			result.add(new NotaFiscalData(nf.notaFiscalId().id(), nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.chave() != null ? String.valueOf(nf.chave()) : null,
					String.valueOf(nf.tipoEmissao()), nf.emissao(), nf.valor()
							.valor(), nf.publicoTipo(), nf.publicoCodigo(), nf
							.publicoNome(), nf.tipo(),
					nf.mensagem() != null ? new Long(nf.mensagem().codigo())
							: null, nf.mensagem() != null ? nf.mensagem()
							.descricao() : null));
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

	public List<NotaFiscalData> notasFicaisAutorizadasResumo(Ambiente ambiente,
			Double empresa, String filial, Date inicio, Date fim,
			String notistaId, String notaFiscalId) {

		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();

		List<NotaFiscal> notas = null;
		if (notistaId != null && !notistaId.isEmpty())
			notas = notaFiscalRepositorio.notasAutorizadas(
					new FilialId(filial), ambiente, new NotistaId(notistaId));
		else
			notas = notaFiscalRepositorio.notasAutorizadas(
					new FilialId(filial), ambiente);

		for (NotaFiscal nf : notas)
			result.add(construir(nf));

		return result;

	}

	public List<NotaFiscalData> notasFicaisAutorizadasNaoImpressasResumo(
			Ambiente ambiente, Double empresa, String filial, Date inicio,
			Date fim, String notistaId, String notaFiscalId) {

		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();

		List<NotaFiscal> notas = null;

		if (notistaId != null && !notistaId.isEmpty())
			notas = notaFiscalRepositorio.notasAutorizadasNaoImpressas(
					new FilialId(filial), ambiente, new NotistaId(notistaId));
		else
			notas = notaFiscalRepositorio.notasAutorizadasNaoImpressas(
					new FilialId(filial), ambiente);

		for (NotaFiscal nf : notas)
			result.add(construir(nf));

		return result;

	}

	public ResponseEntity<InputStreamResource> imprimirDanfe(String notaFiscalId)
			throws IOException, JRException {
		NotaFiscal nf = notaFiscalRepositorio
				.notaFiscalPeloId(new NotaFiscalId(notaFiscalId));
		nf.definirDanfeComoImpresso();
		notaFiscalRepositorio.salvar(nf);
		return preVisualizarDanfe(notaFiscalId);
	}

	public ResponseEntity<InputStreamResource> preVisualizarDanfe(
			String notaFiscalId) throws IOException, JRException {
		NotaFiscal nf = notaFiscalRepositorio
				.notaFiscalPeloId(new NotaFiscalId(notaFiscalId));
		return obterDanfe(nf);
	}


	public String enviarNotas(EnviarNotasComando comando) {
		Ambiente ambiente = Ambiente.valueOf(comando.getAmbiente());

		Lote lote = null;

		if (ambiente == Ambiente.PRODUCAO)
			lote = geracaoLoteService.gerarLoteEmProducao(notas(
					comando.getIds(), ambiente));
		else
			lote = geracaoLoteService.gerarLoteEmHomologacao(notas(
					comando.getIds(), ambiente));

		loteRepositorio.salvar(lote);

		enviarLoteService.enviar(lote);

		loteRepositorio.salvar(lote);

		return String.valueOf(lote.loteId());

	}

	public void definirNotaComoRejeitada(DefinirNotaComoRejeitadaComando comando) {
		NotaFiscal nota = nota(comando.getNotaFiscalId());
		if (nota != null)
			nota.rejeitada(new Mensagem(comando.getMsgCodigo(), comando
					.getMsgDescricao()));
		notaFiscalRepositorio.salvar(nota);
	}

	public void definirNotaComoAutorizada(
			DefinirNotaComoAutorizadaComando comando) {
		NotaFiscal nota = nota(comando.getNotaFiscalId());
		if (nota != null)
			nota.autorizada(
					new NumeroProtocolo(comando.getNumeroProtocolo()),
					new Mensagem(comando.getMsgCodigo(), comando
							.getMsgDescricao()), comando.getXmlProtocolo());
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
		List<NotaFiscalId> listaId = new ArrayList<NotaFiscalId>();

		for (String notaFiscalId : ids)
			listaId.add(new NotaFiscalId(notaFiscalId));

		return new HashSet<NotaFiscal>(
				notaFiscalRepositorio.notasPendentesAutorizacao(listaId,
						ambiente));
	}

	private NotaFiscalData construir(NotaFiscal nf) {
		return new NotaFiscalData(
				nf.notaFiscalId().id(),
				nf.numero(),
				String.valueOf(nf.serie().numero()),
				nf.chaveAcesso() != null ? String.valueOf(nf.chaveAcesso())
						: null,
				String.valueOf(nf.tipoEmissao()),
				nf.emissao(),
				nf.total().valor(),
				null,
				null,
				nf.destinatario().razaoSocial(),
				nf.tipoOperacao().toString(),
				nf.mensagem() != null ? new Long(nf.mensagem().codigo()) : null,
				nf.mensagem() != null ? nf.mensagem().descricao() : null);
	}

	
	public byte[] gerarDanfe(Document nfeProc) throws JRException{
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		
		JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlParaInpuStream(nfeProc), "/nfeProc/NFe/infNFe/det");
		jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
		jasperPrint = JasperFillManager.fillReport(jasperReport, null,xmlDataSource);		
		return JasperExportManager.exportReportToPdf(jasperPrint);		
	}
	
	public Document gerarXml(NotaFiscal nf) throws JRException{
		
		NotaFiscalSerializador serializador = new NotaFiscalSerializador();
		
		Document nfeProc = XmlUtil.novoDocument();
		nfeProc.normalizeDocument();
		Element root = nfeProc.createElementNS(
				"http://www.portalfiscal.inf.br/nfe", "nfeProc");
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
		
		Server server = configuracaoEmail(nf.filialId());
		
		if (server == null)
			throw new RuntimeException("Configuração de e-mail não definida");
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		Properties properties = new Properties();
		if (server.getStarttls() != null)
			properties.put("mail.smtp.starttls.enable", server.getStarttls());
		
		if (server.getTrust() != null)
			properties.put("mail.smtp.ssl.trust", server.getTrust());

		if (server.getDebug() != null)
			properties.put("mail.debug", server.getDebug());
		
		mailSender.setJavaMailProperties(properties);
		mailSender.setUsername(server.getUsername());
		mailSender.setPassword(server.getPassword());
		mailSender.setHost(server.getHost());
		mailSender.setPort(server.getPort());		
		MimeMessage mm = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm, true);
		
		String filename = nf.chaveAcesso().toString();
		
		Document xml = gerarXml(nf);		
		byte[] pdf = gerarDanfe(xml);
		
		SimpleMailMessage message = smm(nf);
		
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
		
		List<Email> emails = obterEmailService.obterEmailsContatoDaNotaFiscal(nf.notaFiscalId());
		
		if (emails.size() == 0)
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
		
		byte[] pdf = gerarDanfe(gerarXml(nf));
		
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
}
