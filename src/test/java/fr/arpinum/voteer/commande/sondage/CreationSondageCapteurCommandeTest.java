package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CreationSondageCapteurCommandeTest {

    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
        creationSondageCommande = new CreationSondageCapteurCommande();
    }

    @Test
    public void peutCréerSondageSimple() {
        CreationSondageCommande message = new CreationSondageCommande();
        message.nom = "Nom du sondage";

        IdentifiantsSondage resultat = creationSondageCommande.execute(message);

        Sondage sondage = sondageCréé(resultat);
        assertThat(sondage).isNotNull();
        assertThat(sondage.getAdminKey()).isEqualTo(resultat.adminKey);
        assertThat(sondage.getNom()).isEqualTo("Nom du sondage");
    }

    @Test
    public void peutPrendreEnCompteLaVisibilitéDuSondage() {
        CreationSondageCommande message = new CreationSondageCommande();
        message.nom = "test";
        message.estPublic = true;

        IdentifiantsSondage identifiants = creationSondageCommande.execute(message);

        Sondage sondage = sondageCréé(identifiants);
        assertThat(sondage.avecVotesPublics()).isTrue();
    }

    private Sondage sondageCréé(IdentifiantsSondage identifiants) {
        return Entrepots.sondages().get(identifiants.id);
    }

    @Test
    public void peutAjouterLesPropositions() {
        CreationSondageCommande message = new CreationSondageCommande();
        message.propositions.add(new CreationSondageCommande.Proposition());
        message.propositions.get(0).label = "label";

        IdentifiantsSondage identifiants = creationSondageCommande.execute(message);

        Sondage sondage = sondageCréé(identifiants);
        assertThat(sondage.getPropositions()).hasSize(1);
    }

    private CreationSondageCapteurCommande creationSondageCommande;
}
