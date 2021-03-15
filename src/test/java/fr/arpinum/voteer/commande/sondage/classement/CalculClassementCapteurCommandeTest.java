package fr.arpinum.voteer.commande.sondage.classement;

import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.web.ressource.api.sondage.classement.VueResultat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CalculClassementCapteurCommandeTest {

    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
    }

    @After
    public void tearDown() throws Exception {
        Entrepots.initialise(null);
    }

    @Test
    public void peutDemanderLeClassement() {
        Sondage sondage = unSondageAvecUnVote();
        CalculClassementCommande message = new CalculClassementCommande(sondage.getId());

        List<VueResultat> résultat = new CalculClassementCapteurCommande().execute(message);

        assertThat(résultat).isNotNull();
        assertThat(résultat).hasSize(1);
        assertThat(résultat.get(0).propositions).hasSize(1);
        assertThat(résultat.get(0).propositions.get(0).label).isEqualTo("test");
    }

    private Sondage unSondageAvecUnVote() {
        Sondage sondage = FabriquePourTests.obtenirSondageAvec("test");
        new Participant("jb").voteLors(sondage).pour("test");
        Entrepots.sondages().ajoute(sondage);
        return sondage;
    }
}
