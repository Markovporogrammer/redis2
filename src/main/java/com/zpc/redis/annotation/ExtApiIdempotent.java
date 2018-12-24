package com.zpc.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识一个接口是否需要校验token，type取值为head/form
 * head:表示客户端token放在请求头中
 * form:表示客户端token放在表单中
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExtApiIdempotent {
    String type();
}
