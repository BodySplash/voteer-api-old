package fr.arpinum.voteer.infrastructure.persistance;

import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.EntrepotSondage;

public class EntrepotsMemoire extends Entrepots {

    @Override
    protected EntrepotSondage getEntrepotSondage() {
        return entrepotSondage;
    }

    private EntrepotSondage entrepotSondage = new EntrepotSondageMemoire();
}
