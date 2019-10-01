package com.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.utils.MQConnectionUtils;

public class Consumer {
	//��������
		private static final String QUEUE_NAME ="zrf_644";
		public static void main(String[] args) throws IOException, TimeoutException {
			System.out.println("������01");
			//1.����һ���µ�����
			Connection connection = MQConnectionUtils.newConnection();
			//2.����ͨ��
			final Channel channel = connection.createChannel();
			//3.����һ������ ����1:�������� ����2:�Ƿ�־û�   ����3:�Ƿ�Ψһ  ����4:�Ƿ��Զ�ɾ��
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicQos(1);
			DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
				//������ȡ��Ϣ
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String msg = new String(body,"UTF-8");
					System.out.println("�����߻�ȡ��������Ϣ:"+msg);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
					
				}
			};
			//����Ӧ��ģʽ  ����Ϊtrue ��ʾĬ���Զ�Ӧ��  ����Ϊfalse����Ϊ�ֶ�Ӧ��
			channel.basicConsume(QUEUE_NAME, false,defaultConsumer);
		}

}
