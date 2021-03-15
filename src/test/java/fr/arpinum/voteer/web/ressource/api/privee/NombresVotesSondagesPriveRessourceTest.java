package fr.arpinum.voteer.web.ressource.api.privee;

import fr.arpinum.voteer.recherche.sondage.DetailNombreVotes;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NombresVotesSondagesPriveRessourceTest {

    @Test
    public void peutDemanderLeNombreDeVotes() {
        RechercheSondage recherche = mock(RechercheSondage.class);
        NombresVotesSondagesPriveRessource ressource = new NombresVotesSondagesPriveRessource(recherche);

        List<DetailNombreVotes> réponse = ressource.représente();

        assertThat(réponse).isNotNull();
        verify(recherche).nombreDeVotes();
    }
}
