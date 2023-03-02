package net.ent.etrs.ski.model.facades.dtos.converters;

import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.dtos.RemonteeDto;

import java.util.List;
import java.util.stream.Collectors;

public class RemonteeDtpConv {

    // @NoArgsConstructor(access = AccessLevel.PRIVATE)

    public static RemonteeDto toDto(Remontee remontee) {
        return RemonteeDto.builder()
                .id(remontee.getId())
                .nom(remontee.getNom())
                .build();
    }

    public static List<RemonteeDto> toDtoList(List<Remontee> remontees) {
        return remontees.stream()
                .map(RemonteeDtpConv::toDto)
                .collect(Collectors.toList());
    }

    public static List<Remontee> toEntityList(List<RemonteeDto> remontees) {
        return remontees.stream()
                .map(RemonteeDtpConv::toEntity)
                .collect(Collectors.toList());
    }

    public static Remontee toEntity(RemonteeDto remonteeDto) {
        Remontee remontee = new Remontee();
        remontee.setId(remonteeDto.getId());
        remontee.setNom(remonteeDto.getNom());

        return remontee;
    }
}
