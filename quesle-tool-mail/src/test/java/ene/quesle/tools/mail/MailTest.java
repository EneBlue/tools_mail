package ene.quesle.tools.mail;

import org.junit.Test;

public class MailTest {

	@Test
	public void test(){
		
		MailServer server = new MailServer();
		server.setServerHost("smtp.126.com");
		server.setServerPort(25);
		server.setUsername("****1@126.com");
		server.setPassword("******");
		
		MailContent content = new MailContent();
		content.setFrom("****1@126.com");
		content.setTo("****2@126.com");
		content.setTitle("This is a test!");
		content.setContent("Hello World!");
		
		content.addAdditionalFile("test1.docx", "C:/test1.docx");
		
		content.addAdditionalFile("test2.docx", "C:/test2.docx");
		
		MailUtils.sendMailWithAdditional(server, content);
	}
}
