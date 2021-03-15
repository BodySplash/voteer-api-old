package fr.arpinum.voteer.web.ressource.api.sondage.classement;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.classement.CalculClassementCommande;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import java.util.List;

public class ClassementSondageApiRessource extends RessourceVoteer {

    @Inject
    public ClassementSondageApiRessource(BusCommande bus) {
        this.bus = bus;
    }

    @Override
    protected void doInit() throws ResourceException {
        this.id = getRequestAttributes().get("id").toString();
    }

    @Get
    public List<VueResultat> représente() {
        ListenableFuture<ResultatExecution<List<VueResultat>>> résultat = bus.envoie(new CalculClassementCommande(id));
        ResultatExecution<List<VueResultat>> résultatCommande = Futures.getUnchecked(résultat);
        return résultatCommande.donnees();
    }

    private final BusCommande bus;
    private String id;
}
