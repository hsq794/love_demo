package com.hy.demo.config;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hy.demo.entity.Systemlog;
import com.hy.demo.entity.User;
import com.hy.demo.service.ISystemlogService;
import com.hy.demo.service.UserService;
import com.hy.demo.util.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author hsq
 */
@Aspect
@Component
public class SystemLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    private ISystemlogService iSystemlogService;

    @Autowired
    private UserService userService;

    /**
     * Controller层切点
     */
    @Pointcut("@annotation(com.hy.demo.config.SystemControllerLog)")
    public void SystemControllerLog(){

    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作的开始时间
     * @param joinPoint 切点
     * @throws InterruptedException
     */
    @Before("SystemControllerLog()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException{
        logger.info("进入日志切面前置通知!");

    }

   /* @After("SystemControllerLog()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("进入日志切面后置通知!");

    }

    *//**value切入点位置
     * returning 自定义的变量，标识目标方法的返回值,自定义变量名必须和通知方法的形参一样
     * 特点：在目标方法之后执行的,能够获取到目标方法的返回值，可以根据这个返回值做不同的处理
     *//*
    @AfterReturning(value = "SystemControllerLog()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
    }

    *//***
     * 异常通知 记录操作报错日志
     * * @param joinPoint
     * * @param e
     * *//*
    @AfterThrowing(pointcut = "SystemControllerLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.info("进入日志切面异常通知!!");
        logger.info("异常信息:" + e.getMessage());
    }*/

    /**
     * 通知包裹了目标方法，在目标方法调用之前和之后执行自定义的行为
     * ProceedingJoinPoint切入点可以获取切入点方法上的名字、参数、注解和对象
     * @param joinPoint
     */
    @Around( "SystemControllerLog() && @annotation(systemControllerLog)")
    public Result doAfterReturning(ProceedingJoinPoint joinPoint, SystemControllerLog systemControllerLog) throws Throwable {
        logger.info("设置日志信息存储到表中!");
        //joinPoint.proceed() 结果集
        //参数数组
        Object[] args = joinPoint.getArgs();
        //请求参数数据
        String requestJson = JSONUtil.toJsonStr(args);
        //方法名
        String methodName = joinPoint.getSignature().getName();
        //得到request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //得到token
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        User user = userService.getById(userId);
        logger.info("得到用户信息："+user.toString());
        //写入数据库操作日志
        Systemlog systemlog = new Systemlog();
        systemlog.setUserId(user.getUid());
        systemlog.setUserName(user.getUname());
        systemlog.setOperate(systemControllerLog.operate());
        systemlog.setModule(systemControllerLog.module());
        systemlog.setCreateTime(new Date());
        //存入返回的结果集 joinPoint.proceed()
        Result proceed = (Result) joinPoint.proceed();
        systemlog.setResult(JSONUtil.toJsonStr(joinPoint.proceed()));
        //保存
       saveSystemLog(systemlog);

       return proceed;

    }

    /**
     *
     * @param systemlog
     */
    public void saveSystemLog(Systemlog systemlog){

        //保存
        try {
            iSystemlogService.save(systemlog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
