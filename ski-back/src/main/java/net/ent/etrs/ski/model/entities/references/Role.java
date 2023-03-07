package net.ent.etrs.ski.model.entities.references;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum Role {
    USER("Utilisateur"),
    ADMIN("Administrateur", USER),
    GESTIONNAIRE("Gestionnaire", USER),
    SUPERADMIN("SuperAdmin", ADMIN, GESTIONNAIRE),
    ;

    @Getter
    private final String libelle;

    private final List<Role> roles;

    Role(String libelle, Role... roles) {
        this.libelle = libelle;
        this.roles = Arrays.asList(roles);
    }

    private static Set<Role> getAllRoles(Role role) {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        for (Role r : role.roles) {
            roles.addAll(getAllRoles(r));
        }
        return roles;
    }

    public boolean hasRole(Role role) {
        return this.getAllRoles().contains(role);
    }

    public Set<Role> getAllRoles() {
        return Role.getAllRoles(this);
    }
}
