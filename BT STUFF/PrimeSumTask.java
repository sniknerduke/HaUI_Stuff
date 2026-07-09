import java.util.*;

public class PrimeSumTask {
    private static List<Integer> primes = Collections.synchronizedList(new ArrayList<>());
    private static boolean isDone = false;

    public static void main(String[] args) {
        // Luồng 1: Tìm số nguyên tố
        Thread finder = new Thread(() -> {
            for (int i = 100; i <= 100000; i++) {
                if (isPrime(i)) primes.add(i);
            }
            isDone = true;
            System.out.println("Tìm xong số nguyên tố.");
        });

        // Luồng 2: Tính tổng
        Thread calculator = new Thread(() -> {
            long sum = 0;
            int lastSize = 0;
            while (!isDone || lastSize < primes.size()) {
                if (primes.size() > lastSize) {
                    sum += primes.get(lastSize);
                    lastSize++;
                }
            }
            System.out.println("Tổng các số nguyên tố là: " + sum);
        });

        finder.start();
        calculator.start();
    }

    private static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
