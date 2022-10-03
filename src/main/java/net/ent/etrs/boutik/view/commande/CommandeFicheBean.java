package net.ent.etrs.boutik.view.commande;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.Commande;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeCategorie;
import net.ent.etrs.boutik.model.facades.FacadeCommande;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
import net.ent.etrs.boutik.model.facades.FacadeProduit;
import net.ent.etrs.boutik.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class CommandeFicheBean implements Serializable {

    @Inject
    private FacadeProduit facadeProduit;

    @Inject
    private FacadeCommande facadeCommande;

    @Inject
    private FacadeCategorie facadeCategorie;

    @Inject
    private FacadeMarque facadeMarque;

    @Inject
    @Getter
    private Commande commande;

    @Getter
    private List<Produit> produitList = new ArrayList<>();

    @Getter
    private List<Produit> produitListCommande = new ArrayList<>();

    @Getter
    @Setter
    private List<Marque> marqueList;

    @Getter
    @Setter
    private List<Categorie> categorieList;


    @PostConstruct
    public void init() {
        try {
            this.marqueList = this.facadeMarque.findAll().stream().collect(Collectors.toList());
            this.categorieList = this.facadeCategorie.findAll().stream().collect(Collectors.toList());
            this.produitList.addAll(this.facadeProduit.findAll());
            Commande c = (Commande) JsfUtils.getFromFlashScope(CommandeListeBean.FLASH_COMMANDE);
            //TODO SOUT
            System.out.println("commande : " + c);
            if (Objects.nonNull(c)) {
                this.commande = c;
            }
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors du chargement de la page.");
        }
    }

    public void valider() {
        try {
            this.facadeCommande.save(this.commande);
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO, "Commande enregistré avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement de la commande.");
        }
    }

//    public Produit getvalue()
}
