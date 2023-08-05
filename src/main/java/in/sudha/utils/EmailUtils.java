package in.sudha.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendMail(String subject,String body,String to) {
		boolean isSent=false;
	
		try {
			
			MimeMessage mimeMsg = mailSender.createMimeMessage();
	          MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
	          helper.setSubject(subject);
	          helper.setText(body, true);
	          helper.setTo(to);
	          
	          mailSender.send(mimeMsg);
	          
	          isSent=true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return isSent;
		
		
	}
	
}
