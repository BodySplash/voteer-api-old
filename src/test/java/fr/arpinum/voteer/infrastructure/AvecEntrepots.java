package fr.arpinum.voteer.infrastructure;

import fr.arpinum.voteer.infrastructure.persistance.EntrepotsMemoire;
import fr.arpinum.voteer.modele.Entrepots;
import org.junit.rules.ExternalResource;

public class AvecEntrepots extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        Entrepots.initialise(new EntrepotsMemoire());
    }

    @Override
    protected void after() {
        Entrepots.initialise(null);
    }
}
