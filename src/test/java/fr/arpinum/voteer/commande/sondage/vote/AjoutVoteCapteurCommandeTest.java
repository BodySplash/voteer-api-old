package fr.arpinum.voteer.commande.sondage.vote;

import com.google.common.collect.Lists;
import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AjoutVoteCapteurCommandeTest {

    @Before
    public void setUp() throws Exception {
        Entrepots.initialise(new EntrepotsMemoire());
    }

    @Test
    public void peutAjouterLeVote() {
        Sondage sondage = new Sondage("test");
        sondage.ajouteProposition("un");
        sondage.ajouteProposition("deux");
        Entrepots.sondages().ajoute(sondage);
        AjoutVoteCommande commande = new AjoutVoteCommande(sondage.getId(), "jb", Lists.newArrayList("un"));

        new AjoutVoteCapteurCommande().execute(commande);

        assertThat(sondage.getVotes()).hasSize(1);
        assertThat(sondage.getVoteDe(new Participant("jb")).getChoix()).contains("un").hasSize(1);
    }
}
