package net.ent.etrs.ski.validators;


import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.facades.FacadeStation;
import net.ent.etrs.ski.view.station.StationFicheBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class DoublonValidator implements Validator<String>, Serializable
{
    @Inject
    private StationFicheBean mb;

    @Inject
    private FacadeStation facadeStation;

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException
    {
            try
            {
                Long idStation = facadeStation.findByVille(value);
                // si j'ai une ville déja enregistrée avec le même nom et son id est different du mien.
                if(idStation!=0 && !idStation.equals(mb.getStation().getId()))
                {
                    throw new ValidatorException((new FacesMessage("Doublon détécté")));
                }
            }
            catch (BusinessException e)
            {
                throw new ValidatorException((new FacesMessage("Erreur de la partie métier(validation)")));
            }
        }
}
