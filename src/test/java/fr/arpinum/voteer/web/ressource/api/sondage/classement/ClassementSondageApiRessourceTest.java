package fr.arpinum.voteer.web.ressource.api.sondage.classement;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.voteer.commande.sondage.classement.CalculClassementCommande;
import fr.arpinum.voteer.web.ressource.InitialisateurRessource;
import fr.arpinum.voteer.web.ressource.api.sondage.proposition.VueProposition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ClassementSondageApiRessourceTest {

    @Before
    public void setUp() throws Exception {
        bus = mock(BusCommande.class);
        ressource = new ClassementSondageApiRessource(bus);
        List<VueResultat> donnée = Lists.newArrayList();
        when(bus.envoie(any(CalculClassementCommande.class))).thenReturn(Futures.immediateFuture(ResultatExecution.succes(donnée)));
    }

    @Test
    public void peutDemanderLeClassement() {
        InitialisateurRessource.pour(ressource).avecParamètre("id", "un id").initialise();

        ressource.représente();

        ArgumentCaptor<CalculClassementCommande> captor = ArgumentCaptor.forClass(CalculClassementCommande.class);
        verify(bus).envoie(captor.capture());
        assertThat(captor.getValue().idSondage).isEqualTo("un id");
    }

    @Test
    public void peutRetournerLeClassement() {
        List<VueResultat> classement = Lists.newArrayList();
        classement.add(new VueResultat(Lists.newArrayList(new VueProposition("test"))));
        classement.add(new VueResultat(Lists.newArrayList(new VueProposition("autre"))));
        when(bus.envoie(any(CalculClassementCommande.class))).thenReturn(Futures.immediateFuture(ResultatExecution.succes(classement)));
        InitialisateurRessource.pour(ressource).avecParamètre("id", "id");

        List<VueResultat> résultat = ressource.représente();

        assertThat(résultat).hasSize(2);
        assertThat(résultat.get(0).propositions.get(0).label).isEqualTo("test");
    }

    private BusCommande bus;
    private ClassementSondageApiRessource ressource;
}
