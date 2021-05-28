package com.company.myesn;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // 泛型
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1255945193293888

        /*
         * 泛型是一种“代码模版”，可以用一套代码套用各种类型
         * 泛型就是编写模版代码来适应任意类型
         * 泛型的好处是不必对类型进行强制转换，它通过编译器对类型进行检查
         */
        final List<String> strList = new ArrayList<String>();
        strList.add("myesn");
        final String name = strList.get(0);
    }
}
