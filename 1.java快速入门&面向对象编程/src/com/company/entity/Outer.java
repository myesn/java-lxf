package com.company.entity;

public class Outer {
    private String name;
    private static String staticField;

    public Outer(String name) {
        this.name = name;
    }

    private static void sayHi(){

    }

    // 匿名类 Anonymous class
    public void asyncHello() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable, " + Outer.this.name);
            }
        };
        new Thread(r).start();
    }

    public class Inner {
        public void hi() {
            // inner class 除了有一个 this 指向自己，还隐含地持有一个 outer class 的实例，可以用 Outer.this 访问/修改这个实例
            System.out.println("hi, " + Outer.this.name);
        }
    }

    // 用 static 修饰的内部类和 inner class 有很大的不同，它不再依附与 Outer 的实例，而是一个完全独立的类
    // 因此无法引用 Outer.this，但它可以访问 Outer 的 private 静态字段和静态方法
    static class StaticInner {
        static void hi() {
            String a =  Outer.staticField;
            Outer.sayHi();
            Outer.StaticInner.hi();
        }
    }
}

