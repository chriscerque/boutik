package net.ent.etrs.boutik.view.commande;

import net.ent.etrs.boutik.model.entities.Commande;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeCommande;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LazyDataModelCommande extends LazyDataModel<Commande> {
    
    @Inject
    private FacadeCommande facadeCommande;
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facadeCommande.count(filterBy);
    }
    
    @Override
    public List<Commande> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.facadeCommande.load(first, pageSize, sortBy, filterBy);
    }
}
