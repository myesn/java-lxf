package com.company.myesn.entity;

import java.lang.reflect.InvocationHandler;

public class HiDynamicProxy implements IHi {
    private InvocationHandler handler;

    public HiDynamicProxy(InvocationHandler handler) {
        this.handler = handler;
    }

    @Override
    public void say(String name) throws Throwable {
        handler.invoke(
                this,
                IHi.class.getMethod("say", String.class),
                new Object[]{name}
        );
    }
}
