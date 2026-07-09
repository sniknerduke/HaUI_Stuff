import java.util.*;

class Bank {
    private double[] accounts;
    private int transactionsCount = 0;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    // Phương thức chuyển tiền đồng bộ
    public synchronized void transfer(int from, int to, double amount) {
        if (accounts[from] < amount) return;
        accounts[from] -= amount;
        accounts[to] += amount;
        transactionsCount++;
        if (transactionsCount % 1000 == 0) test();
    }

    public void test() {
        double sum = 0;
        for (double a : accounts) sum += a;
        System.out.println("Giao dịch: " + transactionsCount + " | Tổng tiền: " + sum);
    }

    public int size() { return accounts.length; }
}

class TransferThread extends Thread {
    private Bank bank;
    private int fromAccount;
    private double maxAmount;

    public TransferThread(Bank b, int from, double max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    public void run() {
        try {
            while (true) {
                int toAccount = (int) (bank.size() * Math.random());
                double amount = maxAmount * Math.random();
                bank.transfer(fromAccount, toAccount, amount);
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {}
    }
}

public class BankTest {
    public static void main(String[] args) {
        Bank b = new Bank(100, 10000);
        for (int i = 0; i < 100; i++) {
            TransferThread t = new TransferThread(b, i, 10000);
            t.setPriority((int)(Math.random() * 10) + 1);
            t.start();
        }
    }
}
