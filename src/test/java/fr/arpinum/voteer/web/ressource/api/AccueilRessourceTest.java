package fr.arpinum.voteer.web.ressource.api;

import org.json.JSONException;
import org.junit.Test;
import org.restlet.ext.json.JsonRepresentation;

import static org.fest.assertions.Assertions.assertThat;

public class AccueilRessourceTest {

    @Test
    public void peutRépondre() throws JSONException {
        JsonRepresentation représentation = new AccueilRessource().représente();

        assertThat(représentation.getJsonObject().toString()).isEqualTo("{\"error\":\"not for human\"}");
    }
}
