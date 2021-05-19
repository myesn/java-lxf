//// 模块描述文件
//// 从 java 9 引入
//// java 模块文件后缀名是 .jmod
//// 使用模块的作用就是：声明依赖关系
//module IDE {
//    // 只有声明的导出的包，外部代码（即其他模块）才被允许访问
//    // 换句话说，如果外包代码想要访问我们这个模块中的 com.company.entity.interfaces.IPerson 接口，我们必须将其导出
//    exports com.company.entity.interfaces;
//
//    requires java.base; // 可不写，任何模块都会自动引入 java.base
//    requires java.xml; // 因为 main 中用到了 java.xml 模块下的类，所以这里必须先写明依赖关系，注释这一行后编译会报错
//}