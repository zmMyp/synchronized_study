/**
 * @author myp
 * 2019.04.29
 */
public class Main6 {

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
}
