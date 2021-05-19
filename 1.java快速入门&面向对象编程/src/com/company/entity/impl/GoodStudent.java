package com.company.entity.impl;

import com.company.entity.interfaces.IPerson;
import com.company.entity.interfaces.IPersonB;

// 当一个具体的 class 去实现一个 interface 时，需要使用 implements 关键字
// 一个类只能继承自另一个类，不能从多个类继承，但是一个接口可以实现多个 interface
public class GoodStudent implements IPerson, IPersonB {
    private String name;

    // 所有实例共享一个静态字段
    public static int number;

    public GoodStudent(String name) {
        this.name = name;
    }

    public static  void staticMethod(){
        // 因为静态方法属于 class 而不属于实例，因此，静态方法内部无法访问 this 变量
        // 也无法访问实例字段，它只能访问静态字段

    }

    @Override
    public void run() {
        int number = GoodStudent.number;
        GoodStudent.staticMethod();
    }

    @Override
    public String getName() {
        return null;
    }
}
