package com.company.myesn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {

    // 在 main 方法声明可能抛出的异常，坏处是，一旦发生异常，程序就会立刻退出，因为 main 是最后捕获 exception 的机会
    public static void main1(String[] args) throws Exception {
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1255943543190176
        // Throwable 是异常体系的根，它继承自 Object
        // Throwable 有两个体系： Error 和 Exception:
        //  Error 表示严重的错误，程序对此一般无能为力，比如内存耗尽等，无需捕获即 try catch
        //  Exception 则是运行时的错误，它可以被捕获并处理
        //      RuntimeException 以及它的子类，无需强制捕获异常
        //      非 RuntimeException(包括 IOException、ReflectiveOperationException 等等)，称为 Checked Exception,
        //      需强制捕获，或者用 throws 声明(在函数上写 throws xxxException)

        // 可以使用多个 catch 分别捕获对应的 Exception 及其子类，jvm 会从上到下匹配 catch 语句，多个 catch 只有一个能被执行，或者一个都没匹配到
        // 存在多个 catch　的时候，catch 的顺序非常重要，子类必须写在前面
        try {
            throw new IOException();
        } catch (IOException | NumberFormatException ex) { // 用 | 合并多个异常，当多种异常的处理代码是相同的，就可以合并异常
            ex.printStackTrace();
            // 捕获到异常并再次抛出时，一定要留住原始异常，这样才能在打印的异常栈中看到完整的调用链，即错误定位
            //throw new IllegalArgumentException(ex);
        } finally { // 保证有无异常都会执行，它是可选的
            // 在 finally 语句中抛出异常，catch 中抛出的异常就会消失，因为只能抛出一个异常。
            // 没有被抛出的异常称为“被屏蔽”的异常(Suppressed Exception 压制住的异常)
        }

        // 可以没有 catch(因为方法 main 声明了可能抛出的异常)
        try {
        } finally {
        }

        // 保存所有的异常信息
        // 当 catch 和 finally 都抛出异常，虽然 catch 的异常被屏蔽了，但 finally 抛出的异常仍然包含了它
        Exception origin = null;
        try {

        } catch (Exception e) {
            origin = e;
            throw e;
        } finally {
            Exception e = new IllegalArgumentException();
            if (origin != null) {
                e.addSuppressed(origin);
            }
            //e.getSuppressed(); // 可以获取所有的 Suppressed Exception
            // 决大多数情况下，不在 finally 中抛出异常。因此，通常不关心 Suppressed Exception
            throw e;
        }

        // 当我们在代码中需要抛出异常时，尽量使用 jdk 已定义的异常类型，例如参数不合法应抛出 IllegalArgumentException
        // 在大型项目中，可以自定义异常类型，但是，保持一个合理的异常继承体系是非常重要的
        // 一般先自定义一个 BaseException 作为 根异常，然后再派生除各种业务类型的异常
        // BaseException 通常建议从 RuntimeException 派生
        // 具体查看 com.company.myesn.exceptions 包下面的自定义异常

        // NullPointerException
        // 字符串使用 "" 初始化，数组使用 new String[0] 而不是返回 null
        // 如果一定要根据 null 判断，可以返回 Optional<T>，没有的时候返回 Optional.empty(), 调用方通过 Optional.isPresent() 判断是否有结果
        // 从 java 14 开始，如果产生了 NullPointerException，jvm 会告知 null 对象到底是谁
        // Exception in thread "main" java.lang.NullPointerException: Cannot invoke "String.toLowerCase()" because "<local1>.address.city" is null
        // at Main.main(Main.java:5)
        // 这种增强的 NullPointerException 详细信息是默认关闭的，我们可以给 jvm 添加一个 -XX:+ShowCodeDetailsInExceptionMessages 参数启用它
        // java -XX:+ShowCodeDetailsInExceptionMessages Main.java


    }

    public static void main2(String[] args) throws Exception {
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264740093521088
        // 断言 Assertion：断言失败会抛出 AssertionError，导致程序退出。因此断言不能用于可恢复的程序错误，只能用于开发和测试阶段
        // jvm 默认关闭断言指令，即遇到 assert 语句就自动忽略了，不执行
        // 需要给 jvm 传递 -enableassertions （可简写为 -ea）参数启用断言，
        // 对特定类启用 -ea:com.company.myesn.Main
        // 对特定包启用 -ea:com.company.myesn... (结尾要加三个.)
        // 实际开发中，很少使用 assertion，更好的方法是编写单元测试
        // java -ea Main.java
        double x = Math.abs(-123.45);
        assert x < 0 : "x 必须小于 0";
        System.out.println(x);
    }

    public static void main3(String[] args) {
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264738568571776
        // jdk logging。共有 7 个级别(从严重到普通)：
        // SEVERE
        // WARNING
        // INFO
        // CONFIG
        // FINE
        // FINER
        // FINEST
        Logger logger = Logger.getGlobal();
        Logger logger1 = Logger.getLogger(Main.class.getName());
        logger.info("start process..");
        logger.warning("memory is running out..");
        // 因为默认级别是 info，所以 info 级别以下的日志不会被打印出来
        logger.fine("ignored.");
        logger.severe("process will be terminated...");
        // Logging 系统在 jvm 启动时读取配置文件并完成初始化，一旦开始运行 main()，就无法修改配置
        // 配置需要在 jvm 启动时传递参数 -Djava.util.logging.config.file=<config-file-name>
    }

    public static void main4(String[] args) {
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264738932870688
        // Commons Logging: 是一个第三方日志库，由 Apache 创建的日志模块(它是一个日志的抽象接口(interface/factory)，可以配置使用的具体日志系统）
        // 它是使用最广泛的日志模块，它可以自动检测并使用其他日志模块
        // 它可以挂接不同的日志系统，并通过配置文件指定挂接的日志系统。默认自动搜索并使用 Log4j(另一个流行的日志系统)，没有找到就使用 JDK Logging
        // 项目添加 jar 包依赖：File --》Project Structure --》 Modules --》Dependencies --》+ 号 --》 添加 JARs or directories --》选择 Jar 包 --》 Apply
        // 它定义了 6 个日志级别（默认级别是 INFO)：
        // FATAL
        // ERROR
        // WARNING
        // INFO
        // DEBUG
        // TRACE
        final Log log = LogFactory.getLog(Math.class);
        log.info("start...");
        log.warn("end.");
        // 在静态方法中引用 Log, 通常定义一个静态类型变量
        // static final Log log = LogFactory.getLog(Main.class);
        // 在实例方法中引用 Log, 通常定义一个实例变量, getClass() 好处是可以在子类中使用该 log 实例，如果 是 xxClass.class 就会固定日志输出的名称
        // protected final Log log = LogFactory.getLog(getClass())
    }

    public static void main5(String[] args) {
        // commons logging && log4j2
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264739436350112
        // Log4j 2：最流行的日志实现框架
        // Appender 把同一条日志输出到不同的目的地(console: 屏幕; file: 文件; socket: 通过网络输出到远程计算机; jdbc: 输出到数据库)
        // Filter 过滤哪些 log 需要被输出，哪些不需要，例如仅输出 ERROR 级别的日志
        // Layout 来格式化日志信息，例如，自动添加日期、时间、方法名称等信息
        // Appender -> Filter -> Layout -> Console/File/Socket/JDBC
        // 配置文件：log4j2.xml
        // 配置文件的方式： xml、json、yaml、properties、编程式配置(programmatic configuration)
        // https://logging.apache.org/log4j/2.x/manual/configuration.html

        Log log = LogFactory.getLog(Main.class);
        log.info("info log...");
        log.warn("warn log...");
        log.error("error log...");
    }

    public static void main(String[] args) {
        // slf4j && logback
        // 目前最流行的是这种日志方式
        // slf4j 是一个日志接口，logback 是日志的实现，同样需要一个 logback.xml 的配置文件来配置
        // https://www.liaoxuefeng.com/wiki/1252599548343744/1264739155914176
        final org.slf4j.Logger logger = LoggerFactory.getLogger(Main.class);

        logger.info("set {} to {}", "name", "myesn");
    }
}
