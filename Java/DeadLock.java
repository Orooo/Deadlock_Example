public class DeadLock {
    public static void main(String[] args) {
        // These are the two resource objects
        // we'll try to get locks for
        final Object resource1 = "resource1";
        final Object resource2 = "resource2";

        // Here's the first thread.
        // It tries to lock resource1 then resource2
        Thread t1 = new Thread() {
            public void run() {
                // Lock resource 1
                synchronized(resource1) {
                    System.out.println("Thread 1: locked resource 1");
                    // Pause for a bit, simulating some file I/O or somethin.
                    // Basically, we just want to give the other thread a chance to run.
                    // Threads and deadlock are asynchrinous things, 
                    // but we're trying to force deadlock to happen here...
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {}

                    // Now wait 'till we can get a lock on resource 2'
                    synchronized(resource2) {
                        System.out.println("Thread 1: locked resource 2");
                    }
                }
            }
        };

        // Here's the second thread.
        // It tries to lock resource2 then resource1
        Thread t2 = new Thread() {
            public void run() {
                // This thread locks resource2 right away
                synchronized(resource2) {
                    System.out.println("Thread 2: locked resource 2");
                    
                    // then it pauses, for the same reason as the first thread does
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {}

                    // Then it tries to lock resource1.
                    // But wait! Thread 1 locked resource1,
                    // and won't release it till it gets a lock on resource2.
                    
                    // This thread holds the lock on resource2,
                    // and wont't release it till it gets resource1.

                    // We're at an impasse.
                    // Neither thread can run, and the program freezes up.
                    synchronized(resource1) {
                        System.out.println("Thread 2: locked resource");
                    }
                }
            }
        };

        // Start the two threads.
        // If all goes as planned, deadlock will occur,
        // and the program will never exit.
        t1.start();
        t2.start();
    }
}


// example from the computer science department of a university in Taiwan
// https://people.cs.nctu.edu.tw/~tsaiwn/
// https://stackoverflow.com/questions/1385843/simple-deadlock-examples