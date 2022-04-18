package com.hy.demo.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hy.demo.common.GlobalConstant;
import com.hy.demo.config.AuthAccess;
import com.hy.demo.entity.User;
import com.hy.demo.exception.ServiceException;
import com.hy.demo.entity.Controller;
import com.hy.demo.service.ControllerService;
import com.hy.demo.service.UserService;
import com.hy.demo.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor  implements HandlerInterceptor {

    @Autowired
    private ControllerService controllerService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

//        // 如果不是映射到方法直接通过
//        if(!(handler instanceof HandlerMethod)){
//            return true;
//        }
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        } else {
            HandlerMethod h = (HandlerMethod) handler;
            AuthAccess authAccess = h.getMethodAnnotation(AuthAccess.class);
            if (authAccess != null) {
                return true;
            }
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("402", "无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId = null;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            redisUtils.del(GlobalConstant.REDIS_KEY_TOKEN+userId);
            throw new ServiceException("402", "token验证失败，请重新登录");
        }
        // 根据token中的controllerid查询数据库
       // Controller controller = controllerService.getById(userId);
        User user = userService.getById(userId);
        if (user == null) {
            redisUtils.del(GlobalConstant.REDIS_KEY_TOKEN+userId);
            throw new ServiceException("402", "用户不存在，请重新登录");
        }
        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUpassword())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            //token时间过期
            //可以继续token连续续命2个小时



            throw new ServiceException("402","token验证失败，请重新登录");
        }
        return true;
    }
}
