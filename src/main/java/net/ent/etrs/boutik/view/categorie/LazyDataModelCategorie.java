package net.ent.etrs.boutik.view.categorie;

import net.ent.etrs.boutik.model.entities.Categorie;
import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.facades.FacadeCategorie;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LazyDataModelCategorie extends LazyDataModel<Categorie> {
    
    @Inject
    private FacadeCategorie facadeCategorie;
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facadeCategorie.count(filterBy);
    }
    
    @Override
    public List<Categorie> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.facadeCategorie.load(first, pageSize, sortBy, filterBy);
    }
}
