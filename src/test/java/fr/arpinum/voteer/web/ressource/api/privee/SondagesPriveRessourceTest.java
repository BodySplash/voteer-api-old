package fr.arpinum.voteer.web.ressource.api.privee;

import fr.arpinum.voteer.recherche.sondage.DetailsAdminSondage;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SondagesPriveRessourceTest {

    @Test
    public void peutDemanderLeDétailPrivéDunSondage() {
        RechercheSondage recherche = mock(RechercheSondage.class);
        SondagesPriveRessource ressource = new SondagesPriveRessource(recherche);

        List<DetailsAdminSondage> réponse = ressource.représente();

        assertThat(réponse).isNotNull();
        verify(recherche).tous();
    }
}
