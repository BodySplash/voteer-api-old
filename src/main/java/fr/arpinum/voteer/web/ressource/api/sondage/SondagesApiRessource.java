package fr.arpinum.voteer.web.ressource.api.sondage;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.CreationSondageCommande;
import fr.arpinum.voteer.commande.sondage.IdentifiantsSondage;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.json.JSONException;
import org.restlet.data.Status;
import org.restlet.resource.Post;

public class SondagesApiRessource extends RessourceVoteer {

    @Inject
    public SondagesApiRessource(BusCommande bus) {
        this.bus = bus;

    }

    @Post("application/json")
    public IdentifiantsSondage crée(CreationSondageCommande message) throws JSONException {
        ListenableFuture<ResultatExecution<IdentifiantsSondage>> future = bus.envoie(message);
        ResultatExecution<IdentifiantsSondage> resultat = Futures.getUnchecked(future);
        if (resultat.isErreur()) {
            Throwables.propagate(resultat.erreur());
        }
        setStatus(Status.SUCCESS_CREATED);
        return resultat.donnees();
    }

    private BusCommande bus;

}

