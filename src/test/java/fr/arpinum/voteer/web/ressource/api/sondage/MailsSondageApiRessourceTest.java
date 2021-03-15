package fr.arpinum.voteer.web.ressource.api.sondage;

import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.voteer.commande.sondage.EnvoiMailCommande;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.restlet.data.Form;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MailsSondageApiRessourceTest {

    @Test
    public void peutEnvoyerUnMailAvecLeLienDAdministration() {
        BusCommande bus = mock(BusCommande.class);
        MailsSondageApiRessource resource = new MailsSondageApiRessource(bus);
        InitialisateurRessource.pour(resource)
                .avecParamètre("id", "id")
                .avecQuery("key", "key")
                .initialise();
        Form formulaire = new Form();
        formulaire.add("mail", "contact@arpinum.fr");

        resource.crée(formulaire);

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
        ArgumentCaptor<EnvoiMailCommande> captor = ArgumentCaptor.forClass(EnvoiMailCommande.class);
        verify(bus).envoie(captor.capture());
        EnvoiMailCommande message = captor.getValue();
        assertThat(message.idSondage).isEqualTo("id");
        assertThat(message.adminKey).isEqualTo("key");
        assertThat(message.emailTo).isEqualTo("contact@arpinum.fr");

    }

}
