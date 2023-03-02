package net.ent.etrs.ski.model.facades.dtos.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.Piste;
import net.ent.etrs.ski.model.entities.Remontee;
import net.ent.etrs.ski.model.facades.FacadeRemontee;
import net.ent.etrs.ski.model.facades.dtos.PisteDto;
import net.ent.etrs.ski.utils.CDIUtils;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PisteDtoConverter {

    private static FacadeRemontee facadeRemontee;

    static {
        PisteDtoConverter.facadeRemontee = CDIUtils.getBean(FacadeRemontee.class);
    }

    public static PisteDto toDto(Piste piste) {
        return PisteDto.builder()
                .id(piste.getId())
                .nom(piste.getNom())
                .niveau(piste.getNiveau())
                .longueur(piste.getLongueur())
                .penteMoy(piste.getPenteMoy())
                .etat(piste.getEtat())
                .build();
    }

    public static List<PisteDto> toDtoList(List<Piste> pistes) {
        return pistes.stream()
                .map(PisteDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    public static Piste toEntity(PisteDto pisteDto) throws BusinessException {
        Piste piste = new Piste();
        piste.setId(pisteDto.getId());
        piste.setNom(pisteDto.getNom());
        piste.setNiveau(pisteDto.getNiveau());
        piste.setLongueur(pisteDto.getLongueur());
        piste.setPenteMoy(pisteDto.getPenteMoy());
        piste.setEtat(pisteDto.getEtat());

        if (pisteDto.getRemontees() != null) {
            for (Long idRemontee : pisteDto.getRemontees()) {
                Remontee r = PisteDtoConverter.facadeRemontee.find(idRemontee).orElseThrow(BusinessException::new);
                piste.getRemontees().add(r);
            }
        }

        return piste;
    }

}
