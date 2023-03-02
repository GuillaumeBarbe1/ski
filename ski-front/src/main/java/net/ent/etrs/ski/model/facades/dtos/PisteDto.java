package net.ent.etrs.ski.model.facades.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.model.entities.references.Etat;
import net.ent.etrs.ski.model.entities.references.Niveau;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PisteDto {

    private Long id;

    private String nom;

    private Niveau niveau;

    private Integer longueur;

    private Double penteMoy;

    private Etat etat;

    private List<Long> remontees = new ArrayList<>();

}
