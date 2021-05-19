package com.company.entity;

// 继承 extends
// 注意：子类自动获得父类的所有字段，严禁定义与父类重名的字段
// 在 OOP 的术语中，把 Person 称为超类(super class)、父类(parent class)、基类(base class)
// 把 Student 称为子类(subclass)、扩展类(extended class)

// 没有写 extends 的类，编译器会自动加上 extends Object
// 一个类有且仅有一个父类，只有 Object 特殊，它没有父类
public class Student extends Person {
    private int score;
    // is 关系用继承 extends, has 关系是组合，应该在内部声明字段
    protected Book book;

    // 子类不会继承任何父类的构造方法
    public Student(String name, int score) {
        super(name); // 显示调用父类的构造方法 Person(String)
        this.score = score;
    }

    public int getScore() {
        // super 关键字表示父类
        return super.birth;
    }

    // get
    // set

    // 在继承关系中，子类如果定义了一个与父类方法签名完全相同的方法，被成为覆写(Override)
    // 加上 @Override 注解可以让编译器帮助检查是否进行了正确的覆写，该注解不是必需的
    // 子类不能覆写父类中被 final 修饰的方法
    @Override
    public void run(){

    }
}
