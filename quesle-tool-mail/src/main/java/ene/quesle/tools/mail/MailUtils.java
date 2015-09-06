package ene.quesle.tools.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtils {
	
	public static final String MAIL_TYPE_HTML = "text/html; charset=utf-8";
	public static final String MAIL_TYPE_TEXT = "test/plain; charset=UTF8";
	
	private static Properties mailProperties(String serverHost, int serverPort, boolean validate){
		Properties props = new Properties();
		props.put("mail.smtp.host", serverHost);
		props.put("mail.smtp.port", serverPort);
		props.put("mail.smtp.auth", validate);
		return props;
	}
	
	private static Session mailAuthenticatior(String username, String password, Properties properties){
		MailAuthenticator authenticator = null;
		if (Boolean.parseBoolean(String.valueOf(properties.get("mail.smtp.auth")))) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MailAuthenticator(username, password);
		}
		
		return Session.getDefaultInstance(properties, authenticator);
	}
	
	public static boolean sendMailWithAdditional(MailServer server, MailContent content){
		
		Properties properties = mailProperties(server.getServerHost(), server.getServerPort(), server.isValidate());
		Session session = mailAuthenticatior(server.getUsername(), server.getPassword(), properties);
		
		
		try {
			// 根据session创建一个邮件消息
			Message message = new MimeMessage(session);
			setMailInfo(message, content);
			
			// 设置邮件消息的主要内容
			Multipart multipart = new MimeMultipart();
			
			//设置邮件的显示类容
			setMailHtmlContent(multipart, content);
			
			//设置邮件附录文件
			setMailAdditionalContent(multipart, content);
			
			//将multipart对象放到message中
			message.setContent(multipart);
	        //保存邮件
			message.saveChanges();
			// 发送邮件
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		
		
		return true;
	}
	
	public static void setMailInfo(Message message, MailContent content) throws MessagingException{
		// 创建邮件发送者地址
		Address from = new InternetAddress(content.getFrom());
		// 设置邮件消息的发送者
		message.setFrom(from);
		// 创建邮件的接收者地址，并设置到邮件消息中
		Address to = new InternetAddress(content.getTo());
		message.setRecipient(Message.RecipientType.TO, to);
		// 设置邮件消息的主题
		message.setSubject(content.getTitle());
		// 设置邮件消息发送的时间
		message.setSentDate(new Date());
	}
	
	public static void setMailTextContent(Message message, MailContent content) throws MessagingException{
		message.setContent("content.getContent()", MAIL_TYPE_TEXT);
		//message.setText(content.getContent());
	}
	
	public static void setMailHtmlContent(Multipart multipart, MailContent content) throws MessagingException{
		//   设置邮件的文本内容
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(content.getContent(), MAIL_TYPE_HTML);
        multipart.addBodyPart(contentPart);
	}
	
	@SuppressWarnings("restriction")
	public static void setMailAdditionalContent(Multipart multipart, MailContent content) throws MessagingException{
		while (content.hasNext()) {
			//添加附件
            BodyPart messageBodyPart= new MimeBodyPart();
            DataSource source = new FileDataSource(content.getFilePath());
            //添加附件的内容
            messageBodyPart.setDataHandler(new DataHandler(source));
            //添加附件的标题
            //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            messageBodyPart.setFileName("=?UTF-8?B?"+enc.encode(content.getFileName().getBytes())+"?=");
            multipart.addBodyPart(messageBodyPart);
		}
	}
	
}
