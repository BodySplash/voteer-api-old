package fr.arpinum.voteer.commande.sondage.vote;

import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.sondage.MauvaiseClefException;
import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class SuppressionVoteCapteurCommandeTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
    }

    @Test
    public void peutSupprimerVote() {
        Sondage sondage = unSondageAvecUnVote();

        SuppressionVoteCommande message = new SuppressionVoteCommande(sondage.getId(), 0, sondage.getAdminKey());
        new SuppressionVoteCapteurCommande().execute(message);


        assertThat(sondage.getVotes()).isEmpty();
    }

    @Test
    public void vérifieBienLaClef() {
        Sondage sondage = unSondageAvecUnVote();

        SuppressionVoteCommande message = new SuppressionVoteCommande(sondage.getId(), 0, "naitesrati");

        exception.expect(MauvaiseClefException.class);
        new SuppressionVoteCapteurCommande().execute(message);
    }

    private Sondage unSondageAvecUnVote() {
        Sondage sondage = FabriquePourTests.obtenirSondageAvec("un");
        Entrepots.sondages().ajoute(sondage);
        Participant participant = new Participant("jb");
        participant.voteLors(sondage).pour("un");
        return sondage;
    }
}
