package net.ent.etrs.boutik.view.marque;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
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
public class MarqueFicheBean implements Serializable {
    
    @Inject
    private FacadeMarque facadeMarque;
    
    @Inject
    @Getter
    private Marque marque;
    
    @Getter @Setter
    private UploadedFile file;
    
    @PostConstruct
    public void init() {
        try {
            Marque m = (Marque) JsfUtils.getFromFlashScope(MarqueListeBean.FLASH_MARQUE);
            if (Objects.nonNull(m)) {
                this.marque = m;
            }
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors du chargement de la page.");
        }
    }
    
    public void valider() {
        try {
            if(this.file != null && this.file.getFileName() != null) {
                this.marque.setLogo(this.file.getContent());
            }
            this.facadeMarque.save(this.marque);
            JsfUtils.sendMessage(FacesMessage.SEVERITY_INFO,"Marque enregistré avec succès.");
        } catch (Exception e) {
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Erreur lors de l'enregistrement de la marque.");
        }
    }
}
