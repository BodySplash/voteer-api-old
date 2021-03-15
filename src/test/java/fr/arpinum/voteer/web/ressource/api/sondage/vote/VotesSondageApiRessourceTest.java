package fr.arpinum.voteer.web.ressource.api.sondage.vote;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.vote.AjoutVoteCommande;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.proposition.VueProposition;
import org.json.JSONException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VotesSondageApiRessourceTest {

    @Test
    public void peutRetournerLesVotesAvecLaClefDeSondage() {
        RechercheSondage recherche = mock(RechercheSondage.class);
        VotesSondageApiRessource ressource = new VotesSondageApiRessource(recherche, mock(BusCommande.class));
        InitialisateurRessource.pour(ressource).avecParamètre("id", "un id").avecQuery("key", "key").initialise();

        ressource.récupère();

        verify(recherche).votesDe("un id", "key");
    }

    @Test
    public void peutRécupérerLesVotesSIlsSontPublics() {
        RechercheSondage recherche = mock(RechercheSondage.class);
        VotesSondageApiRessource ressource = new VotesSondageApiRessource(recherche, mock(BusCommande.class));
        InitialisateurRessource.pour(ressource).avecParamètre("id", "un id").initialise();

        ressource.récupère();

        verify(recherche).votesPublicsDe("un id");
    }

    @Test
    public void peutVoter() throws JSONException {
        BusCommande bus = mock(BusCommande.class);
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(ResultatExecution.succes(null)));
        VotesSondageApiRessource ressource = new VotesSondageApiRessource(mock(RechercheSondage.class), bus);
        InitialisateurRessource.pour(ressource).avecParamètre("id", "un id").initialise();
        ParticipationSondage participationSondage = new ParticipationSondage();
        participationSondage.name = "Jb";
        participationSondage.proposals = Lists.newArrayList(new VueProposition("Un"), new VueProposition("Deux"));

        ressource.crée(participationSondage);

        ArgumentCaptor<AjoutVoteCommande> captor = ArgumentCaptor.forClass(AjoutVoteCommande.class);
        verify(bus).envoie(captor.capture());
        AjoutVoteCommande message = captor.getValue();
        assertThat(message.idSondage).isEqualTo("un id");
        assertThat(message.participant).isEqualTo("Jb");
        assertThat(message.propositions).hasSize(2).contains("Un", "Deux");
        assertThat(ressource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }
}
