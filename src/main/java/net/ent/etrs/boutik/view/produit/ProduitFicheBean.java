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
import net.ent.etrs.boutik.view.categorie.CategorieListeBean;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Named
@ViewScoped
public class ProduitFicheBean implements Serializable {
    
    @Inject
    private FacadeProduit facadeProduit;
    
    @Inject
    private FacadeCategorie facadeCategorie;
    
    @Inject
    private FacadeMarque facadeMarque;
    
    @Inject
    @Getter
    private Produit produit;
    
    @Getter
    private List<Categorie> categorieList = new ArrayList<>();
    
    @Getter
    private List<Marque> marqueList = new ArrayList<>();
    
    @Getter @Setter
    private UploadedFile file;
    
    @PostConstruct
    public void init() {
        try {
            this.categorieList.addAll(this.facadeCategorie.findAll());
            this.marqueList.addAll(this.facadeMarque.findAll());
            Produit p = (Produit) JsfUtils.getFromFlashScope(ProduitListeBean.FLASH_PRODUIT);
            if (Objects.nonNull(p)) {
                this.produit = p;
            }
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors du chargement de la page.");
        }
    }
    
    public void valider() {
        try {
            if(this.file != null && this.file.getFileName() != null) {
                this.produit.setImage(this.file.getContent());
            }
            this.facadeProduit.save(this.produit);
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO,"Produit enregistré avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors de l'enregistrement du produit.");
        }
    }
}
