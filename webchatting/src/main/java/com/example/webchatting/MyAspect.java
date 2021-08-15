package com.example.webchatting;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

//    @Pointcut("execution(void com.example.webchatting.MyHandler.chooseFriend(..))")
//    public void test(){};
//
//    @Before("test()")
//    public void before(){
//        System.out.println("aspect before run:前置通知");
//        System.out.println("8002接收请求");
//    }
}
