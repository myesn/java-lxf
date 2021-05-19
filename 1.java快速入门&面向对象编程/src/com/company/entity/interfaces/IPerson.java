package com.company.entity.interfaces;

// 如果一个抽象类没有字段，所有方法全部都是抽象方法，那么可以把该抽象类改写为接口 interface
public interface IPerson {

    // interface 可以有静态字段，并且静态字段必须为 final 类型
    // 实际上，因为 interface 的字段只能是 public static final 类型，所以我们可以把这些修饰符都去掉
//    public static final int MALE = 1;
    // 编译器会自动加上 public static final
    int MALE =1;

    // 接口定义的所有方法默认都是 public abstract 的，所以这两个修饰符不需要写出来（写不写效果都是一样的）
    void run();

    // 在接口中，可以定义 default 方法（jdk>=1.8)
    // 实现类可以不必覆写 default 方法
    default void run2(){
        System.out.println("default method of interface");
    }
}
