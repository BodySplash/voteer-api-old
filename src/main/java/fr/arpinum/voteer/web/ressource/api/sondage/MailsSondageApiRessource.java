package fr.arpinum.voteer.web.ressource.api.sondage;

import com.google.inject.Inject;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.voteer.commande.sondage.EnvoiMailCommande;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.Post;

public class MailsSondageApiRessource extends RessourceVoteer {

    @Inject
    public MailsSondageApiRessource(BusCommande bus) {
        this.bus = bus;
    }

    @Override
    protected void doInit() {
        id = getAttribute("id");
        key = getQueryValue("key");
    }

    @Post
    public void crée(Form form) {
        EnvoiMailCommande message = new EnvoiMailCommande(id, key, form.getFirstValue("mail"));
        bus.envoie(message);
        setStatus(Status.SUCCESS_CREATED);
    }

    private final BusCommande bus;
    private String id;
    private String key;
}
