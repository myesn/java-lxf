package com.company;

import com.company.entity.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.xml.XMLConstants;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IntrospectionException {
        System.out.println("hi");
        /**
         * 基本数据类型
         * 整数类型：byte, short, int, long
         * 浮点数类型：float, double
         * 字符类型：char
         * 布尔类型：boolean
         *
         * 引用类型
         * 除了上诉的基本数据类型，剩下的都是引用类型
         *
         * 定义变量的时候加上 final 修饰符，这个变量就变成了常量
         */
        StringBuilder sb = new StringBuilder();
//        var sb2 = new StringBuilder();

        // 整数的数值表示不但是精确的，而且整数运算永远是精确的，即使是除法也是精确的，
        // 因为两个整数相除只能得到结果的整数部分
        // attention: 整数的除法对于除数为 0 时运行时将报错，但编译不会报错
        int x = 12345 / 67;
        int y = 123456 % 67;
        int z = 9 / 2; // 结果为4.5，java 会直接舍弃小数部分取整数部分
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);

        // 移位运算，左移 1 << 2 等于 1 * 2 * 2 = 4, 右移 8 >> 2 等于 8 / 2 / 2 = 2
        // 仔细观察可发现，左移实际上就是不断地 * 2，右移实际上就是不断地 / 2

        // 位运算
        // 位运算是按位进行与、或、非、异或的运算
        // 1. 与运算的规则是，必须两个数同时为 1，结果才为 1：0 & 0 = 0; 0 & 1 = 0; 1 & 0 = 0; 1 & 1 = 1;
        // 2. 或运算的规则是，只要任意一个为 1，结果就为 1： 0 | 0 = 0, 0 | 1 = 1; 1 | 0 = 1; 1 | 1 = 1;
        // 3. 非运算的规则是，0 和 1 互换：~0 = 1; ~1 = 0;
        // 4. 异或运算的规则是，如果两个数不同，结果为 1，否则为 0：0 ^ 0 = 0; 0 ^ 1 = 1; 1 ^ 0 = 1; 1 ^ 1 = 0;

        // 由于浮点数存在运算误差，所以比较两个浮点数是否相等常常会出现错误的结果。正确的比较方法是判断两个浮点数之差的绝对值是否小于一个很小的数：
        double r = Math.abs(10.1 - 9.2);
        if (r < 0.00001) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }

        // 如果参与运算的两个数其中一个是整型（必须是其中一个，如果是两个就不会自动提升），那么整型可以自动提升到浮点型
        double d = 1.2 + 24 / 5; // 24 /5 = 4，最终等于 5.2，因为是两个整数进行运算，就无法自动提升到浮点型

        // 溢出
        // 整数运算在除数为 0 时会报错，而浮点数运算在除数为 0 时不会报错，但会返回几个特殊值：
        // NaN = Not a Number
        // Infinity = 正无穷大
        // -Infinity = 负无穷大

        // 强制转型
        // 可以将浮点数强制转型为整数，在转型时，浮点数的小数部分会被丢掉
        int n1 = (int) 12.7; // 12

        // char 占用两个字节
        // 要显示一个字符的 Unicode 编码（十进制），只需将 char 类型直接赋值给 int 类型即可
        char name = 'A';
        int nameUnicode = name;
        System.out.println(nameUnicode); // 65
        // 还可以直接用转义字符 u + Unicode 编码 来表示一个字符
        // 我的吗鸭， java 允许在注释中使用 u 开头的 Unicode 转义字符，上面的注释会报错，
        // 吗的上面也会报错，记得 u 前面要加一个 \ 符号转义，由于加了会被转义，所以上面两行都差一个 \ 符号
        // 因为 javac 编译器会先解释 .java 文件中的 Unicode 转义，然后再编译，上面的不合法，所以编译会报错哦
        // 注意是十六进制
        char c3 = '\u0041'; // 'A', 因为十六进制 0041 = 十进制 65

        // 多行字符串
        // 从 java 13 开始，字符串可以用 """fds""" 表示多行字符串
        // 打印出来的结果也会有换行符，每个换行都会在结尾加上一个 \n 换行符
        // 还需要注意到，多行字符串前面共同的空格会被去掉
        // 如果多行字符串的排版不规则，那么总是以最短的行首空格为基准，删除其他行的空格
//        String s = """
//                 select *  这里会多出一个空格，因为下面一行行首的空格最少，所以是以下面一行行首的空格为基准删除其他行的行首空格
//                from users
//                 """;
//        System.out.println(s);

        // java 的字符串除了是一个引用类型外，还有个重要特点，就是字符串不可变
        // 字符串 s1 没有变，变的是变量 s1 的 “指向”，即引用地址，原来的字符串 “hello” 还在，只是无法通过变量 s1 访问它而已
        // 因此，字符串的不可变是指字符串内容不可变
        String s1 = "hello";
        System.out.println(s1); // 显示 hello
        s1 = "world";
        System.out.println(s1); // 显示 world

        // 数组一旦创建后，大小就不可改变;
        //   解释：因为变量存储的是引用类型的“指向”，所以就算 ns 数组 重新赋值后，也不会改变原有的数组，只是变了“指向”
        //   原有的 5 个元素的数组并没有改变，只是无法通过变量 ns 访问它而已，就像 String 一样，即所有引用类型都是如此
        // 数组所有元素初始化为对应类型的默认值;
        // 数组是引用类型，在使用数组访问数组元素时，如果索引超出返回，运行时会报错
        int[] ns = new int[5];
        int[] ns2 = new int[]{1, 2, 3};
        int[] ns3 = {1, 2, 3};
        ns[0] = 1; // ...
        int a = ns.length;

        // 这个数组实际上存储了 3 个引用类型的“指向”，
        // names[1] = "2" 的行为会修改指向即在堆中创建一个新的内存空间用来存储新的字符串值，而原 names[1] 指向中的内容并不会改变，只是无法再被访问到了
        String[] names = {"a", "b", "c"};
        names[1] = "2";

        // 格式化输出，占位符
        // https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Formatter.html#syntax
        Double d2 = 12900000.91;
        System.out.println(d2); // 1.29E7
        System.out.printf("%.2f", d2); // 显示两位小数

        // 输入
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("\nInput your name: ");
//        String yourName = scanner.nextLine();
//        System.out.print("Input your age: ");
//        int age = scanner.nextInt();
//        System.out.printf("Hi, %s, you are %d\n", yourName, age);

        // java 中判断两个值类型浮点数是否相同，不能使用 == 判断，因为浮点数在计算机中常常无法精确表示
        // 正确的方法是利用差值小于某个临界值来判断
        double left = 0.3 - 0.2;
        double right = 0.1;
        System.out.println(left == right);// false
        System.out.println(Math.abs(left - right) < 0.00001); // true

        // 判断引用类型的变量是否相等， == 表示 “引用是否相等”，或者说，是否指向同一个对象
        String o1 = "hello";
        String o2 = "Hello".toLowerCase();
        System.out.println(o1 == o2);
        System.out.println(o1 != null && o1.equals(o2)); // 如果 o1 为 null 就会报错，所以需要在之前使用短路运算符

        // switch 匹配 String 时，是比较“内容相等”
        switch (o1 + "1") {
            case "hello":
                System.out.println(o1);
                break;
            default:
                System.out.println("switch default");
                break;
        }
        // 从 java 12 开始，switch 语句升级为更简洁的表达式语法，使用类似模式匹配（Pattern Matching）的方法
        // 保证只有一种路径会被执行，并且不需要 break 语句
//        switch (o1) {
//            case "apple" -> System.out.println("selected apple");
//            case "hello" -> {
//                System.out.println("selected hello");
//            }
//            default -> System.out.println("default");
//        }
        // 使用 switch 表达式语法给变量赋值
//        int opt = switch (o1) {
//            case "apple" -> 1;
//            case "hello", "hello1" -> {
//                int code = o1.hashCode();
//                yield code; // switch 语句返回值，使用 yield 返回一个值作为 switch 语句的返回值
//            }
//            default -> 0;
//        };
//        System.out.println("opt: " + opt);

        int[] nsArray = {1, 4, 9, 16, 25};
        for (int i = 0; i < nsArray.length; i++) {
            System.out.println(nsArray[i]);
        }
        for (int item : nsArray) {
            System.out.println(item);
        }
        System.out.println(nsArray);
        // java 标准库提供了 Arrays.toString(), 可以快速打印数组内容
        System.out.println(Arrays.toString(nsArray));

        // 冒泡排序
        // 特点：每一轮循环结束后，最大的一个数被交换到末尾，因此，下一轮循环就可以“刨除”最后的数，每一轮循环都比上一轮循环的结束位置靠前一位
        nsArray = new int[]{28, 12, 89, 73, 65, 18, 96, 50, 8, 36};
        System.out.println(Arrays.toString(nsArray));
        for (int i = 0; i < nsArray.length - 1; i++) {
            for (int j = 0; j < nsArray.length - i - 1; j++) {
                if (nsArray[j] > nsArray[j + 1]) {
                    int temp = nsArray[j];
                    nsArray[j] = nsArray[j + 1];
                    nsArray[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(nsArray));
        // java 标准库内置排序功能, jdk 提供的 Arrays.sort(), 此函数会修改数组本身
        nsArray = new int[]{28, 12, 89, 73, 65, 18, 96, 50, 8, 36};
        Arrays.sort(nsArray);
        System.out.println(Arrays.toString(nsArray));

        // 二维数组
        // 使用 jdk 提供的 Arrays.deepToString() 打印一个二维数组
        int[][] ns4 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        System.out.println(Arrays.deepToString(ns4));

        // 从命令行接收参数
        for (String arg :
                args) {
            if ("-version".equals(arg)) {
                System.out.println("v 1.0");
                break;
            }
        }

//        Person ming = new Person();
////        ming.name = "xiao ming";
////        ming.age = 12;
//        ming.setName("  x  ");
////        ming.setAge(18);
//        ming.setBirth(1930);

        Group group = new Group();
        group.setNames("a", "b", "c");
        // 向可变参数传入 0 个参数时，接收到的实际值是一个空数组而不是 null
        // 但如果是 String[] names，那么调用方就可以传入 null
        group.setNames();

        // 向上转型(upcasting)，即子类转为父类类型
        Person p1 = new Student("a", 1);
        // 向下转型(downcasting)，即父类转为子类类型，最好借助 instanceof 判断
        Person p2 = new Student("a", 1);
        // instanceof 判断一个变量所指向的实例是否是指定类型，或者是这个类型的子类
        // 如果一个引用变量为 null, 那么对任务 instanceof 的判断都为 false
        boolean isPersonType = p2 instanceof Person; // true
        boolean isStudentType = p2 instanceof Student; // false
        if (isStudentType) {
            Student s99 = (Student) p2;
        }
        // 从 java 14 开始，判断 instanceof 后，可以直接转型为指定变量，避免再次强制转型
//        if (p2 instanceof Student student) {
//        }

        // 多态是指，针对某个类型的方法调用，其真正执行的方法取决于运行时期实际类型的方法
        // p4 在运行时的实际类型是 Student，所以 p4.run() 调用的是 Student 中覆写的方法
        Person p4 = new Student("123", 1);
        p4.run();

        List list = new ArrayList();

        // 内部类
        // inner class 与普通类有个最大的不同，就是 inner class 的实例不能单独存在，必须依附与一个 outer class 的实例
        Outer outer = new Outer("nested class");
        Outer.Inner inner = outer.new Inner();
        inner.hi();
        // 匿名类 Anonymous class
        outer.asyncHello(); // 通过线程执行的异步函数
        HashMap<String, String> map1 = new HashMap<>(); // 普通的 HashMap 实例
        HashMap<String, String> map2 = new HashMap<String, String>() {
        }; // 继承自 HashMap 的匿名类实例
        HashMap<String, String> map3 = new HashMap<String, String>() { // 继承自 HashMap 的匿名类实例，并且添加了 static 代码块来初始化数据
            {
                put("A", "1");
                put("B", "2");
            }
        };
        System.out.println(map3.get("A"));

        System.out.println(XMLConstants.XML_NS_PREFIX);

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1260576204194144
        boolean a1 = "a".equals("a");
        boolean a2 = "a".equalsIgnoreCase("a");
        // java 的 String 和 char 在内存中总是以 Unicode 编码表示
        byte[] utf8Words = "afda".getBytes(StandardCharsets.UTF_8); // 手动将字符串转换为其他编码
        String s2 = new String(utf8Words, StandardCharsets.UTF_8); // 将已知编码的 byte[] 转为 String

        StringBuilder sb3 = new StringBuilder(1024); // 高效拼接字符串，支持链式调用
        StringJoiner sj = new StringJoiner(",", "prefix", "suffix"); // 指定分隔符拼接字符串数组，并指定开头和结尾
        String.join(",", new String[]{"a", "b"}); // 在不需要指定开头和结尾的时候，使用 String.join 更方便

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1260473794166400
        // 包装类型的比较必须使用 equals，基本类型才能用 ==
        // 整数和浮点数的包装类型都继承自 Number
        Integer i = Integer.valueOf(1); // 较小的数会从缓存中取出一个相同值的实例，并返回，较大的数会返回一个新的实例
        Integer i2 = Integer.valueOf(1);
        i.equals(i2);

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1260474416351680
        // JavaBean 是一种符合命名规范的 class，它通过 getter 和 setter 来定义属性
        // 枚举 JavaBean 属性
        BeanInfo beanInfo = Introspector.getBeanInfo(Student.class);
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            System.out.println(descriptor.getName());
            System.out.println("  " + descriptor.getReadMethod());
            System.out.println("  " + descriptor.getWriteMethod());
        }

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1260473188087424
        // 用 static final 来定义常量
        // enum 虽然是引用类型，单比较时可以使用 ==，因为 enum 类型的每个常量在 JVM 中只有一个唯一实例
        // enum.equals 内部也是使用的 == 进行比较
        Weekday weekday = Weekday.MON;
        String weekdayName = weekday.name(); //MON 返回常量名
        int weekdayOrdinal = weekday.ordinal();// 1 返回定义的常量的顺序，从0开始计数
        int weekdayDayValue = weekday.dayValue;
        // 内部重写了 toString 方法，返回 this.chinese
        // 覆写 toString() 的目的是在输出是更有可读性
        System.out.println(weekday.toString());

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1331429187256353
        // java 14 新增的 record 关键字，see com.company.entity.Point

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1279767986831393
        // java.math.BigInteger 用来表示任意大小的整数，内部用一个 int[] 数组来模拟一个非常大的整数
        // 是不变类，继承自 Number
        // 对 BigInteger 做运算时，只能用实例方法
        // 和 long 型整数运算比，BigInteger 不会有范围限制，但缺点是数度比较慢
        // 内部定义了转为其他数值类型的实例方法，转换时使用 xxValueExact()，超出返回则会报错
        // 如果 BigInteger 超出了 float 的最大范围，返回的 float 值是 Infinity
        BigInteger bigInteger = BigInteger.ZERO;
        BigInteger bi2 = new BigInteger("5464849494948484845444494949");
        System.out.println(bi2.pow(5));
        BigInteger sum = bigInteger.add(bi2);
        long long1 = sum.longValue();
//        long long2 =  sum.longValueExact(); // 该方法在转换为 long 时，如果超出 long 型范围，会抛出 ArithmeticException

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1279768011997217
        // java.math.BigDecimal 和 BigInteger 类似，BigDecimal 可以表示一个任意大小且精度完全准确的浮点数
        // 常用于财务计算
        // 比较两个 BigDecimal 时，必须使用 compareTo()
        BigDecimal bd1 = new BigDecimal("123.4567");
        bd1.multiply(bd1); // 乘法
        // 用 scale() 返回浮点数的小数位数个数
        System.out.println(bd1.scale()); // 4，代表有 4 位小数
        // 通过 stripTrailingZeros() 方法，可以将一个 BigDecimal 格式化位一个相等的，但去掉了末尾 0 的 BigDecimal
        BigDecimal bd2 = new BigDecimal("123.4567800"); // 123.45600
        BigDecimal bd3 = bd2.stripTrailingZeros(); // 123.456
        System.out.println(bd2.scale()); // 5
        System.out.println(bd3.scale()); // 3
        BigDecimal bd4 = new BigDecimal("1234500");
        System.out.println(bd4.stripTrailingZeros().scale());// -2，如果返回负数，表示这个数是一个整数，-2代表末尾有2个0
        // 可以对一个 BigDecimal 设置它的 scale，如果精度比原始值低，那么按照指定的方法进行四舍五入或者直接截断
        System.out.println(bd3.setScale(4, RoundingMode.HALF_UP)); // 四舍五入，保留4位小数
        System.out.println(bd3.setScale(4, RoundingMode.DOWN)); // 直接截断，保留4位小数
        // 对 BigDecimal 做加、减、乘时，精度不会丢失，但做除法时，存在无法除尽的情况，这时，就必须指定精度以及如何进行截断
        // 否则可能会抛出 ArithmeticException, 因为除不尽
        System.out.println(bd2.divide(bd1, 10, RoundingMode.HALF_UP)); // 保留10位小数并四舍五入
        // 还可以对 BigDecimal 做除法的同时求余数
        BigDecimal[] dr = bd2.divideAndRemainder(bd1);
        System.out.println(dr[0]); // 商，总是整数
        System.out.println(dr[1]); // 余数，总是不会大于除数
        if (dr[1].signum() == 0) {
            // 证明两个 BigDecimal 是整数倍数，即没有余数
        }
        // 比较值时候相等，使用 equals() 方法，它可以要求两个 BigDecimal 的值相等，还要求它们的 scale() 相等
        // 但必须使用 compareTo() 方法，它根据两个值的大小分别返回负数、正数和0，分别代表小于、大于和等于
        // 注意：总是使用 compareTo() 方法比较两个 BigDecimal　的值
        System.out.println(bd1.equals(bd2)); // false
        System.out.println(bd1.compareTo(bd2)); // -1，代表 bd1 < bd2
        // 实际上一个 BigDecimal 是通过一个 BigInteger 和 scale 来表示的
        // 即 BigInteger 表示一个完整的整数，而 scale 表示小数位数
        // BigDecimal 也是从 Number 继承的，也是不可变对象

        // https://www.liaoxuefeng.com/wiki/1252599548343744/1260473555087392
        // 常用工具类
        // Math: 用来进行数学计算，提供了大量静态方法
        // 求绝对值
        Math.abs(-100); // 100
        // 取 max or min
        Math.max(100, 90); // 100
        Math.min(100, 80); // 80
        // 计算x的y次方
        Math.pow(2, 10); // 2的10次方=1024
        // 开平方根
        Math.sqrt(2); // 1.414...
        // 计算 e x(这个在e的右上角) 次方 (指数)
        Math.exp(2); // 7.389
        // 计算以e为底的对数
        Math.log(4);// 1.386
        // 计算以 10 为底的对数
        Math.log10(100); // 2
        // 三角函数
        Math.sin(3.14); // 0.00159...
        Math.cos(3.14); // -0.9999...
        Math.tan(3.14); // -0.0015...
        Math.asin(1.0); // 1.57079...
        Math.acos(1.0); // 0.0
        // 还提供了一些数学常量
        double pi = Math.PI;
        double e = Math.E;
        // 生成一个随机数 x，x的范围是 0 <= x <1
        System.out.println(Math.random()); // 0.xxx
        // 生成一个区间值在[min, max]的随机数
        double x1 = Math.random(); // x的范围是[0,1)
        double min = 10;
        double max = 50;
        double y1 = x1 * (max - min) + min; // y的范围是[10,50)
        long n = (long) y1; // n的范围是[10,50)的整数
        System.out.println(y1);
        System.out.println(n);
        // java 标准库还提供了一个 StrictMath，它提供了和 Math 几乎一模一样的方法
        // 区别：由于浮点数计算存在误差，不同的平台（x86、arm）计算的结果可能不一致（指误差不同）
        //      因此，StrictMath 保证所有平台的计算结果都是完全相同的，而 Math 会尽量针对平台优化计算速度
        //      所以，绝大多数情况下，使用 Math 就足够了
        // Random 用来创建伪随机数
        // 伪随机数，是指只要给定一个初始的种子，产生的随机数序列是完全一样的
        Random rd =  new Random(12345); // 指定种子，就会得到完全确定的随机数序列，即每次输出的内容相同
        System.out.println(rd.nextInt(100)); // 生成一个 0-10 之间的 Int
        // SecureRandom 用来创建真随机数
        // 用来创建一个不可预测的安全的随机数
        // 它的安全性是通过操作系统提供的安全的随机种子来生成随机数。这个种子是通过CPU的热噪声、读写磁盘的字节、网络流量等各种随机事件产生的“熵”。
        // 时刻牢记：必须使用 SecureRandom 来产生安全的随机数
        SecureRandom sr = new SecureRandom(); // 无法指定种子
        System.out.println(sr.nextInt(100));
        // 在实际使用的时候，可以优先获取高强度的安全随机数生成器，如果没有提供，再使用普通等级的安全随机生成器
        SecureRandom sr2 = null;
        try {
            // 获取高强度安全随机数生成器
            sr2 = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // 获取普通的安全随机数生成器
            sr2 = new SecureRandom();
        }
        byte[] buffer = new byte[16];
        sr.nextBytes(buffer); // 用安全随机数填充 buffer
        System.out.println(Arrays.toString(buffer));
    }
}
