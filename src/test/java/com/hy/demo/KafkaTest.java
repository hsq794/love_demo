package com.hy.demo;

import com.hy.demo.kafka.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KafkaTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void kafkaProducer(){
        kafkaProducer.send("demo","发送第一条信息msg");

    }

    @Test
    public void kafkaProducerCallBack(){
        kafkaProducer.CallBackSend01("demo","hello world callBack");
    }

    @Test
    public void kafkaProducerCallBack2(){
        kafkaProducer.CallBackSend02("demo","hello world callBack2");
    }

}
