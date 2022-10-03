package net.ent.etrs.boutik.model.facades;

import net.ent.etrs.boutik.model.daos.DaoMarque;
import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Marque;
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
public class FacadeMarque implements Serializable {
    @Inject
    private DaoMarque daoMarque;

    public Optional<Marque> findById(Long id) throws Exception {
        try {
            return this.daoMarque.find(id);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Set<Marque> findAll() throws Exception {
        try {
            return IterableUtils.toList(this.daoMarque.findAll()).stream().collect(Collectors.toSet());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Optional<Marque> save(Marque marque) throws Exception {
        try {
            return this.daoMarque.save(marque);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public void delete(Marque marque) throws Exception {
        try {
            this.daoMarque.delete(marque.getId());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Integer count() throws Exception {
        try {
            return Math.toIntExact(this.daoMarque.count());
        } catch (DaoException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    
    public List<Marque> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.daoMarque.findAll(first, pageSize, sortBy, filterBy);
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        return this.daoMarque.count(filterBy);
    }
}
