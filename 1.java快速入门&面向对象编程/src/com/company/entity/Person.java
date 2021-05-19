package com.company.entity;

// 抽象类无法被实例化
public abstract class Person {
    // 为了让子类能访问该字段，需要将修饰符改为 protected，子类将无法访问 private 的字段和方法
    protected String name = "myesn";
    //    private int age;
    protected int birth;

    public Person() {
        this("Unnamed");
    }

    public Person(String name) {
        this(name, 18);
    }

    public Person(String name, int birth) {
        setName(name);
        setBirth(birth);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
//        java 11 才有 "".isBlank()
//        if (name == null || name.isBlank()) {
//            throw new IllegalArgumentException("invalid name");
//        }
//
//        this.name = name.strip(); // 去掉首尾空格
    }

    public int getAge() {
//        return this.age;
        return calcAge(2021);
    }

//    public void setAge(int age){
//        if(age < 0 || age > 100) {
//            throw new IllegalArgumentException("invalid age value");
//        }
//
//        this.age = age;
//    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    // 抽象方法（没有方法语句）
    public abstract void run ();


    private int calcAge(int currentYear) {
        return currentYear - this.birth;
    }
}
