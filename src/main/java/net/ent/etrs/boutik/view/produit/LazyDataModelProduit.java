package net.ent.etrs.boutik.view.produit;

import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.entities.Produit;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
import net.ent.etrs.boutik.model.facades.FacadeProduit;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LazyDataModelProduit extends LazyDataModel<Produit> {
    
    @Inject
    private FacadeProduit facadeProduit;
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facadeProduit.count(filterBy);
    }
    
    @Override
    public List<Produit> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.facadeProduit.load(first, pageSize, sortBy, filterBy);
    }
}
