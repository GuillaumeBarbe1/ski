package net.ent.etrs.ski.model.facades.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.model.entities.references.Etat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemonteeDto {

    private Long id;

    private String nom;

    private Integer nbPlaceUnite;

    private Etat etat;


}
