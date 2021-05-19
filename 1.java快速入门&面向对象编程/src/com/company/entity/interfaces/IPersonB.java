package com.company.entity.interfaces;

// 一个 interface 可以继承自另一个 interface, interface 继承自 interface 使用 extends，它相当于扩展了接口的方法
public interface IPersonB extends IPerson {
    String getName();
}
