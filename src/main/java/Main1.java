/**
 * @author myp
 * 2019.04.29
 */
public class Main1 {

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
}
