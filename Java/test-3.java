class Acount {
    double balance;

    void withdraw(double amount) {
        balance -= amount;
    }

    void deposit(double amount) {
        balance += amount;
    }

    void transfer(Acount from, Acount to, double amount) {
        synchronized(from);
        synchronized(to);

        from.withdraw(amount);
        to.deposit(amount);

        release(to);
        release(from);
    }
}