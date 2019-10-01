package com.producer;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.MQConnectionUtils;

//�򵥶���������
public class Producer {
	//��������
	private static final String QUEUE_NAME ="zrf_644";
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.����һ���µ�����
		Connection connection = MQConnectionUtils.newConnection();
		//2.����ͨ��
		Channel channel = connection.createChannel();
		//3.����һ������ ����1:�������� ����2:�Ƿ�־û�   ����3:�Ƿ�Ψһ  ����4:�Ƿ��Զ�ɾ��
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for(int i=0;i<50;i++) {
			//4.������Ϣ
			String msg ="zrf_644444msg:"+i;
			//5.�����߷�����Ϣ
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.out.println("�����߷�����Ϣ�ɹ�:"+msg);
		}
		//6.�ر�ͨ��������
		channel.close();
		connection.close();
	}
}
