package fr.arpinum.voteer.web.ressource.api.sondage.proposition;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.proposition.AjoutPropositionsCommande;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.api.RessourceVoteer;
import org.json.JSONException;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

import java.util.List;
import java.util.stream.Collectors;

public class PropositionsSondageApiRessource extends RessourceVoteer {

    @Inject
    public PropositionsSondageApiRessource(RechercheSondage recherche, BusCommande bus) {
        this.recherche = recherche;
        this.bus = bus;
    }

    @Override
    protected void doInit() throws ResourceException {
        id = getRequestAttributes().get("id").toString();
    }

    @Get
    public List<VueProposition> représente() {
        List<VueProposition> résultat = Lists.newArrayList();
        résultat.addAll(recherche.propositionsDe(id).stream().map(VueProposition::new).collect(Collectors.toList()));
        return résultat;
    }

    @Post
    public void ajoute(AjoutPropositionsCommande message) throws JSONException {
        String key = getQueryValue("key");
        message.id = id;
        message.key = key;
        ListenableFuture<ResultatExecution<Boolean>> future = bus.envoie(message);
        check(future, Status.SUCCESS_NO_CONTENT);
    }

    private final RechercheSondage recherche;
    private final BusCommande bus;
    private String id;
}
