package net.ent.etrs.boutik.model.daos.impl;

import net.ent.etrs.boutik.model.daos.DaoCommande;
import net.ent.etrs.boutik.model.daos.JpaBaseDao;
import net.ent.etrs.boutik.model.entities.Commande;
import net.ent.etrs.boutik.model.entities.EtatCommande;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DaoCommandeImpl extends JpaBaseDao<Commande, Serializable> implements DaoCommande {

    @Override
    public List<Commande> findAll(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

        String sql = "SELECT c FROM Commande c WHERE 1=1 ";

        String user = null;
        Float prix = null;
        LocalDate dateCommande = null;
        EtatCommande etatCommande = null;


        if (filterBy.containsKey("user.login")) {
            user = (String) filterBy.get("user.login").getFilterValue();
        }

        if (filterBy.containsKey("prix")) {
            prix = Float.parseFloat((String) filterBy.get("prix").getFilterValue());
        }

        if (filterBy.containsKey("dateCommande")) {
            dateCommande = (LocalDate) filterBy.get("dateCommande").getFilterValue();
        }

        if (filterBy.containsKey("etat")) {
            etatCommande = (EtatCommande) filterBy.get("etat").getFilterValue();
        }


        if (user != null) {
            sql += " AND LOWER(c.user.login) LIKE :user ";
        }

        if (prix != null) {
            sql += " AND c.prix = :prix ";
        }

        if (dateCommande != null) {
            sql += " AND DATE(c.dateCommande) = :dateCommande ";
        }

        if (etatCommande != null) {
            sql += " AND c.etat = :etat ";
        }


        if (!sortBy.isEmpty()) {
            sql += " ORDER BY ";
            for (Map.Entry<String, SortMeta> sort : sortBy.entrySet()) {
                sql += " c." + sort.getValue().getField() + " " + (sort.getValue().getOrder().equals(SortOrder.ASCENDING) ? "ASC" : "DESC") + ",";
            }
            sql = sql.substring(0, sql.length() - 1);
        } else {
            sql += " ORDER BY c.user.login ASC ";
        }

        TypedQuery<Commande> q = this.em.createQuery(sql, Commande.class);

        if (user != null) {
            q.setParameter("user", user.toLowerCase() + "%");
        }

        if (prix != null) {
            q.setParameter("prix", prix);
        }

        if (dateCommande != null) {
            q.setParameter("dateCommande", dateCommande);
        }

        if (etatCommande != null) {
            //TODO sout
            System.out.println(String.format("q.setParameter(\"etat\", %s);", etatCommande));
            q.setParameter("etat", etatCommande);
        }

        q.setFirstResult(first);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {

        String sql = "SELECT COUNT(c) FROM Commande c WHERE 1=1 ";

        String user = null;
        Float prix = null;
        LocalDate dateCommande = null;
        EtatCommande etatCommande = null;

        //TODO sout
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        filterBy.keySet().forEach(System.out::println);

        if (filterBy.containsKey("user.login")) {
            user = (String) filterBy.get("user.login").getFilterValue();
        }

        if (filterBy.containsKey("prix")) {
            prix = Float.parseFloat((String) filterBy.get("prix").getFilterValue());
        }

        if (filterBy.containsKey("dateCommande")) {
            dateCommande = (LocalDate) filterBy.get("dateCommande").getFilterValue();
        }

        if (filterBy.containsKey("etat")) {
            etatCommande = (EtatCommande) filterBy.get("etat").getFilterValue();
        }


        if (user != null) {
            sql += " AND LOWER(c.user.login) LIKE :user ";
        }

        if (prix != null) {
            sql += " AND c.prix = :prix ";
        }

        if (dateCommande != null) {
            sql += " AND DATE(c.dateCommande) = :dateCommande ";
        }

        if (etatCommande != null) {
            sql += " AND c.etat = :etat ";
        }

        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (user != null) {
            q.setParameter("user", user.toLowerCase() + "%");
        }

        if (prix != null) {
            q.setParameter("prix", prix);
        }

        if (dateCommande != null) {
            q.setParameter("dateCommande", dateCommande);
        }

        if (etatCommande != null) {
            q.setParameter("etat", etatCommande);
        }

        return q.getSingleResult().intValue();
    }
}
