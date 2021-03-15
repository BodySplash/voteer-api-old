package fr.arpinum.voteer.modele;

import fr.arpinum.voteer.modele.sondage.EntrepotSondage;

import javax.inject.Inject;

public abstract class Entrepots {

    public static EntrepotSondage sondages() {
        return instance.getEntrepotSondage();
    }

    protected abstract EntrepotSondage getEntrepotSondage();

    public static void initialise(final Entrepots instance) {
        Entrepots.instance = instance;

    }

    @Inject
    private static Entrepots instance;
}
