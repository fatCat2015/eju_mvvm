package com.eju.aop.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UploadEvent {

    /**
     * 事件名称 eventCode
     * @return
     */
    String value() ;

    /**
     * 事件类型 event
     * @return
     */
    String event() default "";

}
