package net.ent.etrs.ski.model.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
public abstract class AbstractEntity implements Serializable {
    
    @Getter @Setter
    private Long id;
    
}
