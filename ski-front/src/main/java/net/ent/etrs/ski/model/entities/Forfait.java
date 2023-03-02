package net.ent.etrs.ski.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etrs.ski.model.entities.references.ConstantesModel;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(of = {"nom"})
public class Forfait extends AbstractEntity
{
    @Getter
    @Setter
    private String nom;

    @Getter
    @Setter
    private BigDecimal prixJournalier;

    @Getter @Setter
    private List<Piste> pistes = new ArrayList<>();


}
