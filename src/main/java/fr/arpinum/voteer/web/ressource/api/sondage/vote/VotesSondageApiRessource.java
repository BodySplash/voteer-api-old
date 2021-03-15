package fr.arpinum.voteer.web.ressource.api.sondage.vote;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.voteer.commande.sondage.vote.AjoutVoteCommande;
import fr.arpinum.voteer.recherche.sondage.DetailsVote;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import fr.arpinum.voteer.web.ressource.api.sondage.proposition.VueProposition;
import org.json.JSONException;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

import java.util.List;

public class VotesSondageApiRessource extends RessourceVoteer {

    @Inject
    public VotesSondageApiRessource(RechercheSondage sondage, BusCommande bus) {
        this.sondage = sondage;
        this.bus = bus;
    }

    @Override
    protected void doInit() throws ResourceException {
        id = getRequestAttributes().get("id").toString();
    }

    @Get
    public List<DetailsVote> récupère() {
        String key = getQueryValue("key");
        if (Strings.isNullOrEmpty(key)) {
            return sondage.votesPublicsDe(id);
        }
        return sondage.votesDe(id, key);
    }

    @Post
    public void crée(ParticipationSondage participationSondage) throws JSONException {
        AjoutVoteCommande message = new AjoutVoteCommande(id, participationSondage.name, enStrings(participationSondage.proposals));
        check(bus.envoie(message), Status.SUCCESS_CREATED);
    }

    private List<String> enStrings(List<VueProposition> proposals) {
        return Lists.transform(proposals, new Function<VueProposition, String>() {
            @Override
            public String apply(VueProposition vueProposition) {
                return vueProposition.label;
            }
        });
    }

    private final RechercheSondage sondage;
    private final BusCommande bus;
    private String id;
}
