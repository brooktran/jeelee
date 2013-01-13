package com.test.test;

/**
 * 
 * 枚举学习 枚举（enum）类型是Java 5新增的特性，它是一种新的类型，允许用常量来表示特定的数据片断，而且全部都以类型安全的形式来表示。Tiger
 * 专家、developerWorks 的多产作者 Brett McLaughlin
 * 将解释枚举的定义，介绍如何在应用程序中运用枚举，以及它为什么能够让您抛弃所有旧的 public static final 代码。
 * 
 * 枚举在什么地方适用呢？一条普遍规则是，任何使用常量的地方，例如目前用 switch
 * 代码切换常量的地方。如果只有单独一个值（例如，鞋的最大尺寸，或者笼子中能装猴子的最大数目），则还是把这个任务留给常量吧。
 * 但是，如果定义了一组值，而这些值中的任何一个都可以用于特定的数据类型，那么将枚举用在这个地方最适合不过。
 * 
 * 二、在枚举类型之前，Java是如何实现枚举功能的
 * 
 * 在枚举类型出现之前，java是通过在接口或者类中定义public static final
 * 的变量来实现的。比如，对于经典的红色警戒2中的英国狙击（sharp-shooter）手三个动作的描述：
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-12-25 新建
 * @since eclipse Ver 1.0
 * 
 */
public enum Enum{
    LOCKED, 
    AIM, 
    SHOOT 
}
/*等同上面
interface SharpShooter_1 { 
    public static final int LOCKED = 1;     // 锁定
    public static final int AIM = 2;        // 瞄准
    public static final int SHOOT = 3;      // 射击
} 
*/

/*
枚举学习
枚举（enum）类型是Java 5新增的特性，它是一种新的类型，允许用常量来表示特定的数据片断，而且全部都以类型安全的形式来表示。Tiger 专家、developerWorks 的多产作者 Brett McLaughlin 将解释枚举的定义，介绍如何在应用程序中运用枚举，以及它为什么能够让您抛弃所有旧的 public static final 代码。 

枚举在什么地方适用呢？一条普遍规则是，任何使用常量的地方，例如目前用 switch 代码切换常量的地方。如果只有单独一个值（例如，鞋的最大尺寸，或者笼子中能装猴子的最大数目），则还是把这个任务留给常量吧。但是，如果定义了一组值，而这些值中的任何一个都可以用于特定的数据类型，那么将枚举用在这个地方最适合不过。 

二、在枚举类型之前，Java是如何实现枚举功能的 

在枚举类型出现之前，java是通过在接口或者类中定义public static final 的变量来实现的。比如，对于经典的红色警戒2中的英国狙击（sharp-shooter）手三个动作的描述： 

*//** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2007-11-29 
* Time: 8:53:04 
* 狙击手活动 
*//* 
public interface SharpShooter_1 { 
    public static final int LOCKED = 1;     //锁定 
    public static final int AIM = 2;        //瞄准 
    public static final int SHOOT = 3;      //射击 
} 


*//** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2007-11-29 
* Time: 9:03:50 
* 测试方法 
*//* 
public class TestDemo_1 { 
    public static void main(String args[]) { 
        TestDemo_1 test = new TestDemo_1(); 
        test.doAction(1); 
        test.doAction(2); 
        test.doAction(3); 
        test.doAction(4); 
    } 

    *//** 
     * 执行的动作 
     * @param action 
     *//* 
    public void doAction(int action) { 
        switch (action) { 
            case SharpShooter_1.LOCKED: 
                System.out.println("1:锁定目标"); 
                break; 
            case SharpShooter_1.AIM: 
                System.out.println("2:瞄准目标"); 
                break; 
            case SharpShooter_1.SHOOT: 
                System.out.println("3:射击"); 
                break; 
            default: 
                System.out.println("×:游戏还没有定义此动作!"); 
        } 
    } 
} 


运行结果： 
1:锁定目标 
2:瞄准目标 
3:射击 
×:游戏还没有定义此动作! 

[说明]:当然SharpShooter_1也可以声明为class，还可以直接将常量定义到TestDemo_1中，如果常量只是在类内部使用，就声明为private或者是protected，如果声明为public，则通常是与类功能相联系的常数。 

[注意]:switch语句的条件只能接收数值或字符(byte、short、int或char)或枚举(enum)类型的变量名或表达式。如果没有符合条件数值或字符，则执行default语句，default语句不是必须的，如果没有默认要处理的动作，则default语句可省略。break语句的作用是跳出循环块。 

三、枚举类型的等价实现 

*//** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2007-11-29 
* Time: 10:10:41 
* 狙击手活动（枚举类型实现） 
*//* 
public enum SharpShooter_2 { 
    LOCKED, 
    AIM, 
    SHOOT 
} 


*//** 
* Created by IntelliJ IDEA. 
* User: leizhimin 
* Date: 2007-11-29 
* Time: 10:12:40 
* 测试普通枚举类型 
*//* 
public class TestDemo_2 { 

    public static void main(String args[]){ 
        TestDemo_2 test=new TestDemo_2(); 
        test.doAction(SharpShooter_2.LOCKED); 
        test.doAction(SharpShooter_2.AIM); 
        test.doAction(SharpShooter_2.SHOOT); 
    } 
    *//** 
     * 执行的动作 
     * @param action 
     *//* 
    public void doAction(SharpShooter_2 action) { 
        switch (action) { 
            case LOCKED: 
                System.out.println("1:锁定目标"); 
                break; 
            case AIM: 
                System.out.println("2:瞄准目标"); 
                break; 
            case SHOOT: 
                System.out.println("3:射击"); 
                break; 
            default: 
                System.out.println("×:游戏还没有定义此动作!"); 
        } 
    } 
} 


运行结果： 

1:锁定目标 
2:瞄准目标 
3:射击 

三、枚举类型的实质 

在编译SharpShooter_2.java后，会生成一个SharpShooter_2.class文件，这说明枚举类型的实质还是一个类。因此，在某种程度上，enum关键字的作用就是class或者interface。 

当使用enum定义一个枚举类型时，实际上所定义的类型自动继承了java.lang.Enum类。而每个被枚举的成员实质就是一个枚举类型的实例，他们默认都是public static final的。可以直接通过枚举类型名直接使用它们。 

在查询JDK1.5文档的java.lang.Enum类，里面清楚的说明：java.lang.Enum类是所有 Java 语言枚举类型的公共基本类。因此，所有枚举类型都拥有有java.lang.Enum类所提供的共有方法。因此，要学会使用枚举，还必须认识java.lang.Enum类。下面将详细说明。 


四、java.lang.Enum类 

public abstract class Enum<E extends Enum<E>>extends Objectimplements Comparable<E>, Serializable这是所有 Java 语言枚举类型的公共基本类。 

－－－－－－－－－－－－－－－－－－ 
构造方法摘要 
protected Enum(String name,int ordinal) 
    单独的构造方法。程序员无法调用此构造方法。该构造方法用于由响应枚举类型声明的编译器发出的代码。 

    参数： 
    name - - 此枚举常量的名称，它是用来声明该常量的标识符。 
    ordinal - - 枚举常量的序数（它在枚举声明中的位置，其中初始常量序数为零）。 

－－－－－－－－－－－－－－－－－－ 
方法摘要 

protected  Object clone() 
          抛出 CloneNotSupportedException。 

int compareTo(E o) 
          比较此枚举与指定对象的顺序。在该对象小于、等于或大于指定对象时，分别返回负整数、零或正整数。 枚举常量只能与相同枚举类型的其他枚举常量进行比较。该方法实现的自然顺序就是声明常量的顺序。  

boolean equals(Object other) 
          当指定对象等于此枚举常量时，返回 true。 

Class<E> getDeclaringClass() 
          返回与此枚举常量的枚举类型相对应的 Class 对象。当且仅当 e1.getDeclaringClass() == e2.getDeclaringClass() 时，两个枚举常量 e1 和 e2 的枚举类型才相同。（由该方法返回的值不同于由 Object.getClass() 方法返回的值，Object.getClass() 方法用于带有特定常量的类主体的枚举常量。）  

int hashCode() 
          返回枚举常量的哈希码。 

String name() 
          返回此枚举常量的名称，在其枚举声明中对其进行声明。 与此方法相比，大多数程序员应该优先考虑使用 toString() 方法，因为 toString 方法返回更加用户友好的名称。该方法主要设计用于特殊情形，其正确性取决于获得正确的名称，其名称不会随版本的改变而改变 

int ordinal() 
          返回枚举常量的序数（它在枚举声明中的位置，其中初始常量序数为零）。 大多数程序员不会使用此方法。它被设计用于复杂的基于枚举的数据结构，比如 EnumSet 和 EnumMap。  

String toString() 
          返回枚举常量的名称，它包含在声明中。 

public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) 
          返回带指定名称的指定枚举类型的枚举常量。名称必须与在此类型中声明枚举常量所用的标识符完全匹配。（不允许使用额外的空白字符。） 

   参数： 
   enumType - 要从中返回常量的枚举类型的 Class 对象 
   name - 要返回的常量名称  

注意：ordinal() 方法得到枚举顺序的索引，默认从0开始。*/

