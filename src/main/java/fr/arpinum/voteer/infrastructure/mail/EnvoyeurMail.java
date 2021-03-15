package fr.arpinum.voteer.infrastructure.mail;

import com.google.inject.Inject;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;

public class EnvoyeurMail {

    @Inject
    public EnvoyeurMail(ConfigurationMail configuration) {
        this.configuration = configuration;
    }

    public void envoie(String a, String sujet, String contenuHtml) {
        Reference reference = new Reference("https://sendgrid.com/api/mail.send.json");
        reference.addQueryParameter("api_user", configuration.utilisateur);
        reference.addQueryParameter("api_key", configuration.motDePasse);
        reference.addQueryParameter("to", a);
        reference.addQueryParameter("subject", sujet);
        reference.addQueryParameter("html", contenuHtml);
        reference.addQueryParameter("from", configuration.from);
        new Client(Protocol.HTTPS).handle(new Request(Method.GET, reference));
    }

    private final ConfigurationMail configuration;
}
