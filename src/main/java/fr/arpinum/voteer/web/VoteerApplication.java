package fr.arpinum.voteer.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.arpinum.graine.web.restlet.BaseApplication;
import fr.arpinum.voteer.web.configuration.RoutesPubliques;
import fr.arpinum.voteer.web.configuration.VoteerModule;
import org.restlet.Context;
import org.restlet.routing.Router;

public class VoteerApplication extends BaseApplication {


    public VoteerApplication(final Context context) {
        super(context);
    }

    @Override
    protected Router routes() {
        return new RoutesPubliques(getContext(), injector());
    }

    protected Injector injector() {
        return Guice.createInjector(new VoteerModule());
    }


}
