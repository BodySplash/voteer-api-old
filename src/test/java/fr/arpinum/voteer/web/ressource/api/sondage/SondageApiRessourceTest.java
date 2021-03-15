package fr.arpinum.voteer.web.ressource.api.sondage;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.ModificationSondageCommande;
import fr.arpinum.voteer.commande.sondage.SuppressionSondageCommande;
import fr.arpinum.voteer.recherche.sondage.DetailsAdminSondage;
import fr.arpinum.voteer.recherche.sondage.DetailsSondage;
import fr.arpinum.voteer.recherche.sondage.RechercheSondage;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.data.Status;

import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SondageApiRessourceTest {

    @Before
    public void setUp() throws Exception {
        bus = mock(BusCommande.class);
        recherche = mock(RechercheSondage.class);
        ressource = new SondageApiRessource(recherche, bus);
    }

    @Test
    public void peutRécupérerLesInformationsDeBase() {
        UUID id = UUID.randomUUID();
        DetailsSondage detailsSondage = new DetailsSondage();
        laRechercheRetourne(id, detailsSondage);
        InitialisateurRessource.pour(ressource).avecParamètre("id", id).initialise();

        DetailsSondage détails = ressource.récupère();

        assertThat(détails).isNotNull().isEqualTo(detailsSondage);
    }

    private void laRechercheRetourne(UUID id, DetailsSondage detailsSondage) {
        when(recherche.detailsDe(id)).thenReturn(Optional.of(detailsSondage));
    }

    @Test
    public void peutRécupérerLesInformationsDAdministrationAvecLaClef() {
        when(recherche.adminDe(anyString(), anyString())).thenReturn(Optional.of(new DetailsAdminSondage()));
        UUID id = UUID.randomUUID();
        InitialisateurRessource.pour(ressource).avecParamètre("id", id).avecQuery("key", "une clef").initialise();

        ressource.récupère();

        verify(recherche).adminDe(id.toString(), "une clef");
    }

    @Test
    public void peutModifier() throws JSONException {
        UUID id = UUID.randomUUID();
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(ResultatExecution.succes(null)));
        InitialisateurRessource.pour(ressource).avecParamètre("id", id).avecQuery("key", "une clef").initialise();
        ModificationSondageCommande modification = new ModificationSondageCommande();
        modification.visibilite = "Public";
        modification.avecCommentaires = true;
        modification.status = "Ferme";

        ressource.modifie(modification);

        ArgumentCaptor<ModificationSondageCommande> captor = ArgumentCaptor.forClass(ModificationSondageCommande.class);
        verify(bus).envoie(captor.capture());
        ModificationSondageCommande message = captor.getValue();
        assertThat(message.id).isEqualTo(id.toString());
        assertThat(message.adminKey).isEqualTo("une clef");
        assertThat(message.avecCommentaires).isTrue();
        assertThat(message.visibilite).isEqualTo("Public");
        assertThat(message.status).isEqualTo("Ferme");
        assertThat(ressource.getStatus()).isEqualTo(Status.SUCCESS_NO_CONTENT);
    }

    @Test
    public void peutDemanderASupprimer() {
        String id = UUID.randomUUID().toString();
        InitialisateurRessource.pour(ressource).avecParamètre("id", id).avecQuery("key", "une clef").initialise();

        ressource.supprime();

        ArgumentCaptor<SuppressionSondageCommande> captor = ArgumentCaptor.forClass(SuppressionSondageCommande.class);
        verify(bus).envoie(captor.capture());
        assertThat(captor.getValue().id).isEqualTo(id);
        assertThat(captor.getValue().adminKey).isEqualTo("une clef");
    }

    private BusCommande bus;
    private RechercheSondage recherche;
    private SondageApiRessource ressource;
}
