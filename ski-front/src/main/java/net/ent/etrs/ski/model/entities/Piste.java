package net.ent.etrs.ski.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etrs.ski.model.entities.references.ConstantesModel;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Niveau;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(callSuper = true, of = {"etat", "niveau", "longueur", "penteMoy"})
public class Piste extends AbstractEntity {
    
    //LBK
    @Getter
    @Setter
    //BV
    @NotNull(message = ConstantesModel.PISTE_NOM_NULL)
    @Size(min = ConstantesModel.PISTE_NOM_TAILLE_MIN, max = ConstantesModel.PISTE_NOM_TAILLE_MAX, message = ConstantesModel.PISTE_NOM_TAILLE_INCORRECTE)
    private String nom;

    //LBK
    @Getter
    @Setter
    private Niveau niveau;

    //LBK
    @Getter
    @Setter
    //BV
    @NotNull(message = ConstantesModel.PISTE_LONGUEUR_NULL)
    @Positive(message = ConstantesModel.PISTE_LONGUEUR_INCORRECTE)
    private Integer longueur;

    //LBK
    @Getter
    @Setter
    //BV
    @NotNull(message = ConstantesModel.PISTE_PENTE_MOYENNE_NULL)
    @Positive(message = ConstantesModel.PISTE_PENTE_MOYENNE_INCORRECTE)
    @Max(ConstantesModel.PISTE_PENTE_MOYENNE_TROP_GRANDE)
    private Double penteMoy;

    //LBK
    @Getter
    @Setter
    //BV
    @NotNull(message = ConstantesModel.PISTE_ETAT_NULL)
    private Etat etat;

    @Getter
    @Setter
    private List<Remontee> remontees = new ArrayList<>();


}
