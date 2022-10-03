package net.ent.etrs.boutik.init;

import com.github.javafaker.Faker;
import net.ent.etrs.boutik.model.entities.*;
import net.ent.etrs.boutik.model.facades.*;
import org.apache.commons.collections4.IterableUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Startup
@Singleton
public class InitData {

    private Faker faker = Faker.instance(Locale.FRANCE);

    @Inject
    private FacadeCategorie facadeCategorie;

    @Inject
    private FacadeMarque facadeMarque;

    @Inject
    private FacadeProduit facadeProduit;

    @Inject
    private FacadeUser facadeUser;

    @Inject
    private FacadeCommande facadeCommande;

    @PostConstruct
    public void init() {
        try {
            this.initUser();
            this.initCategorie();
            this.initMarque();
            this.initProduit();
            this.initCommande();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCommande() throws Exception {
        List<User> lstUsers = this.facadeUser.findAll().stream().collect(Collectors.toList());
        for (int i = 0; i < faker.random().nextInt(5, lstUsers.size() - 1); i++) {
            Commande commande = new Commande();

            commande.setUser(lstUsers.get(faker.random().nextInt(0, lstUsers.size() - 1)));
            commande.setDateCommande(LocalDateTime.now().plusMinutes(1));
            commande.setPrix(((Integer) (faker.random().nextInt(10000))).floatValue());
            commande.setEtat(EtatCommande.values()[faker.random().nextInt(0, EtatCommande.values().length - 1)]);
            List<Produit> lstProduits = this.facadeProduit.findAll().stream().collect(Collectors.toList());
            for (int j = 0; j < faker.random().nextInt(5, lstProduits.size() - 1); j++) {
                commande.ajouterProduit(lstProduits.get(faker.random().nextInt(0, lstProduits.size() - 1)));
            }
            this.facadeCommande.save(commande);
        }


//        Commande commande2 = new Commande();
//        commande2.setUser(this.facadeUser.findByLogin("USER").get());
//        commande2.setDateCommande(LocalDateTime.now().plusMinutes(3));
//        commande2.setPrix(50f);
//        commande2.setEtat(EtatCommande.ANNULE);
//        this.facadeCommande.save(commande2);
//
//        Commande commande3 = new Commande();
//        commande3.setUser(this.facadeUser.findByLogin("USER").get());
//        commande3.setDateCommande(LocalDateTime.now().plusMinutes(10));
//        commande3.setPrix(342.0f);
//        commande3.setEtat(EtatCommande.VALIDE);
//        this.facadeCommande.save(commande3);
//
//        Commande commande4 = new Commande();
//        commande4.setUser(this.facadeUser.findByLogin("ADMIN").get());
//        commande4.setDateCommande(LocalDateTime.now().plusMinutes(10));
//        commande4.setPrix(342.0f);
//        commande4.setEtat(EtatCommande.VALIDE);
//        this.facadeCommande.save(commande4);

    }

    private void initUser() throws Exception {

        if (this.facadeUser.count() == 0) {
            Set<User> users = new HashSet<>();
            do {
                User user = new User();
                user.setLogin(this.faker.funnyName().name());
                user.setPassword(user.getLogin());
                user.setRole(Role.USER);
                users.add(user);

            } while (users.size() != 20);

            for (User u : users) {
                this.facadeUser.save(u);
            }
        }

        User user = new User();
        user.setLogin("USER");
        user.setPassword("USER");
        user.setRole(Role.USER);
        this.facadeUser.save(user);

        User admin = new User();
        admin.setLogin("ADMIN");
        admin.setPassword("ADMIN");
        admin.setRole(Role.ADMIN);
        this.facadeUser.save(admin);
    }

    private void initMarque() {
        try {
            if (this.facadeMarque.count() == 0) {
                Set<Marque> marques = new HashSet<>();
                do {
                    Marque m = new Marque();
                    m.setLibelle(this.faker.company().name());
                    marques.add(m);
                } while (marques.size() != 50);

                for (Marque m : marques) {
                    this.facadeMarque.save(m);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCategorie() {
        try {
            if (this.facadeCategorie.count() == 0) {
                Set<Categorie> categories = new HashSet<Categorie>();
                do {
                    Categorie c = new Categorie();
                    c.setLibelle(this.faker.funnyName().name());
                    categories.add(c);
                } while (categories.size() != 50);

                for (Categorie c : categories) {
                    this.facadeCategorie.save(c);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initProduit() {
        try {
            List<Categorie> categorieList = IterableUtils.toList(this.facadeCategorie.findAll());
            List<Marque> marqueList = IterableUtils.toList(this.facadeMarque.findAll());
            if (this.facadeProduit.count() == 0) {
                Set<Produit> produits = new HashSet<>();
                do {
                    Produit p = new Produit();
                    p.setLibelle(this.faker.commerce().material());
                    p.setDescription(this.faker.lorem().paragraph(2));
                    p.setPrixHT(this.generateRandomFloat(0.99f, 4999.99f));
                    p.setPoidsKg(this.generateRandomFloat(0.1f, 50f));
                    p.setMarque(marqueList.get(this.faker.random().nextInt(0, marqueList.size() - 1)));
                    p.setCategorie(categorieList.get(this.faker.random().nextInt(0, categorieList.size() - 1)));
                    produits.add(p);
                } while (produits.size() != 500);

                for (Produit p : produits) {
                    this.facadeProduit.save(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float generateRandomFloat(float min, float max) {
        if (min >= max)
            throw new IllegalArgumentException("max must be greater than min");
        float result = ThreadLocalRandom.current().nextFloat() * (max - min) + min;
        if (result >= max) // correct for rounding
            result = Float.intBitsToFloat(Float.floatToIntBits(max) - 1);

        result = (float) (Math.round(result * 100.0) / 100.0);
        return result;
    }
}
