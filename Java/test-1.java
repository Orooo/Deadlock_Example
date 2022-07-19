class Acount {
    double balance;
    int id;

    public Acount(int id, double balance) {
        this.balance = balance;
        this.id = id;
    }

    void withdraw(double amount) {
        balance -= amount;
    }

    void deposit(double amount) {
        balance += amount;
    }
}

class Main {
    public static void main(String[] args) {
        final Acount first = new Acount(1, 1000);
        final Acount second = new Acount(2, 300);

        Thread a = new Thread() {
            public void run() {
                transfer(first, second, 200);
            }
        };

        Thread b = new Thread() {
            public void run() {
                transfer(second, first, 300);
            }
        };

        a.start();
        b.start();
    }

    public static void transfer(Acount from, Acount to, double amount) {
        synchronized(lock) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }
}


// https://stackoverflow.com/questions/13326861/avoid-deadlock-example/13326948#comment34617396_13326948