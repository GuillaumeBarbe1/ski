package net.ent.etrs.ski.model.facades.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.dtos.RemonteeDto;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RemonteeDtoConverter {

    public static RemonteeDto toDto(Remontee remontee) {
        return RemonteeDto.builder()
                .id(remontee.getId())
                .nom(remontee.getNom())
                .nbPlaceUnite(remontee.getNbPlaceUnite())
                .etat(remontee.getEtat())
                .build();
    }

    public static List<RemonteeDto> toDtoList(List<Remontee> remontees) {
        return remontees.stream()
                .map(RemonteeDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public static List<Remontee> toEntityList(List<RemonteeDto> remontees) {
        return remontees.stream()
                .map(RemonteeDtoConverter::toEntity)
                .collect(Collectors.toList());
    }

    public static Remontee toEntity(RemonteeDto remonteeDto){
        Remontee remontee = new Remontee();
        remontee.setId(remonteeDto.getId());
        remontee.setNom(remonteeDto.getNom());
        remontee.setNbPlaceUnite(remonteeDto.getNbPlaceUnite());
        remontee.setEtat(remonteeDto.getEtat());

        return remontee;
    }

}
