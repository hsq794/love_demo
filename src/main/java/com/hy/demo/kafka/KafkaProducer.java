package com.hy.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * 生产者
 * @author hsq
 */
@Component
public class KafkaProducer {

    private static final Logger log= LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 生产者简单发送消息
     * @param topic
     * @param msg
     */
    public void send(String topic,String msg){
        System.out.println("发送信息内容："+msg);
        kafkaTemplate.send(topic,msg);
    }

    /**
     * 回调发送信息 是否成功
     * @param topic
     * @param msg
     */
    public void CallBackSend01(String topic,String msg){
        System.out.println("发送信息内容："+msg);
        kafkaTemplate.send(topic, msg).addCallback(success -> {
            // 消息发送到的topic
            String topics = success.getRecordMetadata().topic();
            // 消息发送到的分区
            int partition = success.getRecordMetadata().partition();
            // 消息在分区内的offset
            long offset = success.getRecordMetadata().offset();
            System.out.println("发送消息成功:" + topics + "-" + partition + "-" + offset);
        }, failure -> {

            System.out.println("发送消息失败:" + failure.getMessage());
        });

    }

    /**
     * 第二种方法
     * 回调发送信息 是否成功
     * @param topic
     * @param msg
     */
    public void CallBackSend02(String topic,String msg){
        System.out.println("发送信息内容："+msg);
        kafkaTemplate.send(topic, msg).addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败："+ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }
        });

    }



}
