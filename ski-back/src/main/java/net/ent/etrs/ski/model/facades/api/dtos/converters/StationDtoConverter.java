package net.ent.etrs.ski.model.facades.api.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Forfait;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Station;
import net.ent.etrs.ski.model.facades.FacadeForfait;
import net.ent.etrs.ski.model.facades.FacadePiste;
import net.ent.etrs.ski.model.facades.api.dtos.StationDto;
import net.ent.etrs.ski.utils.CDIUtils;

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
                .build();
    }
    
    public static List<StationDto> toDtoList(List<Station> stations) {
        return stations.stream()
                .map(StationDtoConverter::toDto)
                .collect(Collectors.toList());
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
        
        if (stationDto.getPistes() != null) {
            for (Long idPiste : stationDto.getPistes()) {
                Piste p = StationDtoConverter.facadePiste.find(idPiste).orElseThrow(BusinessException::new);
                station.getPistes().add(p);
            }
        }
        
        if (stationDto.getForfaits() != null) {
            for (Long idForfait : stationDto.getForfaits()) {
                Forfait f = StationDtoConverter.facadeForfait.find(idForfait).orElseThrow(BusinessException::new);
                station.getLstForfaits().add(f);
            }
        }
        return station;
    }
}
