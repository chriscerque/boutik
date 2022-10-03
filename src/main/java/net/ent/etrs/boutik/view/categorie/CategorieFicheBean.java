package net.ent.etrs.boutik.view.categorie;

import lombok.Getter;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.facades.FacadeCategorie;
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

@Named
@ViewScoped
public class CategorieFicheBean implements Serializable {
    
    @Inject
    private FacadeCategorie facadeCategorie;
    
    @Inject
    @Getter
    private Categorie categorie;
    
    @Getter
    private List<Categorie> categorieList = new ArrayList<>();
    
    @PostConstruct
    public void init() {
        try {
            this.categorieList.addAll(this.facadeCategorie.findAll());
            Categorie c = (Categorie) JsfUtils.getFromFlashScope(CategorieListeBean.FLASH_CATEGORIE);
            if (Objects.nonNull(c)) {
                this.categorie = c;
                this.categorieList.remove(c);
            }
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors du chargement de la page.");
        }
    }
    
    public void valider() {
        try {
            this.facadeCategorie.save(this.categorie);
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO,"Catégorie enregistré avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors de l'enregistrement de la catégorie.");
        }
    }
}
