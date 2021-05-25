package com.company.myesn;

import com.company.myesn.entity.IHi;
import com.company.myesn.entity.Person;
import com.company.myesn.entity.Student;
import org.omg.CORBA.OBJ_ADAPTER;

import java.io.Serializable;
import java.lang.reflect.*;
import java.util.Arrays;

public class Main {

    public static final boolean ASSIGNABLE_FROM = Integer.class.isAssignableFrom(Integer.class);

    public static void main1(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        // Reflection
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1255945147512512
        // 以 String 类为例，当 jvm 加载 String 类时，它首先读取 String.class 文件到内存，然后，为 String 类创建一个 Class 实例并关联起来
        // 只能由 jvm 创建 Class 实例，我们自己的 Java 程序是无法创建 Class 实例的。
        // 所以 jvm 持有的每个 Class 实例都指向一个数据类型(class 或 interface)
//        Class cls = new Class(String);
        // 一个 Class 实例包含了该 class(String) 的所有完整信息
        // jvm 为每个加载的 class 创建了 Class 实例，并且在 Class 实例中保存了 class 的所有信息
        // 通过 Class 实例获取 class 信息的方法称为反射(Reflection)

        // 获取一个 class 的 Class 实例
        // 因为 Class 实例在 jvm 中是唯一的，所以，以下三种方法获取的 Class 实例是同一个实例，可以用 == 比较两个 Class 实例
        final Class cls = String.class; // 通过一个 class 的静态变量 class 获取
        final Class cls2 = "fds".getClass(); // 通过实例变量的 getClass() 获取
        final Class cls3 = Class.forName("java.lang.String"); // 通过静态方法传入完整的类名获取
        System.out.println(cls == cls2);
        System.out.println(cls2 == cls3);
        // instanceof 不但匹配指定类型，还匹配指定类型的子类
        // 而用 == 判断 class 实例可以精准的判断数据类型，但不能作子类比较
        // 通常情况下，使用 instanceof 判断数据类型，除非要精准判断时才使用 ==
        System.out.println(cls instanceof Class);

        printClassInfo("".getClass());
        printClassInfo(Runnable.class);
        printClassInfo(java.time.Month.class);
        printClassInfo(String[].class);
        printClassInfo(int.class);

        // 通过 Class 实例创建对应类型的实例
        // 只能调用 public 的无参构造函数，其他都不行
        String str = (String) cls.newInstance();

        // 动态加载
        // jvm 并不是一次性把所有的 class 全部加载到内存，而是第一次需要用到 class 时才加载


    }

    public static void main2(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 访问字段
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264803033837024
        /*
            包括父类, 获取 public 的 field：
        *       getField(name)            根据字段名获取某个 public 的 field
        *       getFields()               获取所有 public 的 field
        *
            不包括父类：
        *       getDeclaredField(name)    根据字段名获取当前类的某个 field
        *       getDeclaredFields()       获取当前类的所有 field
        * */

        Class cls = Student.class;
        // getField 可以获取当前类和父类中所有 public 的 field
        System.out.println(cls.getField("score"));
//        System.out.println(cls.getField("grade")); // 不能获取 private 的 field
//        System.out.println(cls.getField("id")); // 不能获取父类中 private 的 field
        System.out.println(cls.getField("name"));

        // getDeclaredField 只能获取当前类中所有的 field
        System.out.println(cls.getDeclaredField("score"));
        System.out.println(cls.getDeclaredField("grade"));
//        System.out.println(cls.getDeclaredField("id")); // 不能获取父类的 field
//        System.out.println(cls.getDeclaredField("name")); // 不能获取父类的 field

        // 以 String 类的 value 字段为例，反射获取该字段的信息
        final Field stringValueField = String.class.getDeclaredField("value");
        System.out.println(stringValueField.getName());
        System.out.println(stringValueField.getType());
        // 获取方法的修饰符，它是一个 int，不同 bit 表示不同的含义
        final int modifiers = stringValueField.getModifiers();
        System.out.println(Modifier.isFinal(modifiers));
        System.out.println(Modifier.isPublic(modifiers));
        System.out.println(Modifier.isProtected(modifiers));
        System.out.println(Modifier.isPrivate(modifiers));
        System.out.println(Modifier.isStatic(modifiers));
        // 获取字段值
        final Object person = new Person(8655);
        final Field personIdField = person.getClass().getDeclaredField("id");
        // 默认不能获取 private/protected field 的值（即非 public 字段）(访问限制)
        // 需要在获取值之前将 private/protected field 设置允许访问（否则 field.get 会抛出 IllegalAccessException 异常）
        // 此外，setAccessible(true) 可能会失败，如果 jvm　运行期存在　SecurityManager，那么它会根据规则进行检查，有可能阻止 setAccessible(true)
        // 某个SecurityManager可能不允许对java和javax开头的package的类调用setAccessible(true)，这样可以保证JVM核心库的安全
        personIdField.setAccessible(true);
        Object personIdFieldValue = personIdField.get(person);
        System.out.println(String.format("person id is: %s", personIdFieldValue));
        // 设置字段值
        // 在设置一个 private field 的值时，也需要先调用 field.setAccessible(true)，这里没写是因为上面已经设置了
        personIdField.set(person, 9517);
        System.out.println(String.format("person id change to %s", personIdField.get(person)));
    }

    public static void main3(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 调用方法
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264803678201760
        /**
         * ("方法名", 参数类型(int/xx.class))
         *
         * 获取所有 public 的 method, 包含父类:
         *      getMethod()
         *      getMethods()
         * 获取当前类所有的 method, 不包含父类：
         *      getDeclaredMethod()
         *      getDeclaredMethods()
         */
        Class cls = Student.class;
        Method[] methods = cls.getMethods();
        Arrays.stream(methods).forEach(x -> System.out.println(x.getName()));
        // getMethod 获取所有 public 的 method，包含父类
//        System.out.println(cls.getMethod("s1", String.class)); // private method 不能获取
        System.out.println(cls.getMethod("s2", int.class));
//        System.out.println(cls.getMethod("p1", String.class)); // private method 不能获取
        System.out.println(cls.getMethod("p2", int.class));

        // getDeclaredMethod 获取所有的 method，不包含父类
        System.out.println(cls.getDeclaredMethod("s1", String.class));
        System.out.println(cls.getDeclaredMethod("s2", int.class));
//        System.out.println(cls.getDeclaredMethod("p1", String.class)); // 不能获取父类的方法
//        System.out.println(cls.getDeclaredMethod("p2", int.class)); // 不能获取父类的方法

        // 调用方法(第一个参数是实例对象，后面是被调用方法的参数列表）
        String s = "hi";
        Method substring = s.getClass().getMethod("substring", int.class);
        String result = (String) substring.invoke(s, 1);
        System.out.println(String.format("substring result is %s", result));

        // 调用静态方法(无需指定实例对象)
        Method parseInt = Integer.class.getMethod("parseInt", String.class);
        Integer parseIntResult = (Integer) parseInt.invoke(null, "123");
        System.out.println(String.format("parseInt result is %s", parseIntResult));

        // 调用非 public 方法，即 private/protected
        Student student = new Student(12);
        Method s1Method = student.getClass().getDeclaredMethod("s1", String.class);
        // 调用非 public 方法有访问限制，需要先调用 method.setAccessible(true) 允许其访问，否则会得到 IllegalAccessException 异常
        // 此外，setAccessible(true)可能会失败。如果JVM运行期存在SecurityManager，那么它会根据规则进行检查，有可能阻止setAccessible(true)。
        // 例如，某个SecurityManager可能不允许对java和javax开头的package的类调用setAccessible(true)，这样可以保证JVM核心库的安全。
        s1Method.setAccessible(true);
        s1Method.invoke(student, "ds");

        // 多态，即父类中和子类中具有同名方法时
        // 使用反射调用方法时，仍遵循多态原则，即总是调用实际类型的覆写方法（如果存在）
        // 相当于：
        // Person p = new Student();
        // p.hello();
        Method hiMethod = Person.class.getMethod("hi");
        hiMethod.invoke(new Student(1)); // student.hi

        System.out.println(hiMethod.getName());
        System.out.println(hiMethod.getReturnType());
        System.out.println(hiMethod.getParameterTypes());
        System.out.println(hiMethod.getModifiers()); // Modifier.isXXX(hiMethod.getModifiers())
    }

    public static void main4(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        // 调用构造方法
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264802263123712
        Person person = new Person(1);
        // xx.class.newInstance() 只能调用 public 无参数的构造方法
        Person person1 = Person.class.newInstance();

        /**
         * 注意：Constructor 总是当前类定义的构造方法，和父类无关，因此不存在多态的问题
         * 调用非 public 的 Constructor 时，需要先调用 constructor.setAccessible(true) 允许其访问，可能会失败，见 field 和 method 中的描述
         *
         * 获取 public 的 constructor
         *      getConstructor()
         *      getConstructors()
         *
         * 获取所有的 constructor
         *      getDeclaredConstructor()
         *      getDeclaredConstructors()
         */

        // Constructor 对象可以调用任意的构造方法和 Method 类似, Constructor 总是返回对象的实例
        Constructor constructor = Integer.class.getConstructor(String.class);
        Integer integer = (Integer) constructor.newInstance("123");
        System.out.println(integer);
    }

    public static void main5(String[] args) {
        // 获取继承关系
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264804244564000
        Class integer = Integer.class;

        // 获取父级的 Class 实例
        Class number = integer.getSuperclass(); // 获取 Integer 父类的 class 实例
        System.out.println(number); // class java.lang.Number
        Class object = number.getSuperclass();
        System.out.println(object); // class java.lang.Object
        System.out.println(object.getSuperclass()); // null

        // 获取 interface
        // getInterfaces() 只返回当前类直接实现的接口类型，并不包括父类
        Class[] integerInterfaces = integer.getInterfaces();
        Arrays.stream(integerInterfaces).forEach(x -> System.out.println(x));
        // 获取父类实现的 interface
        // 对 interface 调用 getSuperclass() 总是返回 null, 获取接口的父接口要用 getInterfaces()
        Arrays.stream(integer.getSuperclass().getInterfaces()).forEach(x -> System.out.println(x));

        // 继承关系[判断]
        // 判断一个实例是否为某个类型时，通常使用 instanceof 操作符
        Object integer1 = Integer.valueOf(123);
        System.out.println(integer1 instanceof Double);
        System.out.println(integer1 instanceof Integer);
        System.out.println(integer1 instanceof Number);
        System.out.println(integer1 instanceof Serializable);
        // 如果是两个 Class 实例，要判断一个向上转型是否成立，可以调用 isAssignableFrom()
        // a.isAssignableFrom(b.class)  b 类型能否转换为 a 类型，即 b 类型是否能向上转型为 a 类型
        // instanceof是子->父的过程：即左边是右边的子类
        // isAssignableFrom是父->子的过程：即左边是右边的父类或接口
        System.out.println(Integer.class.isAssignableFrom(Integer.class));  // true, 因为 Integer 可以赋值给 Integer
        System.out.println(Number.class.isAssignableFrom(Integer.class));   // true, 因为 Integer 可以赋值给 Number
        System.out.println(Object.class.isAssignableFrom(Integer.class));   // true, 因为 Integer 可以赋值给 Object
        System.out.println(Integer.class.isAssignableFrom(Number.class));   // false, 因为 Number 不能赋值给 Integer
//        Number a =1;
//        Integer b= (Integer)a;
    }

    public static void main(String[] args) throws Throwable {
        // 动态代理(Dynamic Proxy) 可以在运行期动态创建某个 interface 的实例
        // 通过 jdk 提供的一个 Proxy.newProxyInstance() 创建一个接口对象
        // jdk 提供的动态创建接口对象的方式，就叫动态代理
        // 动态代理是通过 Proxy 创建代理对象，然后将接口方法“代理”给 InvocationHandler 完成的
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264804593397984

        // 所有 interface 的变量总是向上转型并指向某个实例的
        Serializable number = Integer.valueOf(1);

        /**
         * 在运行期，动态创建一个 interface 的实例方法如下：
         * 1. 定义一个 InvocationHandler 实例，它负责实现接口的方法调用
         * 2. 通过 Proxy.newProxyInstance() 创建 interface 实例，它需要三个参数
         *  2.1. 使用的 ClassLoader, 通常就是接口类的 ClassLoader
         *  2.2. 需要实现的接口数组，至少需要传入一个接口进去
         *  2.3. 用来处理接口方法调用的 InvocationHandler 实例
         * 3. 将 Proxy.newProxyInstance() 返回的 Object 强制转型为接口类型
         */
        // 一个最简单的动态代理实现
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method);
                if (method.getName().equals("say")) {
                    System.out.println(String.format("hi, %s", args[0]));
                }
                return null;
            }
        };
        IHi hi = (IHi) Proxy.newProxyInstance(
                IHi.class.getClassLoader(), // 传入 ClassLoader
                new Class[]{IHi.class},     // 传入要实现的接口
                handler);                   // 传入处理调用方法的 InvocationHandler
        hi.say("myesn");
        // 动态代理实际上是 jvm 在运行期动态创建 class 字节码并加载的过程，它并没有什么黑魔法
        // 把上面的动态代理改写为静态实现类大概是 entity.HiDynamicProxy 的样子
        // 其实就是 jvm 自动编写了一个 HiDynamicProxy 类（不需要源码，可以直接生成字节码），并不存在可以直接实例化接口的黑魔法
    }


    static void printClassInfo(Class cls) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(String.format("Class name: %s", cls.getName()));
        System.out.println(String.format("Simple name: %s", cls.getSimpleName()));
        if (cls.getPackage() != null) {
            System.out.println(String.format("Package name: %s", cls.getPackage().getName()));
        }
        System.out.println(String.format("is interface: %s", cls.isInterface()));
        System.out.println(String.format("is enum: %s", cls.isEnum()));
        System.out.println(String.format("is array: %s", cls.isArray()));
        System.out.println(String.format("is Primitive: %s", cls.isPrimitive()));
    }
}
