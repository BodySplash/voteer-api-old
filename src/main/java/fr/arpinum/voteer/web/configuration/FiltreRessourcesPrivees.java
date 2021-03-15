package fr.arpinum.voteer.web.configuration;

import com.google.common.base.Optional;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

public class FiltreRessourcesPrivees extends Filter {
    public FiltreRessourcesPrivees(Context context, String adminToken) {
        super(context);
        this.adminToken = adminToken;
    }

    @Override
    protected int beforeHandle(Request request, Response response) {
        if (!valeurToken(request).or("rien").equals(adminToken)) {
            response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
            return SKIP;
        }
        return CONTINUE;
    }

    private Optional<String> valeurToken(Request request) {
        return Optional.fromNullable(request.getResourceRef().getQueryAsForm().getFirstValue(NOM_TOKEN));
    }

    private static final String NOM_TOKEN = "adminToken";
    private String adminToken;
}
