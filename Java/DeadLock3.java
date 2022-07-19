import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock3 {
    
    public static class Table {

        private static Lock Flashlight = new ReentrantLock();
        private static Lock Batteries = new ReentrantLock();

        public static void giveFlashLightAndBatteries() {
            try {
                Flashlight.lock();
                Batteries.lock();
                System.out.println("Lights on");
            } finally {
                Batteries.unlock();
                Flashlight.unlock();
            }
        }

        public static void giveBatteriesAndFlashLight() {
            try {
                Batteries.lock();
                Flashlight.lock();
                System.out.println("Lights on");
            } finally {
                Flashlight.unlock();
                Batteries.unlock();
            }
        }
    }

    public static void main(String[] args) {
        // This thread represents person one
        new Thread(new Runnable() {
            public void run() { Table.giveBatteriesAndFlashLight(); }
        }).start();
    }
}


// https://stackoverflow.com/questions/1385843/simple-deadlock-examples