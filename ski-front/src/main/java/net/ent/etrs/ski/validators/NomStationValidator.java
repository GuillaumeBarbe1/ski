package net.ent.etrs.ski.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class NomStationValidator implements Serializable, Validator<String>
{
    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException
    {
        if(value.contains("Les gets"))
        {
            throw new ValidatorException(new FacesMessage("les gets c'est trop cher !"));
        }
    }
}
