package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.voteer.infrastructure.AvecEntrepots;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SuppressionSondageCapteurCommandeTest {

    @Rule
    public AvecEntrepots entrepots = new AvecEntrepots();

    @Test
    public void peutSupprimer() {
        Sondage sondage = new Sondage("test");
        Entrepots.sondages().ajoute(sondage);

        new SuppressionSondageCapteurCommande().execute(new SuppressionSondageCommande(sondage.getId(), sondage.getAdminKey()));

        assertThat(Entrepots.sondages().getTous()).hasSize(0);
    }
}
