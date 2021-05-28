package com.company.myesn.entity;

import com.company.myesn.annotation.Range;
import com.company.myesn.annotation.Report;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Report("hi")
@Report("hi-class")
@Resource(name = "hello")
public class Hi {
    //    @Inject
    @Report("c")
    @Report("b")
    @Report("a")
    int n;

    @Range(min = 1, max = 20)
    public String name;

    @Range(max = 10)
    public String city;

    @Range(min = 18, max = 100)
    public Integer age;

    public Hi() {
    }

    public Hi(String name, String city, Integer age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

    @PostConstruct
    public void hello(/* @Param String name */) {
        System.out.println("hi");
    }

    @Override
    public String toString() {
        return "hi";
    }

}
