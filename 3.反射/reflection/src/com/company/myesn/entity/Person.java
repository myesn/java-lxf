package com.company.myesn.entity;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class Person {
    private int id;
    public String name;

    public Person(){}
    public Person(int id) {
        this.id = id;
    }

    private void p1(String a){
        System.out.println("person.private.p1");
    }

    public void p2(int a){
        System.out.println("person.public.p2");
    }

    public void hi(){
        System.out.println("person.hi");
    }
}
