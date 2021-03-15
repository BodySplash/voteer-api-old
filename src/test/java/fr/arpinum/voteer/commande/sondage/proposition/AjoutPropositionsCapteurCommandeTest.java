package fr.arpinum.voteer.commande.sondage.proposition;

import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.sondage.MauvaiseClefException;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.modele.sondage.Status;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class AjoutPropositionsCapteurCommandeTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
        sondage = new Sondage("test");
        Entrepots.sondages().ajoute(sondage);
    }

    @Test
    public void peutAjouterPropositions() {
        AjoutPropositionsCommande message = message("une");

        new AjoutPropositionsCapteurCommande().execute(message);

        assertThat(sondage.getPropositions()).hasSize(1).contains("une");
    }

    @Test
    public void nePeutPasAjouterAvecLaMauvaiseClef() {
        AjoutPropositionsCommande message = message("une");
        message.key = "autre clef";

        exception.expect(MauvaiseClefException.class);
        new AjoutPropositionsCapteurCommande().execute(message);
    }

    @Test
    public void retourneUnÉchecSurSondageNonModifiable() {
        sondage.ajouteProposition("une valeur");
        FabriquePourTests.obtenirParticipant().voteLors(sondage).pour("une valeur");
        AjoutPropositionsCommande message = message("une");

        exception.expect(Exception.class);
        new AjoutPropositionsCapteurCommande().execute(message);
    }

    @Test
    public void nePeutPasAjouterSiLeSondageEstFermé() {
        sondage.setStatus(Status.Ferme);
        AjoutPropositionsCommande message = message("une");

        exception.expect(Exception.class);
        new AjoutPropositionsCapteurCommande().execute(message);
    }

    @Test
    public void retourneUnÉchecSiAjoutImpossible() {
        sondage.ajouteProposition("une valeur");

        new AjoutPropositionsCapteurCommande().execute(message("une valeur"));

    }

    private AjoutPropositionsCommande message(String proposition) {
        AjoutPropositionsCommande résultat = new AjoutPropositionsCommande();
        résultat.key = sondage.getAdminKey();
        résultat.id = sondage.getId();
        résultat.proposition = proposition;
        return résultat;
    }

    private Sondage sondage;
}
