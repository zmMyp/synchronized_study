/**
 * @author myp
 * 2019.04.29
 */
public class Main4 {

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
}
