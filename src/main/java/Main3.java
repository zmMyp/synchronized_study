/**
 * @author myp
 * 2019.04.29
 */
public class Main3 {


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
}
