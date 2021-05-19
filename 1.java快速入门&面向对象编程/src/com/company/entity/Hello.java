package com.company.entity;

// 方法重载 Overload: 指多个方法的方法名相同，但各自的参数不同
// 注意方法重载的返回值通常是相同的
public class Hello {
    public void hello() {
        String s = "tfdsafsad";
        int n1 = s.indexOf('t');
        int n2 = s.indexOf("s");
        int n3 = s.indexOf("sa", 4);
    }

    public void hello(String name) {

    }

    public void hello(String name, int age) {

    }
}
