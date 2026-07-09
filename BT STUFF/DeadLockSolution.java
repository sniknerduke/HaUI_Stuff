class Product {
    public synchronized void confirmPurchase(Checkout checkout) {
        System.out.println(Thread.currentThread().getName() + " giữ Product, đang chờ Checkout...");
        checkout.modifyStock();
    }
    public synchronized void modifyStock() {
        System.out.println("Đã cập nhật kho hàng.");
    }
}

class Checkout {
    public synchronized void processPayment(Product product) {
        System.out.println(Thread.currentThread().getName() + " giữ Checkout, đang chờ Product...");
        product.modifyStock();
    }
    public synchronized void modifyStock() {
        System.out.println("Đã xử lý thanh toán.");
    }
}

public class DeadLockSolution {
    public static void main(String[] args) {
        Product product = new Product();
        Checkout checkout = new Checkout();

        // Luồng gây Deadlock: thứ tự khóa ngược nhau
        // Giải pháp: Cả 2 luồng phải chiếm khóa theo cùng 1 thứ tự (ví dụ: luôn chiếm Product trước)
        new Thread(() -> {
            synchronized (product) {
                synchronized (checkout) {
                    product.confirmPurchase(checkout);
                }
            }
        }, "Customer-1").start();

        new Thread(() -> {
            synchronized (product) { // Sửa lại thứ tự chiếm khóa ở đây để tránh Deadlock
                synchronized (checkout) {
                    checkout.processPayment(product);
                }
            }
        }, "Customer-2").start();
    }
}