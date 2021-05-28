package com.company.myesn;

import com.company.myesn.entity.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 泛型
// https://www.liaoxuefeng.com/wiki/1252599548343744/1255945193293888
public class Main {

    // 什么是泛型
    // https://www.liaoxuefeng.com/wiki/1252599548343744/1265102638843296
    public static void main1(String[] args) {
        /*
         * 泛型是一种“代码模版”，可以用一套代码套用各种类型
         * 泛型就是编写模版代码来适应任意类型
         * 泛型的好处是不必对类型进行强制转换，它通过编译器对类型进行检查
         */
        final List<String> strList = new ArrayList<String>();
        strList.add("myesn");
        final String name = strList.get(0);
    }

    // 使用泛型
    // https://www.liaoxuefeng.com/wiki/1252599548343744/1265103567584000
    public static void main(String[] args) {
        List<Number> list = new ArrayList<>(); // 编译器自动推断出 ArrayList<T> 的泛型类型是 Number，所以这里可以用简写
        String[] strings = new String[]{"Orange", "Apple", "Pear"};
        Arrays.sort(strings); //  排序的数组元素必须实现 Comparable<T> 这个泛型接口
        System.out.println(Arrays.toString(strings));

        Person[] people = new Person[]{
                new Person("myesn", 23),
                new Person("bob", 18)
        };
        Arrays.sort(people); // Person bean 需要实现 Comparable<Person> 接口，否则会抛出 ClassCastException
        System.out.println(Arrays.toString(people));

    }
}
