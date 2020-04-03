import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class App {
    //1.	Gringotts Database
    private static final String GRINGOTTS_DATABASE = "gringotts_db";
    //2.	Sales Database
    private static final String SALES_DATABASE = "sales_db";
    //3.	University System
    private static final String UNIVERSITY_SYSTEM = "university_system_db";
    //4.	Hospital Database
    private static final String HOSPITAL_DATABASE = "hospital_db";
    //5.	Bills Payment System
    private static final String BILLS_PAYMENT_SYSTEM = "bills_payment_system_db";

//    Hallo, if you want to start the current exercise that you want please follow the next 3 IMPORTANT steps:

    //  First Step : Copy the final cobstant with the of the exercise that you want and paste it in
    //  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(----HERE---);
    //  in main method !

    //  Second Step: Go in persistance.xml file and change the this two names with the current exercise:
    //  - <persistence-unit name="---HERE---">
    //  - <property name="hibernate.connection.url"
    //     value="jdbc:mysql://localhost:3306/---HERE---?createDatabaseIfNotExist=true&amp;useSSL=false"/>

    //    Third Step: Before you star the correct project ,please follow the last step
    //     1.right button one the package (Mark Directory As -> Cancel Exclusion)
    //    2.than exclude the current package and to start the next exercise cancel the exclusion.


    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("demo_db");
        EntityManager manager = entityManagerFactory.createEntityManager();

        Engine engine = new Engine(manager);
    }
}
