package fr.arpinum.voteer;

import com.google.common.base.Optional;
import fr.arpinum.graine.web.restlet.Serveur;
import fr.arpinum.voteer.web.VoteerApplication;
import org.restlet.Context;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) throws Exception {
        new Serveur(new VoteerApplication(new Context())).start(port());

    }

    private static Integer port() {
        Optional<String> heroku = Optional.fromNullable(System.getenv("PORT"));
        return Integer.valueOf(heroku.or("8182"));
    }


}
