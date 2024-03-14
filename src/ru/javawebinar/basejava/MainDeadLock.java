package ru.javawebinar.basejava;

public class MainDeadLock {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000);
        BankAccount account2 = new BankAccount(1000);

        System.out.println("Balances before");
        System.out.println("Account1 balance = " + account.getBalance());
        System.out.println("Account2 balance = " + account2.getBalance());

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                account.pay(100, account2);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                account2.pay(100, account);
            }
        });
        thread.start();
        thread2.start();

        System.out.println("Balances after");
        System.out.println("Account1 balance = " + account.getBalance());
        System.out.println("Account2 balance = " + account2.getBalance());

    }

    static class BankAccount {
        private volatile int balance;

        public BankAccount(int balance) {
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }

        public synchronized void receive(int transact, BankAccount payer) {
            balance += transact;
            System.out.format("Recieved %s from %s",transact,payer);
        }

        public synchronized void pay(int transact, BankAccount consumer) {
            balance -= transact;
            System.out.format("Payed %s to %s",transact,consumer);
            consumer.receive(transact,this);
        }

    }
}
