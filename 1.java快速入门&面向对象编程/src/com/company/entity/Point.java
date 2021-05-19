package com.company.entity;

// https://www.liaoxuefeng.com/wiki/1252599548343744/1331429187256353
// 不变类
// 为了包装不变类的比较，还需要覆写 equals 和 hashCode 方法，这样才能在集合类中正常使用
// 从 java 14 开始，有 record 关键字，可以一行写出不变类
// 除了用final修饰class以及每个字段外，编译器还自动为我们创建了构造方法，和字段名同名的方法，以及覆写toString()、equals()和hashCode()方法。
// public record Point(int x, int y) {}
// 如果需要检查参数
/*
*  public Point {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException();
        }
    }
    *
    *
    作为record的Point仍然可以添加静态方法。一种常用的静态方法是of()方法，用来创建Point：
    public record Point(int x, int y) {
        public static Point of() {
            return new Point(0, 0);
        }
        public static Point of(int x, int y) {
            return new Point(x, y);
        }
    }
    这样我们可以写出更简洁的代码：

    var z = Point.of();
    var p = Point.of(123, 456);
* */
public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y(){
        return this.y;
    }
}
