package com.sbk.service.impl.activeMQDemo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;
            //取消息内容
            String text = textMessage.getText();
            System.out.println("我收到消息了");
            System.out.println(text);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
