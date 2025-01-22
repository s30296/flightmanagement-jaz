package com.example.web.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GlobalErrorHandling {

    @AfterThrowing(pointcut = "execution(* com.example.web..*(..))", throwing = "ex")
    public void handleWebException(Exception ex) {
        System.out.println("Wystapil blad: " + ex.getMessage());
    }
}
