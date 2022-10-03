package net.ent.etrs.boutik.view.categorie;

import lombok.Getter;
import lombok.Setter;
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
import java.util.stream.Collectors;

@Named
@ViewScoped
public class CategorieListeBean implements Serializable {
    
    public final static String FLASH_CATEGORIE = "FLASH_CATEGORIE";
    
    @Inject
    private FacadeCategorie facadeCategorie;

    @Getter
    @Setter
    @Inject
    private LazyDataModelCategorie categorieList;
    
    @Getter
    private List<Categorie> categorieListFilter = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            this.categorieListFilter.addAll(this.facadeCategorie.findAll());
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors du chargement de la page.");
        }
    }

    public void delete(Categorie categorie) {
        try {
            this.facadeCategorie.delete(categorie);
            init();
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO,"Catégorie supprimé avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors de la suppression de la catégorie");
        }
    }
    
    public void modifier(Categorie categorie) {
        JsfUtils.putInFlashScope(CategorieListeBean.FLASH_CATEGORIE, categorie);
    }
}
