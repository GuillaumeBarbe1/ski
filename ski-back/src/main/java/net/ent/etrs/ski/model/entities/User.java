package net.ent.etrs.ski.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.ent.etrs.ski.model.entities.references.Role;

import javax.persistence.*;

@Entity
@Table(name = "USER", uniqueConstraints = @UniqueConstraint(name = "USER__LOGIN__UK", columnNames = {"LOGIN"}))
@EqualsAndHashCode(callSuper = false, of = {"login"})
@ToString(of = {"login"})
public class User extends AbstractEntity
{
    @Getter
    @Setter
    @Column(name = "LOGIN", nullable = false, length = 50)
    private String login;

    @Getter
    @Setter
    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password;


    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;

}
