package net.ent.etrs.ski.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.api.dtos.ForfaitDto;
import net.ent.etrs.ski.model.facades.api.dtos.StationDto;
import net.ent.etrs.ski.utils.CDIUtils;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ForfaitDtoConverter {

    private static FacadePiste facadePiste;

    static {
        ForfaitDtoConverter.facadePiste = CDIUtils.getBean(FacadePiste.class);
    }

    public static ForfaitDto toDto(Forfait forfait) {
        return ForfaitDto.builder()
                .id(forfait.getId())
                .nom(forfait.getNom())
                .prixJournalier(forfait.getPrixJournalier().toEngineeringString())
                .build();
    }

    public static List<ForfaitDto> toDtoList(List<Forfait> forfaits) {
        return forfaits.stream()
                .map(ForfaitDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public static Forfait toEntity(ForfaitDto forfaitDto) throws BusinessException {
        Forfait forfait = new Forfait();
        forfait.setId(forfaitDto.getId());
        forfait.setNom(forfaitDto.getNom());
        forfait.setPrixJournalier(new BigDecimal(forfaitDto.getPrixJournalier()));

        if (forfaitDto.getPistes() != null) {
            for (Long idPiste : forfaitDto.getPistes()) {
                Piste p = ForfaitDtoConverter.facadePiste.find(idPiste).orElseThrow(BusinessException::new);
                forfait.getPistes().add(p);
            }
        }

        return forfait;
    }

}
