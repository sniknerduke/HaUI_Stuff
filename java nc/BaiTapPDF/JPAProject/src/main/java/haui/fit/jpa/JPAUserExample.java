package haui.fit.jpa;

import haui.fit.jpa.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JPAUserExample {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("userPU");
        EntityManager em = emf.createEntityManager();

        try {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));

            em.getTransaction().begin();
            UserEntity user = new UserEntity();
            user.setUserName("jpa_user_01");
            user.setUserPass("123456");
            user.setUserFullname("JPA Demo User");
            user.setUserEmail("demo.jpa@haui.edu.vn");
            user.setCreatedDate(today);
            user.setUserParentId(20);
            user.setUserDeleted(false);
            em.persist(user);
            em.getTransaction().commit();

            Long totalUsers = em.createQuery("SELECT COUNT(u) FROM UserEntity u", Long.class)
                    .getSingleResult();
            System.out.println("Total users: " + totalUsers);

            List<UserEntity> users = em.createQuery("SELECT u FROM UserEntity u ORDER BY u.id DESC", UserEntity.class)
                    .setMaxResults(10)
                    .getResultList();
            users.forEach(System.out::println);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
