package net.ent.etrs.boutik.model.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.swing.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@EqualsAndHashCode(callSuper = false, of = {"libelle"})
@ToString(callSuper = true, of = {"libelle"})
@NoArgsConstructor

@Entity
@Table(name = "MARQUE", uniqueConstraints = @UniqueConstraint(name = "MARQUE__LIBELLE__UK", columnNames = {"LIBELLE"}))
public class Marque extends AbstractEntity{

    @Getter
    @Setter
    @NotBlank(message = "l'attribut libelle ne peut pas etre blanc")
    @Column(name = "libelle", length = 500, nullable = false)
    private String libelle;

    @Getter
    @Setter
    @Lob
    @Column(name = "LOGO", nullable = true)
    private byte[] logo;
}
