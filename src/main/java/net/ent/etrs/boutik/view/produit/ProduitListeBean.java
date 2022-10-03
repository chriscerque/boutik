package net.ent.etrs.boutik.view.produit;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeCategorie;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
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
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class ProduitListeBean implements Serializable {
    
    @PersistenceContext
    private EntityManager em;
    
    public final static String FLASH_PRODUIT = "FLASH_PRODUIT";
    
    @Inject
    private FacadeCategorie facadeCategorie;
    
    @Inject
    private FacadeMarque facadeMarque;

    @Inject
    private FacadeProduit facadeProduit;

    @Getter
    @Setter
    @Inject
    private LazyDataModelProduit produitList;
    
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
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors du chargement de la page.");
        }
    }

    public void delete(Produit produit) {
        try {
            this.facadeProduit.delete(produit);
            init();
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO,"Produit supprimé avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors de la suppression du produit.");
        }
    }
    
    public void modifier(Produit produit) {
        JsfUtils.putInFlashScope(ProduitListeBean.FLASH_PRODUIT, produit);
    }
}
