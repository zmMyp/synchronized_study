# synchronized_study

 为什么研究这个呢？做安卓呢，并发并不常用，线程切换啊这些很多是框架内封装好了，平时开发无非是拉拉数据，展示展示，不过最近框架方面看的多，synchronized词就频繁出现了，以前多多少少也知道，今天索性系统整理一下。

####废话不多说了，抛出synchronized ，分两类大体，对象锁和类锁

先贴个测试类吧，测试详解都在测试类开头注释中
```
public class SynchronizedTestClass {

 // 这个对象锁是加在代码块上的
    public void test1() {

        System.out.println(Thread.currentThread().getName()+"进入test1");
        synchronized (this) {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
        System.out.println(Thread.currentThread().getName()+"test1执行完毕");

    }


    // 这个对象锁是加在方法上
    public synchronized void test2() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }

    }

    // 一般的方法，不是同步方法
    public void test3() {

        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }

    }
}
```

##对象锁
1. 对象锁的使用

``` 
 /**
     * 对象锁的测试
     *
     * 对象锁可以加在实例方法上如 SynchronizedTestClass 的test1()
     * 也可以加载实例方法的代码块如  SynchronizedTestClass 的test2()
     *
     * 如下例子
     * 当不同线程 thread1 和 thread2  同时调用synchronizedTestClass对象的同一个对象锁方法test1时，运行结果如下
     *
     * thread1 : 4
     * thread1 : 3
     * thread1 : 2
     * thread1 : 1
     * thread1 : 0
     * thread2 : 4
     * thread2 : 3
     * thread2 : 2
     * thread2 : 1
     * thread2 : 0
     *
     * 说明在thread1持有对象实例synchronizedTestClass ，thread2是不能调用test1的，因为thread1和thread2使用的是同一把锁
     *
     *
     */

    public static void main(String[] args){

        final SynchronizedTestClass synchronizedTestClass=new SynchronizedTestClass();

        Thread thread1=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test1();
            }
        },"thread1");


        Thread thread2=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test1();
            }
        },"thread2");

        thread1.start();
        thread2.start();

    }
```
2. 对象锁中不同线程调用同一个实例的不同对象锁方法
```
/**
     * 书接上文，thread2此时要想了，既然test1被thread1正在使用不让我用，那么，如果此时 我用用synchronizedTestClass的test2呢？
     *
     * thread1 : 4
     * thread1 : 3
     * thread1 : 2
     * thread1 : 1
     * thread1 : 0
     * thread2 : 4
     * thread2 : 3
     * thread2 : 2
     * thread2 : 1
     * thread2 : 0
     *
     * 我靠，test2也不让我用，还真是霸道
     *
     * 仔细想想为什么呢？
     *
     * 还是因为使用的是同一把锁啊，这把锁就是synchronizedTestClass这个对象，我test1和test2都用这把锁锁着，就算thread1现在使用的是test1方法
     * 但是test2你也不能用
     *
     * 这就说明，不同的线程，在操作同一个实例的同步方法时，假如其中一个线程正在使用这个实例的其中一个同步方法，那么这个实例对象的所有同步方法都不能
     * 被其他线程使用，记住，是所有，所有，所有，重要的事说三遍
     *
     * 当然，如下实例，假如此时再new 一个SynchronizedTestClass的实例对象出来synchronizedTestClass2，那么我thread2 此时调用synchronizedTestClass2
     * 的test1还是test2都没问题，因为此时，锁不同嘛，不是一把锁
     *
     * 请看Main3
     */

    public static void main(String[] args){

        final SynchronizedTestClass synchronizedTestClass=new SynchronizedTestClass();

        Thread thread1=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test1();
            }
        },"thread1");


        Thread thread2=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test2();
            }
        },"thread2");

        thread1.start();
        thread2.start();

    }
```
3. 对象锁中不同线程调用同一个实例的同步方法和一般方法呢
```
/**
     *
     * 书接上文
     *
     * 通过Main1,Main2测试我们知道了SynchronizedTestClass的两个对象锁方法test1和test2的一些规则
     *
     * 如果此时，我SynchronizedTestClass添加了一个非同步方法，test3，thread1 在使用test1方法时，
     * thread2可以使用test3方法么？
     * 随便一想都知道，当然可以，因为test3没有任何锁嘛，谁都可以使用，结果预料之中
     *
     * thread1 : 4
     * thread2 : 4
     * thread1 : 3
     * thread2 : 3
     * thread2 : 2
     * thread1 : 2
     * thread2 : 1
     * thread1 : 1
     * thread1 : 0
     * thread2 : 0
     *
     */

    public static void main(String[] args){

        final SynchronizedTestClass synchronizedTestClass=new SynchronizedTestClass();

        Thread thread1=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test1();
            }
        },"thread1");


        Thread thread2=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test3();
            }
        },"thread2");

        thread1.start();
        thread2.start();

    }
```
4. 对象锁使用时，加在方法上和加在代码块上有啥区别
  先描述轻微的差别，注意看注释
 ```
 /**
     * 这个测试是看看相同的对象锁加在不同位置的区别
     *
     * SynchronizedTestClass我们看到他的test1和test2都是同步方法，但是位置不同
     *
     * test1修饰在方法上，test2修饰在代码块里
     *
     * 有啥区别呢？test是修饰代码块的同步，
     * 此时我们先在test1 的同步代码块以外，System.out.println(Thread.currentThread().getName()+"进入test1");加这行代码运行
     *
     * thread1进入test1
     * thread2进入test1
     * thread1 : 4
     * thread1 : 3
     * thread1 : 2
     * thread1 : 1
     * thread1 : 0
     * thread2 : 4
     * thread2 : 3
     * thread2 : 2
     * thread2 : 1
     * thread2 : 0
     *
     * 现在应该意识到了一点区别吧
     *
     * test1是局部进行了同步，同步代码块以外的代码是可以被其他线程调用到的，而test2同步修饰了整个方法，所以整个方法代码都不能被非占有线程使用
     *
     * 但是就是这么点差别么？从性能考虑下，请看Main5
     *
     */
    public static void main(String[] args){

        final SynchronizedTestClass synchronizedTestClass=new SynchronizedTestClass();

        Thread thread1=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test4();
            }
        },"thread1");


        Thread thread2=new Thread(new Runnable() {
            public void run() {
                synchronizedTestClass.test1();
            }
        },"thread2");

        thread1.start();
        thread2.start();

    }
```
5. 对象锁使用时，加在方法上和加在代码块上的显著差别，并发中的明显差别
 我们如下来设计测试代码，
(1)  先新增一个SynchroniedObject类,需要同步的代码放入synchroniedMethod
```
public class SynchroniedObject {

    public void synchroniedMethod(){

        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i +"SynchroniedObject中的synchroniedMethod");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }

    }
}
```
(2) SynchronizedTestClass增加一个属性  
SynchroniedObject synchroniedObject=new SynchroniedObject(); 

(3) test1方法修改为test4
```

    SynchroniedObject synchroniedObject=new SynchroniedObject();

public  void test4(){

        System.out.println(Thread.currentThread().getName()+"进入test4");

        // 我现在需要同步的是SynchroniedObject的synchroniedMethod方法
        synchronized (synchroniedObject){
            synchroniedObject.synchroniedMethod();
        }

        System.out.println(Thread.currentThread().getName()+"退出test4");
    }
```

(4) 这时，我们让thread1执行 test4 让thread2 执行test2 看看，注意看注释
```
 /**
     *
     * Main4只是讲的对象同步锁加在方法和代码块的一些细小差别
     *
     * 那么这个测试类我们讲点重要的差别：
     *
     * 我们都知道，同一个实例（synchronizedTestClass），不同线程(thread1 ,thread2)
     * 调用他的对象锁同步方法,当thread1 调用synchronizedTestClass的test1方法时，thread2连synchronizedTestClass
     * 的test2方法都不能用，差评啊，很霸道，这在实际开发中会造成什么呢？
     *
     * 当某个线程进入同步方法获得对象锁，那么其他线程访问这里对象的同步方法时，必须等待或者阻塞，
     * 这对高并发的系统是致命的，这很容易导致系统的崩溃
     *
     * 当然同步方法和同步代码块都会有这样的缺陷，只要用了synchronized关键字就会有这样的风险和缺陷。既然避免不了这种缺陷，
     * 那么就应该将风险降到最低。这也是同步代码块在某种情况下要优于同步方法的方面
     *
     * 怎么处理呢？
     *
     * 1.现在我们搞一个SynchroniedObject类，把test1需要同步的代码块放到SynchroniedObject类synchroniedMethod方法中
     * 2.在SynchronizedTestClass中，我持有一个SynchroniedObject类的实例synchroniedObject
     * 3.我们把test1修改成test4的样子，看，对象锁加在了synchroniedObject对象上，有什么效果呢？
     *
     * 4。我们让thread1调用test4,注意啊，test4的对象锁是synchroniedObject，此时我们让thread2调用test2
     *
     *
     * thread2 : 4
     * thread1进入test4
     * thread1 : 4SynchroniedObject中的synchroniedMethod
     * thread2 : 3
     * thread1 : 3SynchroniedObject中的synchroniedMethod
     * thread2 : 2
     * thread1 : 2SynchroniedObject中的synchroniedMethod
     * thread2 : 1
     * thread1 : 1SynchroniedObject中的synchroniedMethod
     * thread2 : 0
     * thread1 : 0SynchroniedObject中的synchroniedMethod
     * thread1退出test4
     *
     * 看到没，thread1在调test4时，一点不影响thread2使用其他同步方法，这就大大降低了并发时的阻塞可能。
     *
     */
    public static void main(String[] args){

        final SynchronizedTestClass synchronizedTestClass=new SynchronizedTestClass();

        Thread thread1=new Thread(new Runnable() {
            public void run() {

                // test4的对象锁是synchroniedObject
                synchronizedTestClass.test4();
            }
        },"thread1");


        Thread thread2=new Thread(new Runnable() {
            public void run() {

                // test2的对象锁是synchronizedTestClass这个实例
                synchronizedTestClass.test2();
            }
        },"thread2");

        thread1.start();
        thread2.start();

    }
```
##类锁
类锁也有两种加法：
```
public class SynchronizedTesClass2 {

 // 1.类锁的第一种方式
    public synchronized static void test1() {

        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }

    }

    // 2.类锁的第二种方式
    public  static void test2() {

        synchronized (SynchronizedTesClass2.class){

            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }


    }

    public synchronized  void test3() {

        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }

    }
```
(1) 同一个类的不同实例执行同一个类锁同步的方法会如何？
 ```
/**
     *
     *  这个测试类研究类锁，
     *  类锁的两种方式，参看 SynchronizedTesClass2 的 test1 和test2 方法
     *
     *  我们探讨的是类锁的影响，如下示例，我们实例化两个SynchronizedTesClass2对象，synchronizedTestClass1 和 synchronizedTestClass2
     *  看清楚，是同一个类的两个实例，如果SynchronizedTesClass2的test1是对象锁锁住的，我们根据上面的研究知道
     *   thread1 调用 synchronizedTestClass1 的test1 一点不影响 thread2 调用  synchronizedTestClass2 的test1 因为锁不同嘛
     *
     *   但是。。。。。。
     *
     *   现在 test1是类锁，会有上面运行结果呢，如下:
     *
     * thread1 : 4
     * thread1 : 3
     * thread1 : 2
     * thread1 : 1
     * thread1 : 0
     * thread2 : 4
     * thread2 : 3
     * thread2 : 2
     * thread2 : 1
     * thread2 : 0
     *
     * 通过以上的运行结果知道，一个方法被类锁了，每个线程都受到这个类的所有实例的同步影响
     *
     */

    public static void main(String[] args) {

        final SynchronizedTesClass2 synchronizedTestClass1 = new SynchronizedTesClass2();
        final SynchronizedTesClass2 synchronizedTestClass2 = new SynchronizedTesClass2();

        Thread thread1 = new Thread(new Runnable() {
            public void run() {


                // test1是类锁
                synchronizedTestClass1.test1();
            }
        }, "thread1");


        Thread thread2 = new Thread(new Runnable() {
            public void run() {

                // test3是对象锁
                synchronizedTestClass2.test1();
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }
```
(2) 类锁和对象锁是同一个锁么？
```

    /**
     *
     * 最后一个话题了
     * 如果一个类的同时有类锁修饰的方法，和对象锁修饰的方法，不同的线程分别调用同一个对象的这两种方法，会被锁么？
     *
     * 举例如下：
     * SynchronizedTesClass2 这类 ,test1 方法是静态的类锁， test3 是对象锁，现在实例化一个  SynchronizedTesClass2 的对象synchronizedTestClass1
     *
     * 分别用thread1 和 thread2 执行他的 test1 和test3结果运行如下
     *
     * thread2 : 4
     * thread1 : 4
     * thread2 : 3
     * thread1 : 3
     * thread2 : 2
     * thread1 : 2
     * thread2 : 1
     * thread1 : 1
     * thread1 : 0
     * thread2 : 0
     *
     *
     * 说明，对象锁和类锁是两个不同的锁
     */

    public static void main(String[] args) {

        final SynchronizedTesClass2 synchronizedTestClass1 = new SynchronizedTesClass2();

        Thread thread1 = new Thread(new Runnable() {
            public void run() {


                // test1是类锁
                synchronizedTestClass1.test1();
            }
        }, "thread1");


        Thread thread2 = new Thread(new Runnable() {
            public void run() {

                // test3是对象锁
                synchronizedTestClass1.test3();
            }
        }, "thread2");

        thread1.start();
        thread2.start();
    }
```
