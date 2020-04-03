package demos.hibernate.entity;

import demos.hibernate.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.management.ManagementFactory;
import java.util.Date;

public class JpaDemoMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("school");
        EntityManager manager = emf.createEntityManager();
        User user = new User("Rado Rusev");

        manager.getTransaction().begin();
        manager.persist(user);
        manager.getTransaction().commit();
        System.out.printf("!!! User identity: %s\n",
                Integer.toHexString(System.identityHashCode(user)));

        System.out.println("---------------------NEW TRANSACTION-----------------------");

        //   manager.getTransaction().begin();
        //   manager.detach(user);
        //   user.setName("R. Rusev");
        //   User user1 = manager.merge(user);
        //   System.out.printf("!!! User identity: %s\n",
        //           Integer.toHexString(System.identityHashCode(user)));
        //   System.out.printf("!!! User identity: %s\n",
        //           Integer.toHexString(System.identityHashCode(user1)));
        //   manager.getTransaction().commit();

        System.out.println("---------------------NEW TRANSACTION-----------------------");

        manager.getTransaction().begin();
        User user2 = manager.find(User.class, 5);
        System.out.println(user2);
        manager.getTransaction().commit();
        System.out.printf("User by Id:%s\n", Integer.toHexString(System.identityHashCode(user2)));

        System.out.println("---------------------NEW TRANSACTION-----------------------");

        // manager.getTransaction().begin();
        // manager.remove(user2);
        // manager.getTransaction().commit();
        System.out.printf("User by Id:%s\n", Integer.toHexString(System.identityHashCode(user2)));

        System.out.println("---------------------NEW TRANSACTION-----------------------");

        manager.getTransaction().begin();
        manager.createQuery("SELECT u FROM User u", User.class)
                .setFirstResult(0)
                .setMaxResults(2)
                .getResultStream().forEach(user1 -> System.out.println(user1.getName()));
        manager.getTransaction().commit();

        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        Root<User> r = criteria.from(User.class);
        criteria.select(r).where(builder.like(r.get("name"), "R%"));
        manager.createQuery(criteria).setMaxResults(3).getResultStream().forEach(System.out::println);
    }
}
