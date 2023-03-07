package net.ent.etrs.ski.view.security;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.ski.utils.JsfUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginBean implements Serializable {
    
    @Inject
    private SessionBean sessionBean;

    @Getter @Setter
    private String login;
    
    @Getter @Setter
    private String password;
    
    public void valider() throws IOException {
        if (sessionBean.login(this.login, this.password)) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        } else {
            JsfUtils.sendMessage(null, FacesMessage.SEVERITY_ERROR, "Identifiants incorrects !!!");
        }
    }

}
