package net.ent.etrs.boutik.view.commande;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.boutik.model.entities.Commande;
import net.ent.etrs.boutik.model.entities.EtatCommande;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeCommande;
import net.ent.etrs.boutik.model.facades.FacadeProduit;
import net.ent.etrs.boutik.utils.JsfUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class CommandeListeBean implements Serializable {

    public final static String FLASH_COMMANDE = "FLASH_COMMANDE";
    @PersistenceContext
    private EntityManager em;
    @Inject
    private FacadeCommande facadeCommande;

    @Inject
    private FacadeProduit facadeProduit;

    @Getter
    @Setter
    @Inject
    private LazyDataModelCommande commandeList;

    @Getter
    @Setter
    private List<Produit> produitList;

    @Getter
    @Setter
    @Inject
    private Commande commande;

    @Getter
    private List<EtatCommande> etatCommandes = new ArrayList<>();


    @PostConstruct
    public void init() {
        try {
            this.produitList = this.facadeProduit.findAll().stream().collect(Collectors.toList());
            this.etatCommandes.addAll(Arrays.asList(EtatCommande.values()));
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors du chargement de la page.");
        }
    }

    public void delete(Commande commande) {
        try {
            this.facadeCommande.delete(commande);
            init();
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO, "Commande supprimée avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la suppression de la commande.");
        }
    }

    public void modifier(Commande commande) {
        JsfUtils.putInFlashScope(CommandeListeBean.FLASH_COMMANDE, commande);
    }
}
