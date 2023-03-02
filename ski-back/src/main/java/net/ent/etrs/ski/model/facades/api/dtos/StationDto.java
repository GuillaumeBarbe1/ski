package net.ent.etrs.ski.model.facades.api.dtos;

import lombok.*;
import net.ent.etrs.ski.model.entities.references.Etat;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {
    private Long id;
    private String nom;
    private String ville;
    private Integer nbHabitants;
    private Integer altitude;
    private Etat etat;
    private String photo;
    private List<Long> pistes = new ArrayList<>();
    private List<Long> forfaits = new ArrayList<>();
}
