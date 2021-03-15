package fr.arpinum.voteer.web.configuration;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import fr.arpinum.voteer.web.configuration.exception.ResolveurException;
import fr.arpinum.voteer.web.configuration.exception.ResolveurValidationException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.service.StatusService;

import java.util.List;

public class VoteerStatusService extends StatusService {
    @Override
    public Status getStatus(Throwable throwable, Request request, Response response) {
        Optional<ResolveurException> resolveur = resolveur(throwable);
        if(resolveur.isPresent()) {
            return resolveur.get().status();
        }
        return super.getStatus(throwable, request, response);
    }

    private Optional<ResolveurException> resolveur(Throwable throwable) {
        for (ResolveurException resolveur : resolveurs) {
            if(resolveur.peutRésourdre(throwable)) return Optional.of(resolveur);
        }
        return Optional.absent();
    }

    @Override
    public Representation getRepresentation(Status status, Request request, Response response) {
        Optional<ResolveurException> resolveur = resolveur(status.getThrowable());
        if(resolveur.isPresent()) {
            return resolveur.get().representation(status.getThrowable());
        }
        return super.getRepresentation(status, request, response);
    }

    private List<? extends ResolveurException> resolveurs = Lists.newArrayList(
            new ResolveurValidationException()
    );

}
