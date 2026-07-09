package dao;

import entity.TaiKhoan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

public class TaiKhoanDAO {
    private EntityManagerFactory emf;

    public TaiKhoanDAO() {
        // Tên "tx1_pu" phải khớp với cấu hình trong persistence.xml
        try {
            emf = Persistence.createEntityManagerFactory("tx1_pu");
        } catch (Exception e) {
            System.err.println("Kết nối MySQL thất bại bằng cấu hình mặc định. Hiển thị hộp thoại yêu cầu nhập mật khẩu...");
            boolean success = false;
            while (!success) {
                JPanel panel = new JPanel(new GridLayout(0, 1));
                JTextField txtUser = new JTextField("root");
                JPasswordField txtPass = new JPasswordField();
                panel.add(new JLabel("MySQL Username:"));
                panel.add(txtUser);
                panel.add(new JLabel("MySQL Password:"));
                panel.add(txtPass);

                int result = JOptionPane.showConfirmDialog(null, panel, 
                        "Lỗi cấu hình MySQL. Nhập DB Credentials", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                
                if (result == JOptionPane.OK_OPTION) {
                    Map<String, String> properties = new HashMap<>();
                    properties.put("javax.persistence.jdbc.user", txtUser.getText());
                    properties.put("javax.persistence.jdbc.password", new String(txtPass.getPassword()));
                    try {
                        emf = Persistence.createEntityManagerFactory("tx1_pu", properties);
                        success = true;
                        System.out.println("Kết nối MySQL thành công!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Kết nối bị từ chối, vui lòng kiểm tra lại Username/Password!", "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    System.err.println("Đã hủy nhập tài khoản MySQL. Tắt server.");
                    System.exit(1);
                }
            }
        }
    }

    // Phương thức khởi tạo và trả về đối tượng EntityManager quản lý kết nối JPA
    public EntityManager getConnection() {
        return emf.createEntityManager();
    }

    // 1. Xác thực đăng nhập (Login)
    public boolean login(String username, String password) {
        EntityManager em = getConnection();
        try {
            TaiKhoan tk = em.find(TaiKhoan.class, username);
            if (tk != null && tk.getPassword().equals(password)) {
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }

    // 2. Tạo tài khoản mới (Create)
    public boolean createAccount(TaiKhoan account) {
        EntityManager em = getConnection();
        try {
            em.getTransaction().begin();
            // Kiểm tra xem tài khoản đã tồn tại chưa
            TaiKhoan existTk = em.find(TaiKhoan.class, account.getUsername());
            if (existTk != null) {
                em.getTransaction().rollback();
                return false; // Tài khoản đã tồn tại
            }
            em.persist(account);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // 3. Đọc thông tin tài khoản để sửa (Read)
    public TaiKhoan readAccount(String username) {
        EntityManager em = getConnection();
        try {
            return em.find(TaiKhoan.class, username);
        } finally {
            em.close();
        }
    }

    // 4. Cập nhật thông tin tài khoản (Update)
    public boolean updateAccount(TaiKhoan account) {
        EntityManager em = getConnection();
        try {
            em.getTransaction().begin();
            TaiKhoan tk = em.find(TaiKhoan.class, account.getUsername());
            if (tk == null) {
                em.getTransaction().rollback();
                return false; // Không tìm thấy tài khoản để cập nhật
            }
            tk.setPassword(account.getPassword());
            em.merge(tk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // 5. Xóa tài khoản (Delete)
    public boolean deleteAccount(String username) {
        EntityManager em = getConnection();
        try {
            em.getTransaction().begin();
            TaiKhoan tk = em.find(TaiKhoan.class, username);
            if (tk == null) {
                em.getTransaction().rollback();
                return false; // Không tìm thấy tài khoản để xóa
            }
            em.remove(tk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // Đóng EntityManagerFactory khi server dừng
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
