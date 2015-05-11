package com.orange.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.orange.log.LoggerFactory;

/**
 * 发送邮件
 * @author hqch
 *
 */
public class MailUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MailUtil.class);

	private static final String HOST = "smtp.exmail.qq.com";
	
	private static final int PORT = 25;
	
	private static final String USERNAME = "monitor@orangegame.cn";
	
	private static final String PASSWORD = "orange.game2013";
	
	private static final String MAIL_TO = "huqc@orangegame.cn,guojiang@orangegame.cn";

	private static final String MAIL_FROM = "monitor@orangegame.cn";

	private static final String MAIL_SUBJECT = "监管服务器发生宕机";

	private static final String MAIL_BODY = "DOMAINID：%s IP：%s PORT：%s 宕机!";

	private static final String PERSONALNAME = "橙子游戏分发服务器";
	
	/**
	 * 发送邮件
	 */
	public static void sendServerShutDownMail(Object ... args){
		Properties props = new Properties(); // 获取系统环境
		Authenticator auth = new MailUtil().new Email_Autherticator(USERNAME,PASSWORD); // 进行邮件服务器用户认证
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, auth);
		// 设置session,和邮件服务器进行通讯。
		MimeMessage message = new MimeMessage(session);
		try {
			message.setSubject(MAIL_SUBJECT);
			message.setText(String.format(MAIL_BODY, args)); // 设置邮件正文
			message.setSentDate(new Date()); // 设置邮件发送日期
			Address address = new InternetAddress(MAIL_FROM, PERSONALNAME);
			message.setFrom(address); // 设置邮件发送者的地址
			String[] toNames = MAIL_TO.split(",");
			Address toAddress = null;
			for(String name : toNames){
				toAddress = new InternetAddress(name); // 设置邮件接收方的地址
				message.addRecipient(Message.RecipientType.TO, toAddress);
				Transport.send(message); // 发送邮件
				logger.info("send " + name + " ok!");
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	class Email_Autherticator extends Authenticator {

		private String user;
		private String pwd;
		
		public Email_Autherticator() {
		}

		public Email_Autherticator(String user, String pwd) {
			this.user = user;
			this.pwd = pwd;
		}

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pwd);
		}
	}
}
