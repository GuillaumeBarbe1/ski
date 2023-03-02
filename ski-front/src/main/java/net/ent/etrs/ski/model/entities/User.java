package net.ent.etrs.ski.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etrs.ski.model.entities.references.Role;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false, of = {"login"})
@ToString(of = {"login"})
public class User extends AbstractEntity
{
    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;


    @Getter
    @Setter
    private Role role;

}
