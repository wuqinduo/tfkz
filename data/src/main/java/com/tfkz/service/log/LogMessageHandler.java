package com.tfkz.service.log;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tfkz.dao.userManagement.LogDao;
import com.tfkz.model.userManagement.Log;
import com.tfkz.rpc.mq.handler.MessageHandler;

@Service
public class LogMessageHandler implements MessageHandler {

	@Autowired
    private LogDao logDao;
    
    public void handle(Message message) {
        System.out.println(logDao);
        ObjectMessage objMsg = (ObjectMessage)message;
        try {
            Log log = (Log)objMsg.getObject();
            logDao.insertLog(log);//将日志写入数据库
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}