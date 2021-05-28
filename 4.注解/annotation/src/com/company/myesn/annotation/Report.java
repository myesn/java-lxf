package com.company.myesn.annotation;

import java.lang.annotation.*;

// 使用 @interface 语法定义注解 annotation
// 注解的参数类似无参方法，可以用 default 设定一个默认值（最好）
@Inherited // 仅对 @Target(ElementType.TYPE) 有效
@Repeatable(ReportContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE}) // 定义注解 @Report 可用在方法或字段上, 只有一个元素时可以省略数组的写法
public @interface Report {
    int type() default 0;

    String level() default "info";

    String value() default ""; // 最常用的参数应当命名为 value
}