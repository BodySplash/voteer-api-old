package fr.arpinum.voteer.commande.sondage.proposition;

import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.MauvaiseClefException;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.modele.sondage.SondageNonModifiableException;
import fr.arpinum.voteer.modele.sondage.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

public class SuppressionPropositionCapteurCommandeTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
        handler = new SuppressionPropositionCapteurCommande();
    }

    @After
    public void tearDown() throws Exception {
        Entrepots.initialise(null);
    }

    @Test
    public void peutSupprimmerUneProposition() {
        Sondage sondage = unSondageAvecUneProposition();

        handler.execute(new SuppressionPropositionCommande(sondage.getId(), sondage.getAdminKey(), 0));

        assertThat(sondage.getPropositions()).isEmpty();
    }

    @Test
    public void nePeutSupprimerAvecLaMauvaiseClef() {
        Sondage sondage = unSondageAvecUneProposition();

        exception.expect(MauvaiseClefException.class);
        handler.execute(new SuppressionPropositionCommande(sondage.getId(), "rien à voir", 0));
    }

    @Test
    public void nePeutSupprimerSiLeSondageEstFermé() {
        Sondage sondage = unSondageAvecUneProposition();
        sondage.setStatus(Status.Ferme);

        exception.expect(SondageNonModifiableException.class);
        handler.execute(new SuppressionPropositionCommande(sondage.getId(), sondage.getAdminKey(), 0));
    }

    private Sondage unSondageAvecUneProposition() {
        Sondage sondage = new Sondage("test");
        sondage.ajouteProposition("proposition");
        Entrepots.sondages().ajoute(sondage);
        return sondage;
    }

    private SuppressionPropositionCapteurCommande handler;
}
