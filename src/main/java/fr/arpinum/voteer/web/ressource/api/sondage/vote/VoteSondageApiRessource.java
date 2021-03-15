package fr.arpinum.voteer.web.ressource.api.sondage.vote;

import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.voteer.commande.sondage.vote.SuppressionVoteCommande;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.json.JSONException;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.ResourceException;

import javax.inject.Inject;

public class VoteSondageApiRessource extends RessourceVoteer {

    @Inject
    public VoteSondageApiRessource(BusCommande bus) {
        this.bus = bus;
    }

    @Override
    protected void doInit() throws ResourceException {
        this.idSondage = getRequestAttributes().get("id").toString();
        this.indexVote = Integer.parseInt(getRequestAttributes().get("index").toString());
    }

    @Delete
    public void supprime() throws JSONException {
        SuppressionVoteCommande message = new SuppressionVoteCommande(idSondage, indexVote, getQueryValue("key"));
        check(bus.envoie(message), Status.SUCCESS_NO_CONTENT);
    }

    private final BusCommande bus;
    private String idSondage;
    private int indexVote;
}
