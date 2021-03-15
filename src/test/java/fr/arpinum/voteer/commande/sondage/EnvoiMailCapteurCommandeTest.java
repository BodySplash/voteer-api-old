package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.voteer.infrastructure.mail.EnvoyeurMail;
import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EnvoiMailCapteurCommandeTest {

    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
    }

    @Test
    public void peutEnvoyerMail() {
        Sondage sondage = new Sondage("test");
        Entrepots.sondages().ajoute(sondage);
        EnvoyeurMail envoyeurMail = mock(EnvoyeurMail.class);
        EnvoiMailCapteurCommande handler = new EnvoiMailCapteurCommande(envoyeurMail);

        handler.execute(new EnvoiMailCommande(sondage.getId(), sondage.getAdminKey(), "contact@arpinum.fr"));

        String contenuAttendu =
                "Poll name : " + sondage.getNom() + "<br /><a href='http://itg.voteer.com/polls/" + sondage.getId() + "/admin?key=" + sondage.getAdminKey()
                        + "'>Admin link</a><br /><a href='http://itg.voteer.com/polls/" + sondage.getId() + "'>Vote link</a>";
        verify(envoyeurMail).envoie("contact@arpinum.fr", "Your poll on itg.voteer.com", contenuAttendu);
    }
}
