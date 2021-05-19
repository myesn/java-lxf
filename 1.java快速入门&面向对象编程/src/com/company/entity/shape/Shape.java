//package com.company.entity.shape;

// 正常情况下，只要某个 class 没有 final 修饰符，那么任何类都可以从该 class 继承，如果写了就不能
// 从 java 15 开始，允许是哟个 sealed 修饰 class，并通过 permits 明确写出能够从该 class 继承的子类名称
// 如果一个类不希望任何其他类继承自它，可以把类标记为 final
// 对于一个类的实例字段，同样可以用 final 修饰。用 final 修饰的字段在初始化后不能被修改（可以在构造方法中初始化 final 字段）
//public sealed class Shape permits Rect, Circle, Triangle {
//}
