package net.ent.etrs.boutik.model.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(of = {"libelle"})
@ToString(callSuper = true, of = {"libelle"})
@NoArgsConstructor

@Entity
@Table(name = "CATEGORIE", uniqueConstraints = @UniqueConstraint(name = "CATEGORIE__LIBELLE__UK", columnNames = {"LIBELLE"}))
public class Categorie extends AbstractEntity{

    @Getter
    @Setter
    @NotBlank(message = "l'attribut libelle ne peut pas etre blanc")
    @Column(name = "libelle", length = 100, nullable = false)
    private String libelle;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERE", foreignKey = @ForeignKey(name = "CATEGORIE_CATEGORIE__PERE__FK"))
    private Categorie pere;
}
