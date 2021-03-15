package fr.arpinum.voteer.web.ressource.api.sondage.vote;

import com.google.common.util.concurrent.Futures;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.vote.SuppressionVoteCommande;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VoteSondageApiRessourceTest {

    @Test
    public void peutDemanderSuppressionVote() throws JSONException {
        BusCommande bus = mock(BusCommande.class);
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(ResultatExecution.succes(null)));
        VoteSondageApiRessource ressource = new VoteSondageApiRessource(bus);
        InitialisateurRessource.pour(ressource)
                .avecParamètre("id", "id")
                .avecParamètre("index", 1)
                .avecQuery("key", "key")
                .initialise();

        ressource.supprime();

        ArgumentCaptor<SuppressionVoteCommande> captor = ArgumentCaptor.forClass(SuppressionVoteCommande.class);
        verify(bus).envoie(captor.capture());
        SuppressionVoteCommande message = captor.getValue();
        assertThat(message.id).isEqualTo("id");
        assertThat(message.index).isEqualTo(1);
        assertThat(message.key).isEqualTo("key");
    }
}
