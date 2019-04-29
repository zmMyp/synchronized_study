/**
 * @author myp
 * 2019.04.29
 */
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
