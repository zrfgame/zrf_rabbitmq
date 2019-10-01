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
	//队列名称
		private static final String QUEUE_NAME ="zrf_644";
		public static void main(String[] args) throws IOException, TimeoutException {
			System.out.println("消费者01");
			//1.创建一个新的连接
			Connection connection = MQConnectionUtils.newConnection();
			//2.创建通道
			final Channel channel = connection.createChannel();
			//3.创建一个队列 参数1:队列名称 参数2:是否持久化   参数3:是否唯一  参数4:是否自动删除
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicQos(1);
			DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
				//监听获取消息
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
						byte[] body) throws IOException {
					String msg = new String(body,"UTF-8");
					System.out.println("消费者获取生产者消息:"+msg);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
					
				}
			};
			//设置应该模式  设置为true 表示默认自动应答  设置为false，则为手动应答
			channel.basicConsume(QUEUE_NAME, false,defaultConsumer);
		}

}
