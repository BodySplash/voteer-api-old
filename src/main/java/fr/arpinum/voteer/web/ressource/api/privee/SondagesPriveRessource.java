package fr.arpinum.voteer.web.ressource.api.privee;

import com.google.inject.Inject;
import fr.arpinum.voteer.recherche.sondage.DetailsAdminSondage;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.restlet.resource.Get;

import java.util.List;

public class SondagesPriveRessource extends RessourceVoteer {

    @Inject
    public SondagesPriveRessource(RechercheSondage rechercheSondage) {
        this.rechercheSondage = rechercheSondage;
    }

    @Get
    public List<DetailsAdminSondage> représente() {
        return rechercheSondage.tous();
    }

    private final RechercheSondage rechercheSondage;
}
