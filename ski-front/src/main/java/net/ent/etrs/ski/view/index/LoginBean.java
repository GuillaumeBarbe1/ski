package net.ent.etrs.ski.view.index;


import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.exceptions.BusinessException;
import net.ent.etrs.ski.model.entities.User;
import net.ent.etrs.ski.model.entities.references.Role;
import net.ent.etrs.ski.model.facades.FacadeUser;
import net.ent.etrs.ski.utils.JsfUtils;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.spi.CDI;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;

@Named
@SessionScoped
public class LoginBean implements Serializable
{

    @Inject
    private FacadeUser facadeUser;

    @Getter
    private Role role;

    @Inject
    @Getter
    @Setter
    private User u;



    public void seConnecter()
    {
        try
        {
            User user = facadeUser.findByLoginPassword(u.getLogin(),u.getPassword());
            if(Objects.nonNull(user))
            {
                if(user.getRole().equals(Role.ADMIN))
                {
                    role=user.getRole();
                }
                u=user;
                JsfUtils.sendMessage("Bienvenue");
            }
        } catch (Exception |BusinessException e) {
            this.reset();
            JsfUtils.sendMessage(FacesMessage.SEVERITY_ERROR,"Connexion impossible");

        }
    }
    public void seDeconnecter()
    {
        this.reset();
        JsfUtils.sendMessage("A bientôt !");
    }

    private void reset()
    {
        u= CDI.current().select(User.class).get();
        role=null;
    }

}
