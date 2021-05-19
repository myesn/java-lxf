package com.company.entity;

public class Group {
    private String[] names;

    // 可变参数用 “类型... 变量名” 定于，可变参数相当于数组类型
    public void setNames(String... names) {
        this.names = names;
    }
}
