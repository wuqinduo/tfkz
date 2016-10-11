package com.tfkz.rpc.mq.handler;

import javax.jms.Message;

/**
 * 消息处理器接口
 * @author Administrator
 *
 */
public interface MessageHandler {
	public void handle(Message message);
}
