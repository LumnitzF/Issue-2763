/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foo.bar.webapp;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author Lumnitzf
 */
@Stateless
@LocalBean
public class JMSSender {

	@Resource(mappedName = "jms/LogToInfoTopicConnectionFactory")
	private ConnectionFactory logToInfoTopicConnectionFactory;
	@Resource(mappedName = "jms/LogToInfoTopic")
	private Topic logToInfoTopic;

	private Connection logToInfoTopicConnection;
	private Session logToInfoSession;
	private MessageProducer logToInfoProducer;

	@PostConstruct
	public void init() {
		try {
			logToInfoTopicConnection = logToInfoTopicConnectionFactory.createConnection();
			logToInfoSession = logToInfoTopicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			logToInfoProducer = logToInfoSession.createProducer(logToInfoTopic);
		}
		catch (JMSException ex) {
			Logger.getLogger(JMSSender.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@PreDestroy
	public void closeConnections() {
		try {
			logToInfoSession.close();
		}
		catch (JMSException ex) {
			Logger.getLogger(JMSSender.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendMessage(int amount) {
		for (int i = 0; i < amount; i++) {
			try {
				TextMessage message = logToInfoSession.createTextMessage(new Date().toString());
				logToInfoProducer.send(message);
			}
			catch (JMSException ex) {
				Logger.getLogger(JMSSender.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
