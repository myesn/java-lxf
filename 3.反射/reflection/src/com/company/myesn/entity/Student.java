package com.company.myesn.entity;

public class Student extends Person {
    public int score;
    private int grade;

    public Student(int id) {
        super(id);
    }

    private void s1(String a){
        System.out.println("student.private.s1 " + a);
    }

    public void s2(int a){
        System.out.println("student.public.s2");
    }

    public void hi(){
        System.out.println("student.hi");
    }
}
