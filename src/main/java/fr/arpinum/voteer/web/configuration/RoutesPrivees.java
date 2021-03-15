package fr.arpinum.voteer.web.configuration;

import com.google.inject.Injector;
import fr.arpinum.voteer.web.ressource.api.privee.NombresVotesSondagesPriveRessource;
import fr.arpinum.voteer.web.ressource.api.privee.SondagesPriveRessource;
import fr.arpinum.voteer.web.restlet.GuiceRouter;
import org.restlet.Context;

public class RoutesPrivees extends GuiceRouter {

    public RoutesPrivees(Context context, Injector injector) {
        super(context, injector);
    }

    @Override
    protected void route() {
        attach("/polls", SondagesPriveRessource.class);
        attach("/polls/votes-count", NombresVotesSondagesPriveRessource.class);
    }
}
