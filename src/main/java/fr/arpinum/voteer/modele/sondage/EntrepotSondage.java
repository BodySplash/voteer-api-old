package fr.arpinum.voteer.modele.sondage;

import fr.arpinum.graine.modele.Entrepot;

import java.util.List;

public interface EntrepotSondage extends Entrepot<String, Sondage> {

    void ajoute(final Sondage élection);

    Sondage get(final String key);

    List<Sondage> getTous();

    void supprime(Sondage élection);
}
