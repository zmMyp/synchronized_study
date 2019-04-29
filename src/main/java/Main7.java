/**
 * @author myp
 * 2019.04.29
 */
public class Main7 {

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
}
