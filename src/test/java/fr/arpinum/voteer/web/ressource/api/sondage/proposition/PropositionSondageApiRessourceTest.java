package fr.arpinum.voteer.web.ressource.api.sondage.proposition;

import com.google.common.util.concurrent.Futures;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.proposition.SuppressionPropositionCommande;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PropositionSondageApiRessourceTest {

    @Test
    public void peutDemanderASupprimerProposition() throws JSONException {
        BusCommande bus = mock(BusCommande.class);
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(ResultatExecution.succes(null)));
        PropositionSondageApiRessource ressource = new PropositionSondageApiRessource(bus);
        InitialisateurRessource.pour(ressource)
                .avecParamètre("id", "id")
                .avecParamètre("index", 0)
                .avecQuery("key", "key")
                .initialise();

        ressource.supprime();

        ArgumentCaptor<SuppressionPropositionCommande> captor = ArgumentCaptor.forClass(SuppressionPropositionCommande.class);
        verify(bus).envoie(captor.capture());
        SuppressionPropositionCommande message = captor.getValue();
        assertThat(message.indexProposition).isEqualTo(0);
        assertThat(message.idSondage).isEqualTo("id");
        assertThat(message.adminKey).isEqualTo("key");
    }
}
