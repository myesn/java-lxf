package com.company.myesn;

import com.company.myesn.annotation.Range;
import com.company.myesn.annotation.Report;
import com.company.myesn.annotation.ReportContainer;
import com.company.myesn.entity.Hi;
import com.sun.istack.internal.NotNull;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException {
        // 使用注解(Annotation)
        // 注解是放在 java 源码的类、方法、字段、参数前的一种特殊“注释”
        // 注解是一种用作标注的“元数据”
        // java 使用 @interface 定义注解，必须要设置 @Target 和 @Retention(RetentionPolice.RUNTIME)
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1265102413966176


        /*
         * 从 jvm 的角度看，注解本身对代码逻辑没有任何影响，如何使用注解完全由工具决定
         *
         * java 注解可分为三大类：
         * 1. 由编译器使用的注解(这类注解不会被编译进 .class 文件，它们在编译后就被编译器扔掉了)
         *  @Override 令编译器检查该方法时候正确地实现了覆写
         *  @SuppressWarnings 告诉编译器忽略此处代码产生的警告
         *
         * 2. 由工具处理 .class 文件使用的注解，比如有些工具会在加载 class 的时候，对 class 做动态修改，实现一些特殊的功能
         * 这类注解会被编译进 .class 文件，当加载结束后并不会存在于内存中。这类注解只被一些底层库使用，一般我们不必自己处理。
         *
         * 3. 在程序运行时能够读取的注解，它们在加载后一直存在 jvm 中，这也是最常用的注解。
         * 例如：一个配置了 @PostConstruct　的方法会在调用构造方法后自动被调用（这是　java 代码读取该注解实现的功能， jvm 并不会识别该注解）
         *
         *
         * 如果注解的参数是 value，且只有一个参数，那么可以省略参数名称
         *
         * 元注解：
         *  有一些注解可以修饰其他注解，这些注解就称为元注解（meta annotation）。
         *  java 标准库已经定义了一些元注解，通常不需要自己取编写元注解
         *
         * @Target
         *  最常用的元注解是 @Target。使用 @Target 可以定义 Annotation 能够被应用于源码的哪些位置：
         *      类或接口:   ElementType.TYPE
         *      字段:      ElementType.FIELD
         *      方法：     ElementType.METHOD
         *      构造方法:   ElementType.CONSTRUCTOR
         *      方法参数：  ElementType.PARAMETER
         *      ... 还有很多
         *
         * @Retention
         *  另一个重要的元注解 @Retention 定义了 Annotation 的生命周期。
         *  如果 @Retention 不存在，则该 Annotation 默认为 CLASS。
         *  通常我们自定义的 Annotation 都是 RUNTIME，所以务必要加上 @Retention(RetentionPolicy.RUNTIME) 这个元注解
         *      仅编译期:        RetentionPolicy.SOURCE
         *      仅 class 文件:  RetentionPolicy.CLASS
         *      运行期:         RetentionPolicy.RUNTIME
         *
         * @Repeatable
         *  使用 @Repeatable 这个元注解可以定义 Annotation 是否可重复。这个注解应用不是特别广泛
         *  经过 @Repeatable 修饰后，在某个类型声明处，就可以添加多个 @Report（自定义的注解） 注解
         *  定义重复注解需要两个类，一个是注解类，一个是注解的容器类
         *
         * @Inherited
         *  使用 @Inherited 定义子类时候可继承父类定义的 Annotation。
         *  @Inherited 仅针对 @Target(ElementType.TYPE) 类型的 Annotation 有效，并且仅针对 class 的继承，对 interface 的继承无效
         */

        /*
         * 处理注解
         * https://www.liaoxuefeng.com/wiki/1252599548343744/1265102026065728
         *
         * 判断某个注解是否存在于 Class/Field/Method/Constructor
         * Class/Field/Method/Constructor.isAnnotationPresent(Class)
         *
         * 读取 Annotation
         * Class/Field/Method/Constructor.getAnnotation(Class)
         */

        // 所有的 Annotations
        System.out.println(Arrays.toString(Hi.class.getAnnotations()));

        // 判断 Annotation 是否存在
        // @Repeatable 修饰的 @Report，在使用后，最终编译为 @ReportContainer({@Report("hi"), @Report("hi-class")})
        // 所以这里只能判断 xxContainer 而不是 xx
        System.out.println(Hi.class.isAnnotationPresent(ReportContainer.class));

        // 读取 Annotation
        ReportContainer reportContainer = Hi.class.getAnnotation(ReportContainer.class);
        if (reportContainer != null) {
            System.out.println(Arrays.toString(reportContainer.value()));
        }

        // 读取方法参数的 Annotation 比较麻烦一点，因为方法参数本身可以看成一个数组，而每个参数又可以定义多个注解
        // 所以一次获取方法参数的所有注解就必须用一个二维数组来表示
        Method hiMethod = Main.class.getMethod("hi", String.class, String.class);
        Annotation[][] hiMethodParameterAnnotations = hiMethod.getParameterAnnotations();// 只能获取 RUNTIME 的 Annotation
        System.out.println(Arrays.toString(hiMethodParameterAnnotations));
        for (Annotation[] annotations : hiMethodParameterAnnotations) {
            for (Annotation annotation : annotations) {
                if (annotation instanceof NotNull) { // NotNull 是 RetentionPolicy.CLASS，这里获取不到
                    NotNull notNull = (NotNull) annotation;
                    System.out.println("notNull");
                } else if (annotation instanceof Range) {
                    Range range = (Range) annotation;
                    System.out.println(String.format("range min %s max %s", range.min(), range.max()));
                }
            }
        }

        // 检查 Hi 的实例是否符合注解要求
        Hi hi = new Hi("myesn", "chengdu",18);
        checkHiClass(hi);
        System.out.println("hi is valid");
    }

    static void checkHiClass(Hi hi) throws IllegalAccessException, IllegalArgumentException {
        for (Field field : hi.getClass().getFields()) {
            Range range = field.getAnnotation(Range.class);
            if (range == null) {
                return;
            }

            // 获取字段的值
            Object value = field.get(hi);
            if (value instanceof String) {
                String str = (String) value;

                if (str.length() < range.min()) {
                    throw new IllegalArgumentException(String.format("invalid field: %s is less than %s", field.getName(), range.min()));
                }

                if (str.length() > range.max()) {
                    throw new IllegalArgumentException(String.format("invalid field: %s is greater than %s", field.getName(), range.max()));
                }
            }else if(value instanceof Integer) {
                int integer = (Integer) value;
                if (integer < range.min()) {
                    throw new IllegalArgumentException(String.format("invalid field: %s is less than %s", field.getName(), range.min()));
                }

                if (integer > range.max()) {
                    throw new IllegalArgumentException(String.format("invalid field: %s is greater than %s", field.getName(), range.max()));
                }
            }
        }
    }

    public void hi(@NotNull @Range(max = 5) String name, @NotNull String prefix) {
    }
}

