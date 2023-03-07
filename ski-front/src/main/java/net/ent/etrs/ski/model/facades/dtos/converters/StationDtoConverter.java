package net.ent.etrs.ski.model.facades.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.AbstractEntity;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.dtos.StationDto;
import net.ent.etrs.ski.utils.CDIUtils;
import org.apache.commons.collections4.IterableUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StationDtoConverter {
    
    private static FacadePiste facadePiste;
    private static FacadeForfait facadeForfait;
    
    static {
        StationDtoConverter.facadePiste = CDIUtils.getBean(FacadePiste.class);
        StationDtoConverter.facadeForfait = CDIUtils.getBean(FacadeForfait.class);
    }
    
    public static StationDto toDto(Station station) {
        return StationDto.builder()
                .id(station.getId())
                .nom(station.getNom())
                .ville(station.getVille())
                .nbHabitants(station.getNbHabitants())
                .altitude(station.getAltitude())
                .etat(station.getEtat())
                .photo(station.getPhoto() != null ? Base64.getEncoder().encodeToString(station.getPhoto()) : null)
                .pistes(station.getPistes().stream().map(AbstractEntity::getId).collect(Collectors.toList()))
                .forfaits(station.getLstForfaits().stream().map(AbstractEntity::getId).collect(Collectors.toList()))
                .build();
    }
    
    public static List<StationDto> toDtoList(List<Station> stations) {
        return stations.stream()
                .map(StationDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public static List<Station> toEntityList(List<StationDto> stations) throws BusinessException {
        List<Station> stations2 = new ArrayList<>();
        for(StationDto s : stations){
            stations2.add(StationDtoConverter.toEntity(s));
        }
        return stations2;
    }
    
    public static Station toEntity(StationDto stationDto) throws BusinessException {
        Station station = new Station();
        station.setId(stationDto.getId());
        station.setNom(stationDto.getNom());
        station.setVille(stationDto.getVille());
        station.setNbHabitants(stationDto.getNbHabitants());
        station.setAltitude(stationDto.getAltitude());
        station.setEtat(stationDto.getEtat());
        station.setPhoto(stationDto.getPhoto() != null ? Base64.getDecoder().decode(stationDto.getPhoto()) : null);

        station.getPistes().addAll(IterableUtils.toList(facadePiste.findAllByStation(station.getId())));
        station.getLstForfaits().addAll(IterableUtils.toList(facadeForfait.findAllByStation(station.getId())));

        return station;
    }
}
