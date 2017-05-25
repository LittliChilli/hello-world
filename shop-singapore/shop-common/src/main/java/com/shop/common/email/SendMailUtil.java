package com.shop.common.email;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailUtil {

	@SuppressWarnings("static-access")
	public static void sendMessage(String smtpHost, String fromEmail, String fromLogin, String fromUserPassword,
			String toEmail, String subject, String messageText, String messageType) throws MessagingException {
		// 第一步：配置javax.mail.Session对象
		System.out.println("为" + smtpHost + "配置mail session对象");

		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.starttls.enable", "true");// 使用 STARTTLS安全连接
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true"); // 使用验证

		Authenticator authenticator = new MyAuthenticator(fromEmail, "ncxrottubrpvbdff");

		Session mailSession = Session.getInstance(props, authenticator);

		// 第二步：编写消息
		System.out.println("编写消息from——to:" + fromEmail + "——" + toEmail);

		InternetAddress fromAddress = new InternetAddress(fromEmail);
		InternetAddress toAddress = new InternetAddress(toEmail);

		MimeMessage message = new MimeMessage(mailSession);

		message.setFrom(fromAddress);
		message.addRecipient(RecipientType.TO, toAddress);

		message.setSentDate(Calendar.getInstance().getTime());
		message.setSubject(subject);
		message.setContent(messageText, messageType);

		// 第三步：发送消息
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(smtpHost, fromLogin, fromUserPassword);
		transport.send(message, message.getRecipients(RecipientType.TO));
		System.out.println("message yes");
	}

	public static void main(String[] args) {
		try {
			SendMailUtil.sendMessage("smtp.qq.com", "565372710@qq.com", "邹晟", "anye265147@", "zous@mmfenqi.com", "nihao222",
					"---------------wrwe---- O(∩_∩)O~ 邮件发送-------", "text/html;charset=UTF-8");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class MyAuthenticator extends Authenticator {
	String userName = "";
	String password = "";

	public MyAuthenticator() {

	}

	public MyAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
