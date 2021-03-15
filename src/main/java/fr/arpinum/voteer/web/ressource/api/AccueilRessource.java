package fr.arpinum.voteer.web.ressource.api;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;

public class AccueilRessource extends RessourceVoteer {

    @Get
    public JsonRepresentation représente() throws JSONException {
        return new JsonRepresentation(new JSONObject("{ \"error\" : \"not for human\"}"));
    }
}
