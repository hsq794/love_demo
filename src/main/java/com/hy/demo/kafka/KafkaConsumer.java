package com.hy.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消费者
 * @author hsq
 */
@Component
public class KafkaConsumer {

    /**
     *  消费监听
     * @param record
     */
    @KafkaListener(groupId ="groupId01",topics = {"demo"})
    public void onMessage1(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费："+record.topic()+"-"+record.partition()+"-"+record.value());
    }

    /**
     *  消费监听
     * @param record
     */
    @KafkaListener(groupId ="groupId02",topics = {"demo"})
    public void onMessage2(ConsumerRecord<?, ?> record){
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费2："+record.topic()+"-"+record.partition()+"-"+record.value());
    }


}
