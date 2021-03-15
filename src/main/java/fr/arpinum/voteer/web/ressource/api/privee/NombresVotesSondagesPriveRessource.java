package fr.arpinum.voteer.web.ressource.api.privee;

import com.google.inject.Inject;
import fr.arpinum.voteer.recherche.sondage.DetailNombreVotes;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.restlet.resource.Get;

import java.util.List;

public class NombresVotesSondagesPriveRessource extends RessourceVoteer {

    @Inject
    public NombresVotesSondagesPriveRessource(RechercheSondage rechercheSondage) {
        this.rechercheSondage = rechercheSondage;
    }

    @Get
    public List<DetailNombreVotes> représente() {
        return rechercheSondage.nombreDeVotes();
    }

    private final RechercheSondage rechercheSondage;
}
