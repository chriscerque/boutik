package net.ent.etrs.boutik.view.marque;

import net.ent.etrs.boutik.model.entities.Marque;
import net.ent.etrs.boutik.model.facades.FacadeMarque;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LazyDataModelMarque extends LazyDataModel<Marque> {
    
    @Inject
    private FacadeMarque facadeMarque;
    
    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return this.facadeMarque.count(filterBy);
    }
    
    @Override
    public List<Marque> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.facadeMarque.load(first, pageSize, sortBy, filterBy);
    }
}
