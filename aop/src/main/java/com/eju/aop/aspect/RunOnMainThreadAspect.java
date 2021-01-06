package com.eju.aop.aspect;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RunOnMainThreadAspect {


    private Handler handler=new Handler(Looper.getMainLooper());

    private static final String POINTCUT_METHOD = "execution(@com.eju.aop.annotation.RunOnUIThread * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodWithUIThreadAnnotation() {

    }

    @Around("methodWithUIThreadAnnotation()")
    public void executeOnUiThread(final ProceedingJoinPoint joinPoint) throws Throwable {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

}
