package net.ent.etrs.ski.model.facades.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForfaitDto {

    private Long id;

    private String nom;

    private String prixJournalier;

    private List<Long> pistes = new ArrayList<>();


}
