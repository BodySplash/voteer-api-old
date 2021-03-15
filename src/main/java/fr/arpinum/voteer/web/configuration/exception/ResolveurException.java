package fr.arpinum.voteer.web.configuration.exception;


import org.restlet.data.Status;
import org.restlet.representation.Representation;

public interface ResolveurException {

    boolean peutRésourdre(Throwable throwable);

    Status status();

    Representation representation(Throwable throwable);
}
