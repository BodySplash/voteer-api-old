package fr.arpinum.voteer.web.ressource.api.sondage;

import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.voteer.commande.sondage.ModificationSondageCommande;
import fr.arpinum.voteer.commande.sondage.SuppressionSondageCommande;
import fr.arpinum.voteer.recherche.sondage.DetailsAdminSondage;
import fr.arpinum.voteer.recherche.sondage.DetailsSondage;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.json.JSONException;
import org.restlet.data.Status;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;

import javax.inject.Inject;
import java.util.UUID;

public class SondageApiRessource extends RessourceVoteer {

    @Inject
    public SondageApiRessource(RechercheSondage recherche, BusCommande bus) {
        this.recherche = recherche;
        this.bus = bus;
    }

    @Override
    protected void doInit() throws ResourceException {
        id = UUID.fromString(getRequestAttributes().get("id").toString());
    }

    @Get
    public DetailsSondage récupère() {
        if (estAdmin()) {
            return rechercheDetailsAdmin();
        }
        return recherche.detailsDe(id).get();
    }

    private boolean estAdmin() {
        return getQuery().getNames().contains("key");
    }

    private DetailsAdminSondage rechercheDetailsAdmin() {
        return recherche.adminDe(id.toString(), getQueryValue("key")).get();
    }

    @Put("application/json")
    public void modifie(ModificationSondageCommande message) throws JSONException {
        message.id = id.toString();
        message.adminKey = getQueryValue("key");
        check(bus.envoie(message), Status.SUCCESS_NO_CONTENT);
    }

    @Delete
    public void supprime() {
        bus.envoie(new SuppressionSondageCommande(id.toString(), getQueryValue("key")));
    }

    private final RechercheSondage recherche;
    private final BusCommande bus;
    private UUID id;
}
