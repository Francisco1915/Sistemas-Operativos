import Main.Main;
import SharedObjs.sharedObj;

import java.util.concurrent.Semaphore;

public class Demo {

    public static void main(String[] args) {

        sharedObj srObj = new sharedObj();
        Semaphore s = new Semaphore(0);
        Thread m = new Thread(new Main(srObj, s));

        m.start();

    }
}
