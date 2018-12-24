package com.zpc.redis.aop;

import com.zpc.redis.annotation.ExtApiIdempotent;
import com.zpc.redis.annotation.ExtApiToken;
import com.zpc.redis.service.RedisTokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * AOP切面
 * 完成2个功能：
 * 1)判断接口方法是否有ExtApiToken注解,如果有自动在HttpServletRequest中添加token字段值
 * 2)判断接口方法是否有ExtApiIdempotent注解,如果有则校验token
 */
@Component
@Aspect
public class ExtApiIdempotentAop {

    @Autowired
    private RedisTokenService tokenService;

    @Pointcut("execution(public * com.zpc.redis.controller.*.*(..))")
    public void myAop() {

    }

    @Before("myAop()")
    public void before(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        ExtApiToken annotation = signature.getMethod().getAnnotation(ExtApiToken.class);
        if (annotation != null) {
            getRequest().setAttribute("token", tokenService.getToken());
        }
    }

    //环绕通知
    @Around("myAop()")
    public Object doBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //判断方法上是否有ExtApiIdempotent注解
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        ExtApiIdempotent declaredAnnotation = signature.getMethod().getDeclaredAnnotation(ExtApiIdempotent.class);
        if (declaredAnnotation != null) {
            String type = declaredAnnotation.type();
            String token = null;
            HttpServletRequest request = getRequest();
            if ("head".equals(type)) {
                token = request.getHeader("token");
            } else {
                token = request.getParameter("token");
            }

            if (StringUtils.isEmpty(token)) {
                return "请求参数错误！";
            }

            boolean tokenOk = tokenService.findToken(token);
            if (!tokenOk) {
                getResponse("请勿重复提交！");
                return null;
            }
           String name = request.getParameter("name");

             boolean name1 = tokenService.findName(name);
                if(!name1){
                    return "该用户名已存在!";
                }
                else{
                    tokenService.setName(name);
                }
        }
        //放行
        return proceedingJoinPoint.proceed();
    }


    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }

    public void getResponse(String msg) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(msg);
        writer.close();
    }
}
