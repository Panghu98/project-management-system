package group.uchain.project_management_system.service;

/**
 * 被动使用类字段演示三
 * 常量在编译阶段会存入类调用的常量池中,本质上并没有直接引用到定义常量的类,因此不会触发定义常量的类的初始化
 */
public class ConstClass {

    static {
        System.out.println("ConstClass Init !");
    }

    public static final String HELLO = "Hello";
}
