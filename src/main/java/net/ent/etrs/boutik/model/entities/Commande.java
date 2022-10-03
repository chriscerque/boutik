package net.ent.etrs.boutik.model.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@EqualsAndHashCode(callSuper = false, of = {"user", "dateCommande"})
@ToString(callSuper = true, of = {"user", "dateCommande", "prix", "etat"})
@NoArgsConstructor

@Entity
@Table(name = "COMMANDE", uniqueConstraints = @UniqueConstraint(name = "COMMANDE__USER_ID_DATE_COMMANDE__UK", columnNames = {"USER_ID", "DATE_COMMANDE"}))
public class Commande extends AbstractEntity {

    @Getter
    @Setter
    @NotNull(message = "l'attribut prixHT ne peut pas etre null")
    @Positive(message = "La prix doit être positif")
    @Column(name = "PRIX_HT", nullable = false)
    private Float prix;


    @NotNull(message = "La commande doit contenir des produits.")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "COMMANDE_PRODUIT",
            joinColumns = @JoinColumn(name = "COMMANDE_ID", nullable = false, foreignKey = @ForeignKey(name = "COMMANDE_PRODUIT__COMMANDE_ID__FK")))
    @MapKeyJoinColumn(name = "PRODUIT_ID", nullable = false, foreignKey = @ForeignKey(name = "COMMANDE_PRODUIT__PRODUIT_ID__FK"))
    @Column(name = "QUANTITE", nullable = false)
    private Map<Produit, Integer> produits = new HashMap<>();


    @Getter
    @Setter
    @NotNull(message = "L'utilisateur doit être renseigné.")
    @Valid
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(name = "COMMANDE__USER_ID__FK"))
    private User user;


    @Getter
    @Setter
    @NotNull(message = "L'état de la commande doit être renseingé.")
    @Enumerated(EnumType.STRING)
    @Column(name = "ETAT", nullable = false, length = 100)
    private EtatCommande etat;

    @Getter
    @Setter
    @NotNull(message = "La date de la commande doit être renseingé.")
    @FutureOrPresent(message = "La date de la commande ne peut être dans le passé.")
    @Column(name = "DATE_COMMANDE", nullable = false)
    private LocalDateTime dateCommande;


    public Map<Produit, Integer> getProduits() {
        return Map.copyOf(produits);
    }

    public void ajouterProduit(final Produit produit) throws Exception {
        if (Objects.isNull(produit)) {
            throw new Exception("Le produit doit être renseigné.");
        }

        this.produits.computeIfPresent(produit, (k, v) -> v + 1);
        this.produits.computeIfAbsent(produit, v -> 1);

    }
}
