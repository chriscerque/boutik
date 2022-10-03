package net.ent.etrs.boutik.view.marque;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
import net.ent.etrs.boutik.utils.JsfUtils;
import org.primefaces.model.LazyDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class MarqueListeBean implements Serializable {
    
    public final static String FLASH_MARQUE = "FLASH_MARQUE";

    @Inject
    private FacadeMarque facadeMarque;

    @Getter
    @Setter
    @Inject
    private LazyDataModelMarque marqueList;

    @PostConstruct
    public void init() {
    }

    public void delete(Marque marque) {
        try {
            this.facadeMarque.delete(marque);
            init();
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO,"Marque supprimé avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors de la suppression de la marque.");
        }
    }
    
    public void modifier(Marque marque) {
        JsfUtils.putInFlashScope(MarqueListeBean.FLASH_MARQUE, marque);
    }
}
