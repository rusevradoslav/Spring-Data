import entities.ingredients.AmmoniumChloride;
import entities.ingredients.BasicIngredient;
import entities.ingredients.Mint;
import entities.ingredients.Nettle;
import entities.labels.BasicLabel;
import entities.shampoos.BasicShampoo;
import entities.shampoos.FiftyShades;
import entities.shampoos.FreshNuke;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence
                .createEntityManagerFactory("shampoo");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        BasicIngredient am = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel("Title", "SubTitle");

        BasicShampoo nuke = new FreshNuke();
        BasicShampoo shades = new FiftyShades();

        nuke.getIngredients().add(am);
        nuke.getIngredients().add(mint);

        shades.getIngredients().add(nettle);
        shades.getIngredients().add(mint);

        entityManager.persist(nuke);
        entityManager.persist(shades);

        entityManager.getTransaction().commit();

        entityManager.close();
    }

}
