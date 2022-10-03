package net.ent.etrs.boutik.model.facades;

import net.ent.etrs.boutik.model.daos.DaoProduit;
import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Produit;
import org.apache.commons.collections4.IterableUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@Local
public class FacadeProduit implements Serializable {

    @Inject
    private DaoProduit daoProduit;

    public Optional<Produit> findById(Long id) throws Exception {
        try {
            return this.daoProduit.find(id);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Set<Produit> findAll() throws Exception {
        try {
            return IterableUtils.toList(this.daoProduit.findAll()).stream().collect(Collectors.toSet());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Optional<Produit> save(Produit produit) throws Exception {
        try {
            return this.daoProduit.save(produit);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public void delete(Produit produit) throws Exception {
        try {
            this.daoProduit.delete(produit.getId());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Integer count() throws Exception {
        try {
            return Math.toIntExact(this.daoProduit.count());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        return this.daoProduit.count(filterBy);
    }
    
    public List<Produit> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.daoProduit.findAll(first, pageSize, sortBy, filterBy);
    }
}
