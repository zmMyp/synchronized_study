/**
 * @author myp
 * 2019.04.29
 */
public class Main5 {

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
}
