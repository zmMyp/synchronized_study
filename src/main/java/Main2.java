/**
 * @author myp
 * 2019.04.29
 */
public class Main2 {


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
}
