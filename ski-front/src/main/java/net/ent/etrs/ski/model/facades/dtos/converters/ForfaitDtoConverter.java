package net.ent.etrs.ski.model.facades.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.AbstractEntity;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.dtos.ForfaitDto;
import net.ent.etrs.ski.utils.CDIUtils;
import org.apache.commons.collections4.IterableUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
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
                .pistes(forfait.getPistes().stream().map(AbstractEntity::getId).collect(Collectors.toList()))
                .build();
    }

    public static List<ForfaitDto> toDtoList(List<Forfait> forfaits) {
        return forfaits.stream()
                .map(ForfaitDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public static List<Forfait> toEntityList(List<ForfaitDto> forfaits) throws BusinessException {
        List<Forfait> forfaits2 = new ArrayList<>();
        for(ForfaitDto f : forfaits){
            forfaits2.add(ForfaitDtoConverter.toEntity(f));
        }
        return forfaits2;
    }

    public static Forfait toEntity(ForfaitDto forfaitDto) throws BusinessException {
        Forfait forfait = new Forfait();
        forfait.setId(forfaitDto.getId());
        forfait.setNom(forfaitDto.getNom());
        forfait.setPrixJournalier(new BigDecimal(forfaitDto.getPrixJournalier()));

        forfait.getPistes().addAll(IterableUtils.toList(facadePiste.findAllByForfait(forfait.getId())));

        return forfait;
    }

}
