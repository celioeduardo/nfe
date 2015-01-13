package com.hadrion.util.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hadrion.nfe.dominio.config.Application;
import com.hadrion.nfe.dominio.config.MailSenderAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class}, loader = SpringApplicationContextLoader.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EmailTest {

	@Autowired 	MailSenderAutoConfiguration bean;

	@Test @Ignore
	public void enviar() {
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
