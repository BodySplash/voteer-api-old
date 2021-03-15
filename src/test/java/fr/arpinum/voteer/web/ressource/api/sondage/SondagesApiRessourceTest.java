package fr.arpinum.voteer.web.ressource.api.sondage;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.ValidationException;
import fr.arpinum.voteer.commande.sondage.CreationSondageCommande;
import fr.arpinum.voteer.commande.sondage.IdentifiantsSondage;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.restlet.data.Status;

import javax.validation.ConstraintViolation;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class SondagesApiRessourceTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        bus = mock(BusCommande.class);
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(
                ResultatExecution.succes(new IdentifiantsSondage("", ""))));
        ressource = new SondagesApiRessource(bus);
        InitialisateurRessource.pour(ressource).initialise();
    }

    @Test
    public void peutCréerUnSondage() throws JSONException {
        CreationSondageCommande creationSondage = new CreationSondageCommande();
        creationSondage.nom = "Un sondage";

        ressource.crée(creationSondage);

        ArgumentCaptor<CreationSondageCommande> captor = ArgumentCaptor.forClass(CreationSondageCommande.class);
        verify(bus).envoie(captor.capture());
        CreationSondageCommande message = captor.getValue();
        assertThat(message.nom).isEqualTo("Un sondage");
    }

    @Test
    public void peutDonnerLAdresseDuSondage() throws JSONException {
        IdentifiantsSondage identifiants = new IdentifiantsSondage("id", "adminKey");
        when(bus.envoie(any())).thenReturn(Futures.immediateFuture(ResultatExecution.succes(identifiants)));

        IdentifiantsSondage résultat = ressource.crée(donnéesValides());

        assertThat(ressource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
        assertThat(résultat).isEqualTo(identifiants);
    }

    private CreationSondageCommande donnéesValides() {
        CreationSondageCommande creationSondage = new CreationSondageCommande();
        creationSondage.nom = "test";
        return creationSondage;
    }

    @Test
    public void peutValider() throws JSONException {
        ValidationException erreur = new ValidationException(Sets.<ConstraintViolation<?>>newHashSet());
        ResultatExecution<Object> résultat = ResultatExecution.erreur(erreur);
        ListenableFuture<ResultatExecution<Object>> future = Futures.immediateFuture(
                résultat);
        when(bus.envoie(any())).thenReturn(future);

        exception.expect(ValidationException.class);
        ressource.crée(new CreationSondageCommande());
    }

    private BusCommande bus;
    private SondagesApiRessource ressource;
}
