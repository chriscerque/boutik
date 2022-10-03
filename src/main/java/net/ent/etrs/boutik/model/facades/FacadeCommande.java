package net.ent.etrs.boutik.model.facades;

import net.ent.etrs.boutik.model.daos.DaoCommande;
import net.ent.etrs.boutik.model.daos.exceptions.DaoException;
import net.ent.etrs.boutik.model.entities.Commande;
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
public class FacadeCommande implements Serializable {

    @Inject
    private DaoCommande daoCommande;

    public Optional<Commande> findById(Long id) throws Exception {
        try {
            return this.daoCommande.find(id);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Set<Commande> findAll() throws Exception {
        try {
            return IterableUtils.toList(this.daoCommande.findAll()).stream().collect(Collectors.toSet());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Optional<Commande> save(Commande commande) throws Exception {
        try {
            return this.daoCommande.save(commande);
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public void delete(Commande commande) throws Exception {
        try {
            this.daoCommande.delete(commande.getId());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }

    public Integer count() throws Exception {
        try {
            return Math.toIntExact(this.daoCommande.count());
        } catch (DaoException e) {
            throw new Exception(e);
        }
    }
    
    public int count(Map<String, FilterMeta> filterBy) {
        return this.daoCommande.count(filterBy);
    }
    
    public List<Commande> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        return this.daoCommande.findAll(first, pageSize, sortBy, filterBy);
    }
}
