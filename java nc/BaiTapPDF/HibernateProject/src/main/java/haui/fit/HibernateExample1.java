package haui.fit;

import haui.fit.entity.UserObject;
import haui.fit.utils.HibernateUtils;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HibernateExample1 {
    public static void main(String[] args) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));

            UserObject user1 = new UserObject();
            user1.setUserFullname("Hoang Quang Huy");
            user1.setUserName("huyhq456");
            user1.setUserPass("123456");
            user1.setCreatedDate(today);
            user1.setModifiedDate(today);
            user1.setUserEmail("huyhq@haui.edu.vn");
            user1.setUserParentId(20L);
            user1.setUserDeleted(false);

            Long userId = (Long) session.save(user1);
            System.out.println("User id = " + userId);

            Long numberOfUser = session.createQuery("SELECT COUNT(u.id) FROM UserObject u", Long.class)
                    .uniqueResult();
            System.out.println("Number of user in database: " + numberOfUser);

            UserObject savedUser = session.find(UserObject.class, userId);
            System.out.println("savedUser: " + savedUser);

            savedUser.setUserFullname("Hoang Vuong Bao");
            savedUser.setModifiedDate(today);
            session.update(savedUser);

            List<UserObject> users = session.createQuery("FROM UserObject", UserObject.class).list();
            users.forEach(System.out::println);

            session.delete(savedUser);

            numberOfUser = session.createQuery("SELECT COUNT(u.id) FROM UserObject u", Long.class)
                    .uniqueResult();
            System.out.println("Number of user in database: " + numberOfUser);

            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            HibernateUtils.close();
        }
    }
}
