package com.hadrion.util.email;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.nfe.dominio.config.Application;
import com.hadrion.nfe.dominio.config.MailSenderAutoConfiguration;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class}, loader = SpringApplicationContextLoader.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EmailTest {

	@Autowired 	
	MailSenderAutoConfiguration bean;
	@Autowired	
	private NotaFiscalRepositorio repositorio;

	private JasperReport jasperReport;
	private JasperPrint jasperPrint;

	@Test @Ignore
	public void enviarEmail() {
		bean.mailSender().send(new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage)
                    throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                        false, "UTF-8");
                message.setFrom("teste@hadrion.com.br");
                message.addTo("hdr_ricardo@hotmail.com");
                message.setSubject("TDD From nota 3.0");
                message.setText("Teste via bean!.", true);
            }
        });
	}
	
	SimpleMailMessage smm(){
		
		SimpleMailMessage ssimpleMailMessagemm = new SimpleMailMessage();
		
		ssimpleMailMessagemm.setFrom("teste@hadrion.com.br");
		ssimpleMailMessagemm.setTo("hdr_ricardo@hotmail.com");
		ssimpleMailMessagemm.setSubject("NOTA FISCAL 3.1");
		ssimpleMailMessagemm.setText("TDD via MailSender.MimeMessageHelper.SimpleMailMessage");
		
		return ssimpleMailMessagemm;
	}
	
	DataSource danfe() throws JRException{
		File xmlFile = new File("src/test/resources/report/nfe.xml");
    	
    	JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFile,"/nfeProc/NFe/infNFe/det");    	
    	jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
    	jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  

    	return new ByteArrayDataSource(JasperExportManager.exportReportToPdf(jasperPrint), "application/xml");
	}
	
	@Test @Ignore
	public void enviarEmailXmlEDanfe() throws IOException, MessagingException, JRException {
		MimeMessage mm = bean.mailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm, true);
		
		String filename = "H-03F79B1D8D397592E050007F01005CC8";
		File xml = new File("src/test/resources/report/nfe.xml");		
		
		helper.setFrom(smm().getFrom());
		helper.setTo(smm().getTo());
		helper.setSubject(smm().getSubject());
		helper.setText(smm().getText());		
		helper.addAttachment(filename + ".xml", xml);		
		helper.addAttachment(filename + ".pdf", danfe());
	
		bean.mailSender().send(mm);
	}
	@Test @Ignore
	public void enviarEmailComAnexoXml() throws IOException, MessagingException {
		
		File xml = new File("src/test/resources/report/nfe.xml");
		
		MimeMessage mm = bean.mailSender().createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mm, true);
		
		helper.setFrom(smm().getFrom());
		helper.setTo(smm().getTo());
		helper.setSubject(smm().getSubject());
		helper.setText(smm().getText());		
		helper.addAttachment("H-03F79B1D8D397592E050007F01005CC8" + ".xml", xml);
		
		bean.mailSender().send(mm);
	}

	@Test @Ignore
	public void teste() throws AddressException, MessagingException{
		String host = "smtp.gmail.com";
		String to = "hdr_ricardo@hotmail.com";
		String from = "teste@hadrion.com.br";
		String subject = "My First Email";
		String messageText = "I am sending a message using the";
		boolean sessionDebug = true;
	
		Properties props = System.getProperties();
		props.put("mail.host", host);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		
//		p.put("mail.debug", "true");
//		p.put("mail.smtp.starttls.enable","true");
//		p.put("mail.smtp.auth","true");
		props.put("mail.smtp.ssl.trust","smtp.gmail.com");
//		p.put("mail.smtp.quitwait","true");
//		p.put("mail.smtp.socketFactory","true");
//		p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); 
//		p.put("mail.smtp.socketFactory.fallback","false");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() { 
			protected PasswordAuthentication getPasswordAuthentication() { 
				return new PasswordAuthentication("teste@hadrion.com.br", "testeteste"); 
			} 
		});
		
		session.setDebug(sessionDebug);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		InternetAddress[] address = {new InternetAddress(to)};
		msg.setRecipients(Message.RecipientType.TO, address);
		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setText(messageText);
		Transport.send(msg);
	}
}
