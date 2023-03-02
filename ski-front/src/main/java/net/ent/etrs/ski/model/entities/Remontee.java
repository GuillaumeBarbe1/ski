package net.ent.etrs.ski.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etrs.ski.model.entities.references.ConstantesModel;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(of = {"nom"})
public class Remontee extends AbstractEntity
{
    @Getter
    @Setter
    private String nom;

    @Getter
    @Setter
    private Integer nbPlaceUnite;

    //LBK
    @Getter
    @Setter
    //BV
    @NotNull(message = ConstantesModel.PISTE_ETAT_NULL)
    private Etat etat;

}
