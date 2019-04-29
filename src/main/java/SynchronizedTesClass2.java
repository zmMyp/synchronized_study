/**
 * @author myp
 * 2019.04.29
 */
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

}
