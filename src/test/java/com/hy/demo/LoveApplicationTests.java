package com.hy.demo;

import com.hy.demo.entity.User;
import com.hy.demo.util.*;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class LoveApplicationTests {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private RestTemplateToInterface restTemplateToInterface;

    @Test
    void contextLoads() {
    }

    @Test
    public void testApi() {
        //测试get方法
        String s = HttpClientUtil.doGet("http://localhost:9092/api/getHello", "UTF-8");
        System.out.println("get方法:"+s);
        //测试post方法
        User user = new User();
        user.setUname("胡萝卜");
        user.setRole("普通用户");
        JSONObject jsonObject = new JSONObject();
        String s1 = JsonUtil.toJson(user);
        jsonObject.put("param",s1);
        String postString = HttpClientUtil.doPost("http://localhost:9092/api/postHello", jsonObject);
        System.out.println("post方法:"+postString);
    }

    @Test
    public void testJDKApi(){
        //测试get方法
        String s = HttpClientUtil2.doGet("http://localhost:9092/api/getHello");
        System.out.println("get方法:"+s);
        //测试post方法
        User user = new User();
        user.setUname("胡萝卜");
        user.setRole("普通用户");
        String s1 = JsonUtil.toJson(user);
        String postString = HttpClientUtil2.doPost("http://localhost:9092/api/postHello",s1);
        System.out.println("post方法:"+postString);
    }

    @Test
    public void testSpringBootApi(){
        Result result= restTemplateToInterface.doGetWith1("http://localhost:9092/api/getHello");
        System.out.println("get结果："+result);
        User user = new User();
        user.setUname("胡萝卜");
        user.setRole("普通用户");
        String s = restTemplateToInterface.doPostWith1("http://localhost:9092/api/postHello", user);
        System.out.println("post结果："+s);
    }


    @Test
    public void testString(){
        //操作String类型的数据
        ValueOperations<String, String> valueStr = redisTemplate.opsForValue();
        //存储一条数据
        valueStr.set("goodsProdu","长安");
        //获取一条数据并输出
        String goodsName = valueStr.get("goodsProdu");
        System.out.println(goodsName);
        //存储多条数据
        Map<String,String> map = new HashMap<>();
        map.put("goodsName","福特汽车");
        map.put("goodsPrice","88888");
        map.put("goodsId","88");

        valueStr.multiSet(map);
        //获取多条数据
        System.out.println("========================================");
        List<String> list = new ArrayList<>();
        list.add("goodsName");
        list.add("goodsPrice");
        list.add("goodsId");
        list.add("goodsProdu");

        List<String> listKeys = valueStr.multiGet(list);
        for (String key : listKeys) {
            System.out.println(key);
        }


    }

}
