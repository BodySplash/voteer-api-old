package fr.arpinum.voteer.web.ressource.api.sondage.proposition;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.proposition.SuppressionPropositionCommande;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.json.JSONException;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.ResourceException;

public class PropositionSondageApiRessource extends RessourceVoteer {

    @Inject
    public PropositionSondageApiRessource(BusCommande bus) {
        this.bus = bus;
    }

    @Override
    protected void doInit() throws ResourceException {
        id = getRequestAttributes().get("id").toString();
        index = Integer.parseInt(getRequestAttributes().get("index").toString());
    }

    @Delete
    public void supprime() throws JSONException {
        String key = getQueryValue("key");
        ListenableFuture<ResultatExecution<Void>> résultat = bus.envoie(new SuppressionPropositionCommande(id, key, index));
        check(résultat, Status.SUCCESS_NO_CONTENT);
    }

    private final BusCommande bus;
    private String id;
    private int index;
}

