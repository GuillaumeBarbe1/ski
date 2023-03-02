package net.ent.etrs.ski.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etrs.ski.model.entities.references.Etat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "STATION", uniqueConstraints = @UniqueConstraint(name = "STATION__NOM__UK", columnNames = {"NOM"}))
@EqualsAndHashCode(callSuper = false, of = {"nom"})
@ToString(of = {"nom", "ville", "nbHabitants", "altitude", "etat"})
public class Station extends AbstractEntity{

    @Getter
    @Setter
    @Column(name = "NOM", nullable = false, length = 50)
    private String nom;

    @Getter @Setter
    @Column(name = "VILLE", nullable = false, length = 100)
    private String ville;

    @Getter @Setter
    @Column(name = "NB_HABITANTS", nullable = false)
    private Integer nbHabitants;
    
    @Getter @Setter
    @Column(name = "ALTITUDE", nullable = false)
    private Integer altitude;

    @Getter @Setter
    @Column(name = "ETAT", nullable = false)
    @Enumerated(EnumType.STRING)
    private Etat etat;
    
    @Getter @Setter
    @Lob
    @Column(name = "PHOTO", nullable = true)
    private byte[] photo;
    @Getter @Setter
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATION_ID", foreignKey = @ForeignKey(name = "PISTE__STATION___STATION_ID___FK"))
    private List<Piste> pistes = new ArrayList<>();

    @Getter @Setter
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(name ="STATION_FORFAIT", joinColumns = @JoinColumn(name = "FORFAIT_ID"),inverseJoinColumns = @JoinColumn(name="PISTE_ID"))
    private List<Forfait> lstForfaits = new ArrayList<>();


}
