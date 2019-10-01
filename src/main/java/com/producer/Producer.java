package com.producer;

import java.io.IOException;

import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.utils.MQConnectionUtils;

//简单队列生产者
public class Producer {
	//队列名称
	private static final String QUEUE_NAME ="zrf_644";
	public static void main(String[] args) throws IOException, TimeoutException {
		//1.创建一个新的连接
		Connection connection = MQConnectionUtils.newConnection();
		//2.创建通道
		Channel channel = connection.createChannel();
		//3.创建一个队列 参数1:队列名称 参数2:是否持久化   参数3:是否唯一  参数4:是否自动删除
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for(int i=0;i<50;i++) {
			//4.创建消息
			String msg ="zrf_644444msg:"+i;
			//5.生产者发送消息
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			System.out.println("生产者发送消息成功:"+msg);
		}
		//6.关闭通道和连接
		channel.close();
		connection.close();
	}
}
