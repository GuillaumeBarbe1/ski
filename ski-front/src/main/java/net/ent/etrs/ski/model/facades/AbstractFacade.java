package net.ent.etrs.ski.model.facades;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.Serializable;

public abstract class AbstractFacade implements Serializable {

    protected static final String BACK_URL = "http://localhost:8080/ski-back-1.0-SNAPSHOT/api/";

    protected Client client = ClientBuilder.newClient();
}
