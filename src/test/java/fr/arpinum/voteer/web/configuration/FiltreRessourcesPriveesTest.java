package fr.arpinum.voteer.web.configuration;

import org.junit.Test;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.routing.Filter;

import static org.fest.assertions.Assertions.assertThat;

public class FiltreRessourcesPriveesTest {

    @Test
    public void peutRefuserUneDemande() {
        FiltreRessourcesPrivees filtre = new FiltreRessourcesPrivees(new Context(), "test");
        Request request = new Request(Method.GET, "/private/polls");

        int réponse = filtre.beforeHandle(request, new Response(request));

        assertThat(réponse).isEqualTo(Filter.SKIP);
    }

    @Test
    public void peutAutoriserUneDemande() {
        FiltreRessourcesPrivees filtre = new FiltreRessourcesPrivees(new Context(), "test");
        Request request = new Request(Method.GET, "/private/polls?adminToken=test");

        int réponse = filtre.beforeHandle(request, new Response(request));

        assertThat(réponse).isEqualTo(Filter.CONTINUE);
    }
}
