package fr.arpinum.voteer.infrastructure.persistance;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.arpinum.voteer.modele.sondage.EntrepotSondage;
import fr.arpinum.voteer.modele.sondage.Sondage;

import java.util.List;
import java.util.NoSuchElementException;

public class EntrepotSondageMemoire implements EntrepotSondage {

    @Override
    public void ajoute(final Sondage sondage) {
        liste.add(sondage);

    }

    @Override
    public Sondage get(final String key) {
        try {
            return Iterables.find(liste, input -> input.getId().equals(key));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public List<Sondage> getTous() {
        return liste;
    }

    @Override
    public void supprime(final Sondage sondage) {
        liste.remove(sondage);
    }

    private final List<Sondage> liste = Lists.newArrayList();

}
