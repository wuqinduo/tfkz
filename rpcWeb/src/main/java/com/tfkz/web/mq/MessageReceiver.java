package com.tfkz.web.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tfkz.rpc.mq.util.ActiveMQP2PUtil;
import com.tfkz.service.log.LogMessageHandler;

/**
 * 用于接收消息的测试类
 */
@Controller
@RequestMapping("/mq")
public class MessageReceiver {
    
    @Autowired
    private LogMessageHandler handler;
    
    @RequestMapping("/receive")
    @ResponseBody
    public String receiveMessage() {
        ActiveMQP2PUtil.receiveMessage(handler);
        
        return "ok";
    }
    
}
