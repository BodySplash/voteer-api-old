package fr.arpinum.voteer.modele;

import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.modele.sondage.Vote;

public class FabriquePourTests {

    public static Sondage obtenirSondageAvec(final String... propositions) {
        final Sondage sondage = new Sondage("un nom");
        for (final String proposition : propositions) {
            sondage.ajouteProposition(proposition);
        }
        return sondage;
    }

    public static Sondage obtenirSondage() {
        return obtenirSondageAvec();
    }

    public static Vote votePour(final String... tri) {
        final Vote vote = new Vote(obtenirParticipant());
        vote.pour(tri);
        return vote;
    }

    public static Participant obtenirParticipant() {
        return new Participant("puett");
    }

}
