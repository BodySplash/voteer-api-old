package fr.arpinum.voteer.web.ressource.api.sondage.proposition;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.proposition.AjoutPropositionsCommande;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.data.Status;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PropositionsSondageApiRessourceTest {

    @Before
    public void setUp() throws Exception {
        recherche = mock(RechercheSondage.class);
        bus = mock(BusCommande.class);
        ressource = new PropositionsSondageApiRessource(recherche, bus);
        InitialisateurRessource.pour(ressource).avecParamètre("id", "id").avecQuery("key", "key").initialise();
    }

    @Test
    public void peutDonnerLaListeDesPropositions() {
        when(recherche.propositionsDe("id")).thenReturn(Lists.<String>newArrayList("test", "autre"));

        List<VueProposition> réponse = ressource.représente();

        assertThat(réponse).hasSize(2);
        assertThat(réponse.get(0).label).isEqualTo("test");
        assertThat(réponse.get(1).label).isEqualTo("autre");
    }

    @Test
    public void peutAjouterDesPropositions() throws JSONException {
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(ResultatExecution.succes(null)));

        ressource.ajoute(message());

        ArgumentCaptor<AjoutPropositionsCommande> captor = ArgumentCaptor.forClass(AjoutPropositionsCommande.class);
        verify(bus).envoie(captor.capture());
        AjoutPropositionsCommande messageCapturé = captor.getValue();
        assertThat(messageCapturé.proposition).isEqualTo("proposition");
        assertThat(messageCapturé.id).isEqualTo("id");
        assertThat(messageCapturé.key).isEqualTo("key");
    }

    @Test
    public void peutParserLeRésultat() throws JSONException {
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(ResultatExecution.succes(null)));

        ressource.ajoute(message());

        assertThat(ressource.getStatus()).isEqualTo(Status.SUCCESS_NO_CONTENT);
    }

    private AjoutPropositionsCommande message() {
        AjoutPropositionsCommande message = new AjoutPropositionsCommande();
        message.proposition = "proposition";
        return message;
    }

    private RechercheSondage recherche;
    private PropositionsSondageApiRessource ressource;
    private BusCommande bus;
}
