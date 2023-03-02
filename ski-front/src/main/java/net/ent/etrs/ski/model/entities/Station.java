package net.ent.etrs.ski.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etrs.ski.model.entities.references.Etat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(of = {"nom", "ville", "nbHabitants", "altitude", "etat"})
public class Station extends AbstractEntity{

    @Getter
    @Setter
    private String nom;

    @Getter @Setter
    private String ville;

    @Getter @Setter
    private Integer nbHabitants;
    
    @Getter @Setter
    private Integer altitude;

    @Getter @Setter
    private Etat etat;
    
    @Getter @Setter
    private byte[] photo;
    @Getter @Setter
    private List<Piste> pistes = new ArrayList<>();

    @Getter @Setter
    private List<Forfait> lstForfaits = new ArrayList<>();


}
