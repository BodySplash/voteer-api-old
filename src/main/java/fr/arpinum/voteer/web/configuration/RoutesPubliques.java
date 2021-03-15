package fr.arpinum.voteer.web.configuration;

import com.google.inject.Injector;
import fr.arpinum.voteer.web.ressource.api.AccueilRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.MailsSondageApiRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.SondageApiRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.SondagesApiRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.classement.ClassementSondageApiRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.proposition.PropositionSondageApiRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.proposition.PropositionsSondageApiRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.vote.VoteSondageApiRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.vote.VotesSondageApiRessource;
import fr.arpinum.voteer.web.restlet.GuiceRouter;
import org.restlet.Context;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;

public class RoutesPubliques extends GuiceRouter {

    public RoutesPubliques(Context context, Injector injector) {
        super(context, injector);
        setRoutingMode(Router.MODE_BEST_MATCH);
        route();
    }

    @Override
    protected void route() {
        attach("/", AccueilRessource.class);
        attach("/polls/{id}/proposals/{index}", PropositionSondageApiRessource.class);
        attach("/polls/{id}/proposals", PropositionsSondageApiRessource.class);
        attach("/polls/{id}/ranking", ClassementSondageApiRessource.class);
        attach("/polls/{id}/votes", VotesSondageApiRessource.class);
        attach("/polls/{id}/votes/{index}", VoteSondageApiRessource.class);
        attach("/polls/{id}/mails", MailsSondageApiRessource.class);
        attach("/polls/{id}", SondageApiRessource.class);
        attach("/polls", SondagesApiRessource.class);
        attach("/private", attacheSécurité(new RoutesPrivees(getContext(), injector)));
    }

    private Filter attacheSécurité(Router router) {
        Filter filtreSécurité = new FiltreRessourcesPrivees(getContext(), "thisismadness");
        filtreSécurité.setNext(router);
        return filtreSécurité;
    }


}
