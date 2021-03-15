package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.voteer.infrastructure.AvecEntrepots;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.sondage.MauvaiseClefException;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.modele.sondage.Status;
import fr.arpinum.voteer.modele.sondage.Visibilite;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ModificationSondageCapteurCommandeTest {

    @Rule
    public AvecEntrepots entrepots = new AvecEntrepots();

    @Before
    public void setUp() throws Exception {
        handler = new ModificationSondageCapteurCommande();
        sondage = FabriquePourTests.obtenirSondage();
        Entrepots.sondages().ajoute(sondage);
    }

    @Test
    public void peutModifierLaVisibilité() {
        ModificationSondageCommande message = messageAvecStatus("Ouvert");

        handler.execute(message);

        assertThat(sondage.getVisibilité()).isEqualTo(Visibilite.Public);
    }

    @Test
    public void peutModifierLesCommentaires() {
        ModificationSondageCommande message = messageAvecStatus("Ouvert");

        handler.execute(message);

        assertThat(sondage.isAvecCommentaires()).isTrue();
    }

    @Test
    public void peutModifierLeStatus() {
        ModificationSondageCommande message = messageAvecStatus("Ferme");

        handler.execute(message);

        assertThat(sondage.getStatus()).isEqualTo(Status.Ferme);
    }

    @Test(expected = MauvaiseClefException.class)
    public void nePeutPasModifierSansLaBonneClef() {
        ModificationSondageCommande message = messageAvecStatus("Ouvert");
        message.adminKey = "une fausse clef";

        handler.execute(message);
    }

    private ModificationSondageCommande messageAvecStatus(String status) {
        ModificationSondageCommande message = new ModificationSondageCommande();
        message.id = sondage.getId();
        message.adminKey = sondage.getAdminKey();
        message.avecCommentaires = true;
        message.visibilite = "Public";
        message.status = status;
        return message;
    }

    private ModificationSondageCapteurCommande handler;
    private Sondage sondage;
}
