package jms.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class TemporaryQueueTest {
	public static void main(String[] args) throws Exception {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("vm://localhost");
		Connection connection = factory.createConnection();
		connection.start();

		Queue queue = new ActiveMQQueue("testQueue2");
		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//ʹ��session����һ��TemporaryQueue��
		TemporaryQueue replyQueue = session.createTemporaryQueue();

		//������Ϣ�����ظ���ָ����Queue�У���replyQueue��
		MessageConsumer comsumer = session.createConsumer(queue);
		comsumer.setMessageListener(new MessageListener(){
			public void onMessage(Message m) {
				try {
					System.out.println("Get Message: " + ((TextMessage)m).getText());
					MessageProducer producer = session.createProducer(m.getJMSReplyTo());
					producer.send(session.createTextMessage("ReplyMessage"));
				} catch (JMSException e) { }
			}
		});

		//ʹ��ͬһ��Connection������һ��Session������ȡreplyQueue�ϵ���Ϣ��
		Session session2 = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer replyComsumer = session2.createConsumer(replyQueue);
		replyComsumer.setMessageListener(new MessageListener(){
			public void onMessage(Message m) {
				try {
					System.out.println("Get reply: " + ((TextMessage)m).getText());
				} catch (JMSException e) { }
			}
		});

		MessageProducer producer = session.createProducer(queue);
		TextMessage message = session.createTextMessage("SimpleMessage");
		message.setJMSReplyTo(replyQueue);
		producer.send(message);
	}
}
