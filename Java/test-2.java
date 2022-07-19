public static void transfer(Acount from, Acount to, double amount) {
    Acount first = from;
    Acount second = to;

    if (first.compareTo(second) < 0) {
        // Swap them
        first = to;
        second = from;
    }

    synchronized(first) {
        synchronized(second) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }
}