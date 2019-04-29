/**
 * @author myp
 * 2019.04.29
 */
public class SynchronizedTestClass {


    SynchroniedObject synchroniedObject=new SynchroniedObject();

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


    public  void test4(){

        System.out.println(Thread.currentThread().getName()+"进入test4");

        // 我现在需要同步的是SynchroniedObject的synchroniedMethod方法
        synchronized (synchroniedObject){
            synchroniedObject.synchroniedMethod();
        }

        System.out.println(Thread.currentThread().getName()+"退出test4");
    }
}
