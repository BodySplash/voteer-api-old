package fr.arpinum.voteer.commande.sondage.classement;

import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CalculClassementEnGrapheCapteurCommandeTest {
    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
    }

    @After
    public void tearDown() throws Exception {
        Entrepots.initialise(null);
    }

    @Test
    @Ignore("à reprendre quand le domaine sera au point")
    public void peutDemanderLeClassement() {
        Sondage sondage = unSongadeAvecDeuxVotes();
        CalculClassementEnGrapheCommande message = new CalculClassementEnGrapheCommande(sondage.getId());

        final VueGraphe résultat = new CalculClassementEnGrapheCapteurCommande().execute(message);

        assertThat(résultat).isNotNull();
        assertThat(résultat.propositions).contains("test", "patate");
    }

    private Sondage unSongadeAvecDeuxVotes() {
        Sondage sondage = FabriquePourTests.obtenirSondageAvec("test", "patate");
        new Participant("jb").voteLors(sondage).pour("test", "patate");
        new Participant("chales").voteLors(sondage).pour("test", "patate");
        Entrepots.sondages().ajoute(sondage);
        return sondage;
    }
}